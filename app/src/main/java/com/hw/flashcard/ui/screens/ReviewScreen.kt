package com.hw.flashcard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hw.flashcard.ui.components.FlipCard
import com.hw.flashcard.ui.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReviewScreen(
    viewModel: FlashcardViewModel,
    onNavigateToNewCard: () -> Unit,
    onNavigateToSavedCards: () -> Unit
) {
    val flashcards by viewModel.flashcards.collectAsState()
    val currentCard = viewModel.currentCard
    val isFlipped = viewModel.isFlipped
    val progress = viewModel.progress

    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        if (flashcards.isEmpty()) "No Cards"
                        else "Card ${viewModel.currentCardIndex + 1} of ${flashcards.size}"
                    )
                },
                actions = {
                    if (flashcards.isNotEmpty()) {
                        IconButton(onClick = onNavigateToSavedCards) {
                            Icon(Icons.AutoMirrored.Filled.List, contentDescription = "View Saved Cards")
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onNavigateToNewCard,
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add new card"
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (flashcards.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "No flashcards available. Add some cards to get started!",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    // Progress text at the top
                    Text(
                        text = progress,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(16.dp)
                    )

                    // Flashcard in the middle
                    if (currentCard != null) {
                        FlipCard(
                            frontText = currentCard.question,
                            backText = currentCard.answer,
                            isFlipped = isFlipped,
                            onClick = { viewModel.flipCard() }
                        )
                    }

                    // Navigation and reset buttons at the bottom
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { viewModel.resetProgress() },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Refresh,
                                contentDescription = "Reset Progress",
                                modifier = Modifier.padding(end = 8.dp)
                            )
                            Text("Reset Progress")
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Button(
                                onClick = { viewModel.nextCard() },
                                enabled = viewModel.currentCardIndex < flashcards.size - 1
                            ) {
                                Text("Next Card")
                            }
                        }
                    }
                }
            }
        }
    }
} 