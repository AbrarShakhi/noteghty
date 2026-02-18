package com.github.abrarshakhi.noteghty.note.domain.model

import androidx.compose.ui.graphics.Color
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.amberAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.babyBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.dustyPink
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.forestGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.indigoAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lavender
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightCream
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightCyan
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightPurple
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightRed
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.limePastel
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.mint
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.oliveGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.paleYellow
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.purpleAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.skyBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.softPink
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.softRed
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.tealAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.warmBrown

data class NoteColor(
    val id: Int, val primary: Color, val background: Color, val isLightForeground: Boolean = false,
) {
    companion object {
        fun fromId(id: Int) = listOfColors[id]


        val listOfColors = listOf(

            NoteColor(
                id = 0, primary = forestGreen, background = lightGreen, isLightForeground = false
            ),

            NoteColor(
                id = 1, primary = oliveGreen, background = mint, isLightForeground = false
            ),

            NoteColor(
                id = 2, primary = amberAccent, background = paleYellow, isLightForeground = false
            ),

            NoteColor(
                id = 3, primary = warmBrown, background = lightCream, isLightForeground = false
            ),

            NoteColor(
                id = 4, primary = softRed, background = lightRed, isLightForeground = true
            ),

            NoteColor(
                id = 5, primary = dustyPink, background = softPink, isLightForeground = true
            ),

            NoteColor(
                id = 6, primary = purpleAccent, background = lavender, isLightForeground = true
            ),

            NoteColor(
                id = 7, primary = indigoAccent, background = lightPurple, isLightForeground = true
            ),

            NoteColor(id = 8, primary = skyBlue, background = lightBlue, isLightForeground = true),

            NoteColor(
                id = 9, primary = tealAccent, background = lightCyan, isLightForeground = true
            ),

            NoteColor(
                id = 10, primary = oliveGreen, background = limePastel, isLightForeground = false
            ),

            NoteColor(
                id = 11, primary = skyBlue, background = babyBlue, isLightForeground = true
            )
        )
    }
}
