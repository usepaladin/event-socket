package paladin.socket.model.listener

import org.apache.kafka.clients.consumer.ConsumerRecord
import paladin.socket.entity.listener.EventListenerConfigurationEntity
import paladin.socket.enums.configuration.Broker
import paladin.socket.model.listener.EventListener
import java.util.*

data class EventListener(
    var id: UUID? = null,
    var topic: String,
    var runOnStartup: Boolean = false,
    var groupId: String,
    var key: Broker.ProducerFormat,
    var value: Broker.ProducerFormat,
    val config: AdditionalConsumerProperties,
) {
    fun processMessage(message: ConsumerRecord<Any, Any>) {

    }

    companion object{
        fun EventListener.toEntity(): EventListenerConfigurationEntity {
            return EventListenerConfigurationEntity(
                id = this.id,
                topic = this.topic,
                groupId = this.groupId,
                runOnStartup = this.runOnStartup,
                keyFormat = this.key,
                valueFormat = this.value,
                consumerProperties = this.config,
            )
        }
    }
}
