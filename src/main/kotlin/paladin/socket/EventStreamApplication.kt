package paladin.socket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EventStreamApplication

fun main(args: Array<String>) {
    runApplication<EventStreamApplication>(*args)
}
