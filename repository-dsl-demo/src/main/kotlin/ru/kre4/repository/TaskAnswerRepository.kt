package org.example.ru.kre4.repository

import org.example.ru.kre4.entity.TaskAnswer
import org.springframework.data.jdbc.repository.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface TaskAnswerRepository: CrudRepository<TaskAnswer, Int> {
    fun findByTaskNumberAndStudentId(taskNumber: Int, studentId: Int): TaskAnswer?

    @Query("select * from task_answers where task_number = :taskNumber and student_id = :studentId")
    fun findByTaskNumberAndStudentIdOptional(taskNumber: Int, studentId: Int): Optional<TaskAnswer>
}
