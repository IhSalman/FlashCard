package com.hw.flashcard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hw.flashcard.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddCardScreen(
    viewModel: FlashcardViewModel,
    onNavigateBack: () -> Unit
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add New Card") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = question,
                onValueChange = { question = it },
                label = { Text("Question") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = answer,
                onValueChange = { answer = it },
                label = { Text("Answer") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    if (question.isNotBlank() && answer.isNotBlank()) {
                        viewModel.addFlashcard(question, answer)
                        onNavigateBack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = question.isNotBlank() && answer.isNotBlank()
            ) {
                Text("Add Card")
            }
        }
    }
} 