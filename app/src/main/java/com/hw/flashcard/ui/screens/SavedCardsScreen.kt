package com.hw.flashcard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hw.flashcard.data.Flashcard
import com.hw.flashcard.ui.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedCardsScreen(
    viewModel: FlashcardViewModel,
    onNavigateBack: () -> Unit
) {
    val flashcards by viewModel.flashcards.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Saved Cards") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        if (flashcards.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No saved cards yet")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(flashcards) { card ->
                    Card(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = "Question: ${card.question}",
                                style = MaterialTheme.typography.bodyLarge
                            )
                            Text(
                                text = "Answer: ${card.answer}",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Text(
                                text = if (card.isViewed) "Viewed" else "Not viewed",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }
    }
} 