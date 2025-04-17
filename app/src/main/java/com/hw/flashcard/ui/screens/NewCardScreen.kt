package com.hw.flashcard.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hw.flashcard.ui.viewmodel.FlashcardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewCardScreen(
    viewModel: FlashcardViewModel,
    onNavigateBack: () -> Unit
) {
    var question by remember { mutableStateOf("") }
    var answer by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
    var showSuccess by remember { mutableStateOf(false) }

    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("New Flashcard") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
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

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancel")
                }

                Button(
                    onClick = {
                        if (question.isBlank() || answer.isBlank()) {
                            showError = true
                            showSuccess = false
                        } else {
                            viewModel.addFlashcard(question, answer)
                            question = ""
                            answer = ""
                            showSuccess = true
                            showError = false
                        }
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Save")
                }
            }
        }
    }

    LaunchedEffect(showError) {
        if (showError) {
            snackbarHostState.showSnackbar(
                message = "Please fill in both question and answer",
                duration = SnackbarDuration.Short
            )
            showError = false
        }
    }

    LaunchedEffect(showSuccess) {
        if (showSuccess) {
            snackbarHostState.showSnackbar(
                message = "Card added successfully",
                duration = SnackbarDuration.Short
            )
            showSuccess = false
            onNavigateBack()
        }
    }
} 