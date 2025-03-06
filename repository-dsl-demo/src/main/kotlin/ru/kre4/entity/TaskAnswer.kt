package org.example.ru.kre4.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table(value = "task_answers")
data class TaskAnswer(
    @Id var id: Int? = null,
    val taskNumber: Int,
    var savedAnswer: String,
    val studentId: Int
)
