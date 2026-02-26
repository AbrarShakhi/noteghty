package com.github.abrarshakhi.noteghty.note.presentation.note_home.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.github.abrarshakhi.noteghty.R

@Composable
fun ErrorNotesList(padding: PaddingValues, errorMessage: String, onRetry: () -> Unit) {
    Box(modifier = Modifier.padding(padding).fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconButton(onClick = onRetry, modifier = Modifier.size(300.dp).fillMaxSize()) {
                Icon(
                    painter = painterResource(R.drawable.outline_error_24),
                    modifier = Modifier.padding(50.dp).fillMaxSize(),
                    contentDescription = "Error",
                    tint = MaterialTheme.colorScheme.error
                )
            }
            Text(text = errorMessage)
        }
    }
}