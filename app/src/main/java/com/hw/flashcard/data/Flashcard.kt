package com.hw.flashcard.data

data class Flashcard(
    val id: Long,
    val question: String,
    val answer: String,
    var isViewed: Boolean = false
) 