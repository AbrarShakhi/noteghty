package com.github.abrarshakhi.noteghty.note.presentation.edit_note

import androidx.lifecycle.ViewModel
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import com.github.abrarshakhi.noteghty.note.domain.model.NoteColor
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.greyGreenText
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.offWhite
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.paleYellow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NoteEditViewModel @Inject constructor() : ViewModel() {

    private val _activeNote = MutableStateFlow(
        Note.create(
            title = "", content = "", color = NoteColor(
                primary = paleYellow, background = offWhite, foreground = greyGreenText
            )
        )
    )
    val activeNote = _activeNote.asStateFlow()

    fun getNote(noteId: Int) {
        _activeNote.update { it.update(title = "Loaded From db") }
    }

    fun tryToSave() {
    }


}