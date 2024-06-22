package org.example.goalpet.repository

import org.example.goalpet.config.TASK_EXECUTOR_REPOSITORY
import org.example.goalpet.domain.Visitor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Repository
import java.util.concurrent.CompletableFuture

@Repository
interface VisitorRepository : JpaRepository<Visitor, Long> {

    @Async(TASK_EXECUTOR_REPOSITORY)
    @Query("SELECT v FROM Visitor v WHERE v.id = :id")
    fun findSingleById(id: Long): CompletableFuture<Visitor?>
}