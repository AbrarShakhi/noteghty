package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.domain.use_case.GetNoteByIdUseCase
import com.github.abrarshakhi.noteghty.note.domain.use_case.SaveNoteUseCase
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.greyGreenText
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.offWhite
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.paleYellow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor(
    private val getNoteByIdUseCase: GetNoteByIdUseCase,
    private val saveNoteUseCase: SaveNoteUseCase,
) : ViewModel() {

    var note = Note.create(
        title = "Loaded from ", content = "", color = NoteColor(
            primary = paleYellow, background = offWhite, foreground = greyGreenText
        )
    )

    init {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                tryToSave()
            }
        }
    }

    private val _activeNote = MutableStateFlow(
        Note.create(
            title = "", content = "", color = NoteColor(
                primary = paleYellow, background = offWhite, foreground = greyGreenText
            )
        )
    )
    val activeNote = _activeNote.asStateFlow()

    fun loadNote(noteId: Int) {
        // Imagine it fetches from DB with a key
        _activeNote.update { note }
    }

    fun onContentChange(newContent: String) {
        _activeNote.update { it.update(content = newContent) }
    }

    fun tryToSave() {
        // imagine try to save to DB
        // saveNoteUseCase(_activeNote.value)
        note = _activeNote.value
    }


}