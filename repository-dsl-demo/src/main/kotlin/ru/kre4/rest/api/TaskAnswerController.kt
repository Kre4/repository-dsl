package org.example.ru.kre4.rest.api

import org.example.ru.kre4.rest.dto.SaveAnswerDto
import org.example.ru.kre4.service.TaskAnswerService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/task-answer")
class TaskAnswerController(private val taskAnswerService: TaskAnswerService) {

    @PostMapping
    fun save(@RequestBody dto: SaveAnswerDto) = taskAnswerService.save(dto)
}
