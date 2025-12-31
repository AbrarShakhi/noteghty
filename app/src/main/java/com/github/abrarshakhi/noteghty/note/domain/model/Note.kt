package com.github.abrarshakhi.noteghty.note.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.abrarshakhi.noteghty.ui.theme.LavenderBlue
import com.github.abrarshakhi.noteghty.ui.theme.LightApricot
import com.github.abrarshakhi.noteghty.ui.theme.LightBlushPink
import com.github.abrarshakhi.noteghty.ui.theme.MutedCoralRose
import com.github.abrarshakhi.noteghty.ui.theme.WarmCream


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
            listOf(WarmCream, LightBlushPink, LavenderBlue, LightApricot, MutedCoralRose)
    }
}

class InvalidNoteException(message: String) : Exception(message)