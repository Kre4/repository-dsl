package org.example.ru.kre4.service

import org.example.ru.kre4.entity.TaskAnswer
import org.example.ru.kre4.repository.TaskAnswerRepository
import org.example.ru.kre4.rest.dto.SaveAnswerDto
import org.springframework.stereotype.Service
import ru.kre4.repository.extensions.saveUpdate

@Service
class TaskAnswerService(private val taskAnswerRepository: TaskAnswerRepository) {

    fun save(dto: SaveAnswerDto): TaskAnswer {
        return taskAnswerRepository.saveUpdate {
            bySearch {
                findByTaskNumberAndStudentId(dto.taskNumber, dto.studentId)
            }
            ifExists {
                savedAnswer = dto.answer
            }
            ifNew {
                TaskAnswer(
                    studentId = dto.studentId,
                    savedAnswer = dto.answer,
                    taskNumber = dto.taskNumber
                )
            }
        }
    }

    fun saveShortButUgly(dto: SaveAnswerDto): TaskAnswer {
        return taskAnswerRepository.save(
            taskAnswerRepository.findByTaskNumberAndStudentId(dto.taskNumber, dto.studentId)?.also {
                it.savedAnswer = dto.answer
            } ?: TaskAnswer(
                studentId = dto.studentId,
                savedAnswer = dto.answer,
                taskNumber = dto.taskNumber
            ))
    }
}
