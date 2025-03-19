package org.example.ru.kre4.rest.api

import org.example.ru.kre4.entity.TaskAnswer
import org.example.ru.kre4.repository.TaskAnswerRepository
import org.example.ru.kre4.rest.dto.SaveAnswerDto
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.kre4.repository.extensions.saveUpdate

@RestController
@RequestMapping("/api/v1/task-answer")
class TaskAnswerController(private val taskAnswerRepository: TaskAnswerRepository) {

    @PostMapping("/kotlin-dsl")
    fun save(@RequestBody dto: SaveAnswerDto): TaskAnswer {
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

    @PostMapping("/kotlin-native")
    fun saveShortButUgly(@RequestBody dto: SaveAnswerDto): TaskAnswer {
        return taskAnswerRepository.save(
            taskAnswerRepository.findByTaskNumberAndStudentId(dto.taskNumber, dto.studentId)?.also {
                it.savedAnswer = dto.answer
            } ?: TaskAnswer(
                studentId = dto.studentId,
                savedAnswer = dto.answer,
                taskNumber = dto.taskNumber
            ))
    }

    @PostMapping("/java-style")
    fun saveViaOptional(@RequestBody dto: SaveAnswerDto): TaskAnswer {
        return taskAnswerRepository.save(
            taskAnswerRepository.findByTaskNumberAndStudentIdOptional(dto.taskNumber, dto.studentId)
                .orElse(
                    TaskAnswer(
                        studentId = dto.studentId,
                        savedAnswer = dto.answer,
                        taskNumber = dto.taskNumber
                    )
                )
        )
    }
}
