package com.github.abrarshakhi.noteghty.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.abrarshakhi.noteghty.core.ui.theme.Black
import com.github.abrarshakhi.noteghty.core.ui.theme.Yellow
import com.github.abrarshakhi.noteghty.core.ui.theme.Red
import com.github.abrarshakhi.noteghty.core.ui.theme.MutedCoralRose
import com.github.abrarshakhi.noteghty.core.ui.theme.Orange


@Entity
data class Note(
    val title: String,
    val content: String,
    val timestamp: Long,
    val color: Int,
    @PrimaryKey val id: Int? = null
) {
    companion object {
        val noteColors =
            listOf(Orange, Red, Black, Yellow, MutedCoralRose)
    }
}

class InvalidNoteException(message: String) : Exception(message)