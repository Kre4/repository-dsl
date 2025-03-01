package org.example.ru.kre4.repository

import org.example.ru.kre4.entity.TaskAnswer
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskAnswerRepository: CrudRepository<TaskAnswer, Int> {
    fun findByTaskNumberAndStudentId(taskNumber: Int, studentId: Int): TaskAnswer
}
