package com.hw.flashcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hw.flashcard.ui.screens.NewCardScreen
import com.hw.flashcard.ui.screens.ReviewScreen
import com.hw.flashcard.ui.screens.SavedCardsScreen
import com.hw.flashcard.ui.theme.FlashcardTheme
import com.hw.flashcard.ui.viewmodel.FlashcardViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FlashcardTheme {
                val navController = rememberNavController()
                val viewModel: FlashcardViewModel = viewModel()

                NavHost(
                    navController = navController,
                    startDestination = "review"
                ) {
                    composable("review") {
                        ReviewScreen(
                            viewModel = viewModel,
                            onNavigateToNewCard = { navController.navigate("new_card") },
                            onNavigateToSavedCards = { navController.navigate("saved_cards") }
                        )
                    }
                    composable("new_card") {
                        NewCardScreen(
                            viewModel = viewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                    composable("saved_cards") {
                        SavedCardsScreen(
                            viewModel = viewModel,
                            onNavigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}