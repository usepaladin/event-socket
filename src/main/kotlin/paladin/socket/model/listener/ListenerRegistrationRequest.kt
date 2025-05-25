package paladin.socket.model.listener

import paladin.socket.enums.configuration.Broker
import java.io.Serializable

data class ListenerRegistrationRequest(
    val topic: String,
    val groupId: String,
    val runOnStartup: Boolean = false,
    val key: Broker.ProducerFormat,
    val value: Broker.ProducerFormat,
    val config: AdditionalConsumerProperties,
) : Serializable

