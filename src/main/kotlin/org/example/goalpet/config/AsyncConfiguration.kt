package org.example.goalpet.config

import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import java.util.concurrent.Executor


const val TASK_EXECUTOR_REPOSITORY = "repositoryTaskExecutor"
const val TASK_EXECUTOR_DEFAULT = "taskExecutor"
const val TASK_EXECUTOR_SERVICE = "serviceTaskExecutor"
const val TASK_EXECUTOR_CONTROLLER = "controllerTaskExecutor"


const val TASK_EXECUTOR_NAME_PREFIX_DEFAULT = "taskExecutor-"
const val TASK_EXECUTOR_NAME_PREFIX_REPOSITORY = "serviceTaskExecutor-"
const val TASK_EXECUTOR_NAME_PREFIX_SERVICE = "serviceTaskExecutor-"
const val TASK_EXECUTOR_NAME_PREFIX_CONTROLLER = "controllerTaskExecutor-"


@Configuration
@EnableAsync
class AsyncConfiguration(
    private val applicationProperties: ApplicationProperties
) : AsyncConfigurer {

    @Bean(name = [TASK_EXECUTOR_DEFAULT])
    override fun getAsyncExecutor(): Executor {
        return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_DEFAULT)
    }

    @Bean(TASK_EXECUTOR_REPOSITORY)
    fun getRepositoryAsyncExecutor(): Executor {
        return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_REPOSITORY)
    }

    @Bean(TASK_EXECUTOR_SERVICE)
    fun getServiceAsyncExecutor(): Executor {
        return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_SERVICE)
    }

    @Bean(TASK_EXECUTOR_CONTROLLER)
    fun getControllerAsyncExecutor(): Executor {
        return newTaskExecutor(TASK_EXECUTOR_NAME_PREFIX_CONTROLLER)
    }

    override fun getAsyncUncaughtExceptionHandler(): AsyncUncaughtExceptionHandler {
        return SimpleAsyncUncaughtExceptionHandler()
    }

    private fun newTaskExecutor(taskExecutorNamePrefix: String): Executor {
        val asyncProperties = applicationProperties.async
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = asyncProperties.corePoolSize
        executor.maxPoolSize = asyncProperties.maxPoolSize
        executor.queueCapacity = asyncProperties.queueCapacity
        executor.setThreadNamePrefix(taskExecutorNamePrefix)
        return executor
    }
}