package org.example.ru.kre4.entity

import org.springframework.data.annotation.Id

data class TaskAnswer(
    @Id var id: Int? = null,
    val taskNumber: Int,
    var savedAnswer: String,
    val studentId: Int
)
