package paladin.socket

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.kafka.annotation.EnableKafka

@EnableConfigurationProperties
@EnableKafka
@ConfigurationPropertiesScan
@SpringBootApplication
class EventStreamApplication

fun main(args: Array<String>) {
    runApplication<EventStreamApplication>(*args)
}
