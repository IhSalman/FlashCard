package com.hw.flashcard.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hw.flashcard.data.Flashcard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class FlashcardViewModel : ViewModel() {
    private val _flashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val flashcards: StateFlow<List<Flashcard>> = _flashcards.asStateFlow()

    private var shuffledFlashcards by mutableStateOf<List<Flashcard>>(emptyList())
    var currentCardIndex by mutableStateOf(0)
        private set
    var isFlipped by mutableStateOf(false)
        private set
    var viewedCount by mutableStateOf(0)
        private set

    val currentCard: Flashcard?
        get() = shuffledFlashcards.getOrNull(currentCardIndex)

    val progress: String
        get() = "$viewedCount/${shuffledFlashcards.size} cards reviewed"

    fun addFlashcard(question: String, answer: String) {
        val newFlashcard = Flashcard(
            id = System.currentTimeMillis(),
            question = question,
            answer = answer,
            isViewed = false
        )
        _flashcards.value = _flashcards.value + newFlashcard
        shuffledFlashcards = _flashcards.value.shuffled()
    }

    fun flipCard() {
        isFlipped = !isFlipped
        if (isFlipped && currentCard?.isViewed == false) {
            viewedCount++
            currentCard?.isViewed = true
        }
    }

    fun nextCard() {
        if (currentCardIndex < shuffledFlashcards.size - 1) {
            currentCardIndex++
            isFlipped = false
        }
    }

    fun resetProgress() {
        shuffledFlashcards = _flashcards.value.shuffled()
        currentCardIndex = 0
        isFlipped = false
        viewedCount = 0
        _flashcards.value.forEach { it.isViewed = false }
    }
} 