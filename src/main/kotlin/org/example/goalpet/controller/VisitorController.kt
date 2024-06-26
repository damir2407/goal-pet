package org.example.goalpet.controller

import jakarta.validation.Valid
import org.example.goalpet.config.TASK_EXECUTOR_CONTROLLER
import org.example.goalpet.domain.Visitor
import org.example.goalpet.dto.request.VisitorCreateRequest
import org.example.goalpet.service.VisitorService
import org.springframework.data.domain.Page
import org.springframework.http.ResponseEntity
import org.springframework.scheduling.annotation.Async
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.CompletableFuture

@RestController
@RequestMapping("api/v1/visitors")
class VisitorController(
    private val visitorService: VisitorService
) {

    @Async(TASK_EXECUTOR_CONTROLLER)
    @GetMapping
    fun getVisitors(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "5") size: Int,
    ): CompletableFuture<ResponseEntity<Page<Visitor>>> {
        return visitorService
            .findAllVisitors(page, size)
            .thenApply { ResponseEntity.ok(it) }
    }

    @PostMapping
    fun createVisitor(
        @RequestBody @Valid request: VisitorCreateRequest
    ): Visitor {
        return visitorService.createVisitor(request)
    }
}