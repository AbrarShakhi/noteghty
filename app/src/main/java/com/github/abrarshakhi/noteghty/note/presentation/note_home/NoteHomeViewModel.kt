package com.github.abrarshakhi.noteghty.note.presentation.note_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.abrarshakhi.noteghty.note.domain.model.Note
import dagger.hilt.android.lifecycle.HiltViewModel
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
                    title = "Welcome to NoteGhty",
                    content = "This is your first note. You can edit, pin, or delete it.",
                    color = 0xFFFFF59D,
                    isPinned = true
                ),
                Note.create(
                    title = "Daily Tasks",
                    content = "• Finish UI\n• Review PR\n• Push release build",
                    color = 0xFFBBDEFB
                ),
                Note.create(
                    title = "Design Ideas",
                    content = "Explore Material You, dynamic colors, and animations.",
                    color = 0xFFC8E6C9
                ),
                Note.create(
                    title = "Workout Plan",
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shoulders",
                    color = 0xFFFFCDD2
                )
                , Note.create(
                    title = "Workout Plan",
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shoulders",
                    color = 0xFFFFCDD2
                ), Note.create(
                    title = "Workout Plan",
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shouldersjfri fgghirgharuig rghrughrug harjgharjghrujghrjagharujhgjrhgurgarjg\nueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg ueghfuuefn ug eifgehji efhgEHFGEH FEHGharioughruoghruahg",
                    color = 0xFFFFCDD2
                ), Note.create(
                    title = "Workout Plan",
                    content = "Mon: Chest\nTue: Back\nWed: Legs\nThu: Shoulders",
                    color = 0xFFFFCDD2
                )
            )
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