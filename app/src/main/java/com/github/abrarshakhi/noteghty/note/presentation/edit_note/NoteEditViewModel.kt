package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.use_case.NoteEditUseCases
import com.github.abrarshakhi.outcome.onErr
import com.github.abrarshakhi.outcome.onOk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class, DelicateCoroutinesApi::class)
@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val useCases: NoteEditUseCases,
) : ViewModel() {

    private val _effect = MutableSharedFlow<NoteEditEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow(NoteEditState())
    val state = _state.asStateFlow()

    private var isUpdated = false

    private inline fun update(reducer: NoteEditState.() -> NoteEditState) {
        val current = state.value
        val updated = current.reducer()
        if (current != updated) {
            _state.update { updated }
        }
    }

    private val saveQueue = Channel<Note>(capacity = Channel.CONFLATED)
    private var autoSaveJob: Job? = null

    init {
        startAutoSaveWorker()
    }

    fun onIntent(intent: NoteEditIntent) {
        when (intent) {
            is NoteEditIntent.Load -> loadNote(intent.noteId)
            is NoteEditIntent.Set -> setNewState(intent)
            is NoteEditIntent.SaveAsynchronous -> saveAsynchronously()
        }
    }

    private fun startAutoSaveWorker() {
        autoSaveJob?.cancel()
        autoSaveJob = viewModelScope.launch {
            for (note in saveQueue) {
                useCases.saveNoteUseCase.sync(note)
                    .onOk { noteId -> update { copy(id = noteId) } }
                    .onErr { }
            }
        }
    }

    private fun loadNote(noteId: Long?) {
        if (noteId == null) {
            update { stopLoading() }
            return
        }
        update { startLoading() }
        viewModelScope.launch {
            useCases.getNoteByIdUseCase(noteId).onOk { note ->
                update { fromNote(note) }
            }.onErr {
                _effect.emit(NoteEditEffect.Error("Not Not Found"))
                update { stopLoading() }
            }
        }
    }

    private fun setNewState(changeIntent: NoteEditIntent.Set) {
        isUpdated = true
        when (changeIntent) {
            is NoteEditIntent.Set.Content -> update { copy(content = changeIntent.newContent) }

            is NoteEditIntent.Set.Title -> update { copy(title = changeIntent.newTitle) }

            is NoteEditIntent.Set.TogglePinned -> update { togglePinned() }

            is NoteEditIntent.Set.Color -> setColorFromId(changeIntent.colorId)
        }
        enqueueSave()
    }

    private fun enqueueSave() {
        viewModelScope.launch { saveQueue.send(state.value.toNote()) }
    }

    private fun setColorFromId(colorId: Int) {
        update { copy(color = NoteColor.listOfColors[colorId]) }
    }

    private fun saveAsynchronously() {
        viewModelScope.launch {
            autoSaveJob?.cancel()
            if (isUpdated) {
                useCases.saveNoteUseCase.async(state.value.toNote())
            }
        }
    }
}