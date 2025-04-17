package com.hw.flashcard.model

data class Flashcard(
    val id: Int = 0,
    val question: String,
    val answer: String,
    val isReviewed: Boolean = false
) 