package com.github.abrarshakhi.noteghty.note.domain.model

import androidx.compose.ui.graphics.Color
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.amberAccent
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.babyBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.darkText
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.dustyPink
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.forestGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.greenBrownText
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
import kotlin.random.Random

data class NoteColor(
    val primary: Color, val background: Color, val foreground: Color
) {
    companion object {

        val listOfColors = listOf(

            NoteColor(
                primary = forestGreen, background = lightGreen, foreground = greenBrownText
            ),

            NoteColor(
                primary = oliveGreen, background = mint, foreground = greenBrownText
            ),

            NoteColor(
                primary = amberAccent, background = paleYellow, foreground = greenBrownText
            ),

            NoteColor(
                primary = warmBrown, background = lightCream, foreground = greenBrownText
            ),

            NoteColor(
                primary = softRed, background = lightRed, foreground = darkText
            ),

            NoteColor(
                primary = dustyPink, background = softPink, foreground = darkText
            ),

            NoteColor(
                primary = purpleAccent, background = lavender, foreground = darkText
            ),

            NoteColor(
                primary = indigoAccent, background = lightPurple, foreground = darkText
            ),

            NoteColor(
                primary = skyBlue, background = lightBlue, foreground = darkText
            ),

            NoteColor(
                primary = tealAccent, background = lightCyan, foreground = darkText
            ),

            NoteColor(
                primary = oliveGreen, background = limePastel, foreground = greenBrownText
            ),

            NoteColor(
                primary = skyBlue, background = babyBlue, foreground = darkText
            )
        )

        fun random(): NoteColor {
            return listOfColors[Random.nextInt(listOfColors.size)]
        }
    }
}
