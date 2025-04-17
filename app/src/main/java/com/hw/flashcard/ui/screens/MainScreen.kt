package com.hw.flashcard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hw.flashcard.ui.components.Flashcard
import com.hw.flashcard.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: FlashcardViewModel,
    onAddCardClick: () -> Unit
) {
    val flashcards by viewModel.flashcards.collectAsState()
    val currentIndex by viewModel.currentIndex.collectAsState()
    val isAnswerVisible by viewModel.isAnswerVisible.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Flashcards") },
                actions = {
                    IconButton(onClick = { viewModel.shuffleCards() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Shuffle cards")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddCardClick) {
                Icon(Icons.Default.Add, contentDescription = "Add new card")
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (flashcards.isEmpty()) {
                Text(
                    text = "No flashcards yet. Tap + to add one!",
                    modifier = Modifier.align(Alignment.Center)
                )
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Progress indicator
                    Text(
                        text = "${currentIndex + 1} of ${flashcards.size}",
                        modifier = Modifier.padding(16.dp)
                    )

                    // Flashcard
                    Flashcard(
                        question = flashcards[currentIndex].question,
                        answer = flashcards[currentIndex].answer,
                        isAnswerVisible = isAnswerVisible,
                        onCardClick = { viewModel.toggleAnswerVisibility() }
                    )

                    // Navigation buttons
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Button(
                            onClick = { viewModel.previousCard() },
                            enabled = currentIndex > 0
                        ) {
                            Text("Previous")
                        }

                        Button(
                            onClick = { viewModel.nextCard() },
                            enabled = currentIndex < flashcards.size - 1
                        ) {
                            Text("Next")
                        }
                    }
                }
            }
        }
    }
} 