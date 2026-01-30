package com.github.abrarshakhi.noteghty.note.presentation.note_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.core.ui.theme.MonokaiBlue
import com.github.abrarshakhi.noteghty.core.ui.theme.MonokaiGreen
import com.github.abrarshakhi.noteghty.core.ui.theme.MonokaiOrange
import com.github.abrarshakhi.noteghty.core.ui.theme.MonokaiPink
import com.github.abrarshakhi.noteghty.core.ui.theme.MonokaiPurple
import com.github.abrarshakhi.noteghty.core.ui.theme.MonokaiYellow
import com.github.abrarshakhi.noteghty.core.ui.theme.NoteghtyRed
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteHomeViewModel @Inject constructor() : ViewModel() {
    private val _notesState = MutableStateFlow<NotesListState>(NotesListState.loading(emptyList()))
    val notesState = _notesState.asStateFlow()

    init {
        loadNotes()
    }

    private fun loadNotes() {
        _notesState.update { it.asLoading() }
        viewModelScope.launch {
            val notes = listOf(
                Note.create(
                    content = "This is your first note. You can edit, pin, or delete it.",
                    color = MonokaiBlue.value.toLong(),
                    isPinned = true
                ),
                Note.create(
                    content = "• Finish UI\n• Review PR\n• Push release build",
                    color = MonokaiPink.value.toLong()
                ),
                Note.create(
                    content = "Explore Material You, dynamic colors, and animations.g argergaer gasergerg ergr eragergawerverver geryarweghawerg erghergheraher erherherghergaergert ",
                    color = MonokaiGreen.value.toLong(),
                    isPinned = true
                ),
                Note.create(
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shoulders",
                    color = MonokaiOrange.value.toLong()
                ), Note.create(
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shoulders",
                    color = MonokaiPurple.value.toLong()
                ), Note.create(
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shouldersjfri fgghirgharuig rghrughrug harjgharjghrujghrjagharujhgjrhgurgarjg\nueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg",
                    color = MonokaiYellow.value.toLong()
                ), Note.create(
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shoulders",
                    color = NoteghtyRed.value.toLong()
                )
            )
            delay(2000L)
            _notesState.update { it.asSuccess(notes) }
        }
    }


    private val _viewStyle = MutableStateFlow(ViewStyleState.AGENDA)
    val viewStyle = _viewStyle.asStateFlow()
    fun toggleViewStyle() {
        // TODO: Later save this in a preference
        when (_viewStyle.value) {
            ViewStyleState.AGENDA -> _viewStyle.update { ViewStyleState.COZY }
            ViewStyleState.COZY -> _viewStyle.update { ViewStyleState.AGENDA }
        }
    }
}