package com.example.quizapp

data class SpreadSheetsData(
    val majorDimension: String,
    val range: String,
    val values: List<List<String>>
)