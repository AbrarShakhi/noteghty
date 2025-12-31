package com.github.abrarshakhi.noteghty.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.abrarshakhi.noteghty.ui.theme.BabyBlue
import com.github.abrarshakhi.noteghty.ui.theme.LightGreen
import com.github.abrarshakhi.noteghty.ui.theme.RedOrange
import com.github.abrarshakhi.noteghty.ui.theme.RedPink
import com.github.abrarshakhi.noteghty.ui.theme.Violet


@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors = listOf(RedOrange, RedPink, BabyBlue, Violet, LightGreen)
    }
}

class InvalidNoteException(message: String) : Exception(message)