package com.github.abrarshakhi.noteghty.note.presentation.note_home.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.github.abrarshakhi.noteghty.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NoteHomeScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                },
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 12.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.noteghty),
                            contentDescription = "App Logo",
                            modifier = Modifier
                                .width(36.dp)
                                .background(
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                                .padding(5.dp)
                        )
                    }
                },
                actions = {
                    /* IconButton(onClick = { /* search */ }) { Icon(
                        painter = painterResource(R.drawable.outline_search_24),
                        contentDescription = "Search"
                    )} */

                    IconButton(onClick = { /* sort */ }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_swap_vert_24),
                            contentDescription = "Sort"
                        )
                    }

                    IconButton(onClick = { /* rearrange */ }) {
                        Icon(
                            painter = painterResource(R.drawable.outline_view_agenda_24),
                            contentDescription = "Rearrange"
                        )
                    }
                }
            )

        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text("Home")
        }
    }
}

//@Preview(showBackground = true)
@Composable
fun PreviewNoteHomeScreen() {
    NoteHomeScreen(rememberNavController())
}