package org.example.ru.kre4.rest.dto


data class SaveAnswerDto(
    val studentId: Int,
    val taskNumber: Int,
    val answer: String
)
