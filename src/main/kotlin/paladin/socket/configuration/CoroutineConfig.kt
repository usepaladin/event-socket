package paladin.socket.configuration

import jakarta.annotation.PreDestroy
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.concurrent.Executors

/**
 * Configuration class for coroutine dispatchers.
 * Provides a thread pool-based dispatcher for coroutine execution.
 */
@Configuration
class CoroutineConfig {

    @Value("\${paladin.coroutine.pool-size:10}")
    private val poolSize: Int = 10
    private lateinit var dispatcher: ExecutorCoroutineDispatcher

    /**
     * Provides a coroutine dispatcher backed by a fixed thread pool.
     * @return The coroutine dispatcher for dependency injection
     */
    @Bean
    fun coroutineDispatcher(): CoroutineDispatcher{
        return Executors.newFixedThreadPool(poolSize) { runnable ->
            val thread = Thread(runnable, "coroutine-thread-${UUID.randomUUID()}").apply {
                isDaemon = true
            }
            thread
        }.asCoroutineDispatcher()
    }

    /**
     * Closes the coroutine dispatcher when the application context is destroyed.
     */
    @PreDestroy
    fun closeDispatcher() {
        dispatcher.close()
    }

}