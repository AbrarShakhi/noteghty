package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.core.di.AppModule
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.use_case.NoteEditUseCases
import com.github.abrarshakhi.outcome.onErr
import com.github.abrarshakhi.outcome.onOk
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val useCases: NoteEditUseCases,
    @param:AppModule.CoroutineScopeModule.ApplicationScope
    private val applicationScope: CoroutineScope,
) : ViewModel() {

    private val _effect = MutableSharedFlow<NoteEditEffect>()
    val effect = _effect.asSharedFlow()

    private val _state = MutableStateFlow(NoteEditState())
    val state = _state.asStateFlow()

    private var isDirty = false
    private var debounceJob: Job? = null

    private inline fun update(reducer: NoteEditState.() -> NoteEditState) {
        val current = state.value
        val updated = current.reducer()
        if (current != updated) _state.update { updated }
    }

    fun onIntent(intent: NoteEditIntent) {
        when (intent) {
            is NoteEditIntent.Load -> loadNote(intent.noteId)
            is NoteEditIntent.Set -> setNewState(intent)
            is NoteEditIntent.SaveNow -> saveNow()
            is NoteEditIntent.ToggleEditMode -> update { toggleEditMode() }
        }
    }

    private fun loadNote(noteId: Long?) {
        if (noteId == null) {
            update { stopLoading() }
            return
        }
        update { startLoading() }
        viewModelScope.launch {
            useCases.getNoteByIdUseCase(noteId)
                .onOk { note -> update { fromNote(note) } }
                .onErr {
                    _effect.emit(NoteEditEffect.Error("Note Not Found"))
                    update { stopLoading() }
                }
        }
    }

    private fun setNewState(changeIntent: NoteEditIntent.Set) {
        isDirty = true
        when (changeIntent) {
            is NoteEditIntent.Set.Content -> update { copy(content = changeIntent.newContent) }
            is NoteEditIntent.Set.Title -> update { copy(title = changeIntent.newTitle) }
            is NoteEditIntent.Set.TogglePinned -> update { togglePinned() }
            is NoteEditIntent.Set.Color -> update { copy(color = NoteColor.listOfColors[changeIntent.colorId]) }
        }
        scheduleDebouncedSave()
    }

    private fun scheduleDebouncedSave() {
        debounceJob?.cancel()
        debounceJob = viewModelScope.launch {
            delay(800L)
            commitSave()
        }
    }

    // State is captured on the calling (main) thread before the IO launch to avoid races.
    // isDirty is reset before launch to prevent a double-save if saveNow and debounce both fire.
    private fun commitSave() {
        if (!isDirty) return
        isDirty = false
        val snapshot = state.value.toNote()
        applicationScope.launch {
            useCases.saveNoteUseCase.async(snapshot)
        }
    }

    private fun saveNow() {
        debounceJob?.cancel()
        commitSave()
    }

    override fun onCleared() {
        super.onCleared()
        if (isDirty) {
            isDirty = false
            val snapshot = state.value.toNote()
            applicationScope.launch {
                useCases.saveNoteUseCase.async(snapshot)
            }
        }
    }
}
