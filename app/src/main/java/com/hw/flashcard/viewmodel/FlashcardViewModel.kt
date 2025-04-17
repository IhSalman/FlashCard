package com.hw.flashcard.viewmodel

import androidx.lifecycle.ViewModel
import com.hw.flashcard.model.Flashcard
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FlashcardViewModel : ViewModel() {
    private val _flashcards = MutableStateFlow<List<Flashcard>>(emptyList())
    val flashcards: StateFlow<List<Flashcard>> = _flashcards.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex: StateFlow<Int> = _currentIndex.asStateFlow()

    private val _isAnswerVisible = MutableStateFlow(false)
    val isAnswerVisible: StateFlow<Boolean> = _isAnswerVisible.asStateFlow()

    fun addFlashcard(question: String, answer: String) {
        val newFlashcard = Flashcard(
            id = _flashcards.value.size + 1,
            question = question,
            answer = answer
        )
        _flashcards.update { currentList ->
            currentList + newFlashcard
        }
    }

    fun toggleAnswerVisibility() {
        _isAnswerVisible.update { !it }
    }

    fun nextCard() {
        if (_currentIndex.value < _flashcards.value.size - 1) {
            _currentIndex.update { it + 1 }
            _isAnswerVisible.value = false
        }
    }

    fun previousCard() {
        if (_currentIndex.value > 0) {
            _currentIndex.update { it - 1 }
            _isAnswerVisible.value = false
        }
    }

    fun shuffleCards() {
        _flashcards.update { currentList ->
            currentList.shuffled()
        }
        _currentIndex.value = 0
        _isAnswerVisible.value = false
    }

    fun markAsReviewed() {
        val currentCard = _flashcards.value[_currentIndex.value]
        _flashcards.update { currentList ->
            currentList.map { card ->
                if (card.id == currentCard.id) {
                    card.copy(isReviewed = true)
                } else {
                    card
                }
            }
        }
    }
} 