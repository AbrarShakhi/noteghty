package com.github.abrarshakhi.noteghty.note.domain.model

import androidx.compose.ui.graphics.Color
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.babyBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.greenBrownText
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.greyGreenText
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lavender
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightBlue
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightCream
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightCyan
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightGreen
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightPurple
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.lightRed
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.limePastel
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.mint
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.offWhite
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.paleYellow
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.peach
import com.github.abrarshakhi.noteghty.note.presentation.ui.theme.softPink

object NoteColor {
    val backgrounds = listOf(
        offWhite,
        lightCream,
        peach,
        lightRed,
        softPink,
        lavender,
        lightPurple,
        lightBlue,
        babyBlue,
        lightCyan,
        mint,
        lightGreen,
        limePastel,
        paleYellow
    )

    fun anyBg(): Color = backgrounds.random()
    fun textFg(): Color = greenBrownText
    fun textFg2(): Color = greyGreenText
}