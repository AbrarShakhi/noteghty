package com.github.abrarshakhi.noteghty.note.domain.model

import androidx.compose.ui.graphics.Color
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.amberAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.forestGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.indigoAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lavender
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightCream
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightPurple
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightRed
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.limePastel
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.mint
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.oliveGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.paleYellow
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.purpleAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.skyBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.softRed
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.warmBrown

data class NoteColor(
    val id: Int, val primary: Color, val background: Color, val isLightForeground: Boolean = false,
) {
    companion object {
        val listOfColors = listOf(
            NoteColor(0, forestGreen, lightGreen, false),
            NoteColor(1, oliveGreen, mint, false),
            NoteColor(2, amberAccent, paleYellow, false),
            NoteColor(3, warmBrown, lightCream, false),
            NoteColor(4, softRed, lightRed, false),
            NoteColor(5, purpleAccent, lavender, false),
            NoteColor(6, indigoAccent, lightPurple, false),
            NoteColor(7, skyBlue, lightBlue, false),
            NoteColor(9, oliveGreen, limePastel, false),
        )
    }
}
