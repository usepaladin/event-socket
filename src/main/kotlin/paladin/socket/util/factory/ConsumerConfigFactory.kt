package paladin.socket.util.factory

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.Deserializer
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import paladin.socket.enums.configuration.Broker
import paladin.socket.model.listener.EventListener

object ConsumerConfigFactory {
    /**
     * Generates Configuration properties for an Event Listener being registered
     */
    fun buildConsumer(
        config: MutableMap<String, Any>,
        eventListener: EventListener
    ): Triple<Map<String, Any>, Deserializer<*>, Deserializer<*>> {
        // Assert Schema registry exists if Avro is used, otherwise throw
        if (eventListener.config.schemaRegistryUrl.isNullOrEmpty() && (eventListener.value == Broker.ProducerFormat.AVRO || eventListener.key == Broker.ProducerFormat.AVRO)) {
            throw IllegalArgumentException("Schema Registry URL is required for AVRO format")
        }
        
        val keyDeserializer: ErrorHandlingDeserializer<*> =
            generateDeserializer(eventListener.key, eventListener.config.schemaRegistryUrl, true)
        val valueDeserializer = generateDeserializer(eventListener.value, eventListener.config.schemaRegistryUrl)


        eventListener.config.schemaRegistryUrl?.let {
            config["schema.registry.url"] = it
            config["specific.avro.reader"] = true
        }

        applyConfigIfExist(
            config,
            listOf(
                ConsumerConfig.GROUP_ID_CONFIG to eventListener.groupId,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG to eventListener.config.enableAutoCommit,
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG to eventListener.config.autoOffsetReset,
                ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG to eventListener.config.requestTimeoutMs,
                ConsumerConfig.MAX_POLL_RECORDS_CONFIG to eventListener.config.maxPollRecords,
                ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG to eventListener.config.maxPollIntervalMs,
                ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG to eventListener.config.sessionTimeoutMs,
                ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG to eventListener.config.autoCommitIntervalMs,
            )
        )

        // Set the deserializers in the configuration
        config[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = keyDeserializer.javaClass.name
        config[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = valueDeserializer.javaClass.name

        return Triple(
            config,
            keyDeserializer,
            valueDeserializer
        )
    }


    /**
     * Creates a deserializer instance for the specified format with error handling wrapped around it
     */
    private fun generateDeserializer(
        format: Broker.ProducerFormat,
        schemaRegistryUrl: String? = null,
        isKey: Boolean = false
    ): ErrorHandlingDeserializer<*> {
        val baseDeserializer = when (format) {
            Broker.ProducerFormat.STRING -> org.apache.kafka.common.serialization.StringDeserializer()
            Broker.ProducerFormat.JSON -> {
                if (schemaRegistryUrl.isNullOrEmpty()) {
                    io.confluent.kafka.serializers.KafkaJsonDeserializer<Any>()
                } else {
                    io.confluent.kafka.serializers.json.KafkaJsonSchemaDeserializer<Any>().apply {
                        configure(
                            mapOf("schema.registry.url" to schemaRegistryUrl),
                            isKey
                        )
                    }
                }
            }

            Broker.ProducerFormat.AVRO -> {
                io.confluent.kafka.serializers.KafkaAvroDeserializer().apply {
                    configure(
                        mapOf(
                            "schema.registry.url" to schemaRegistryUrl,
                            "specific.avro.reader" to true
                        ), isKey
                    )
                }
            }
        }

        // Wrap in error handling deserializer
        return ErrorHandlingDeserializer(baseDeserializer)
    }

    private fun applyConfigIfExist(
        config: MutableMap<String, Any>,
        values: List<Pair<String, Any?>>,
    ) {
        values.forEach { (key, value) ->
            value?.let {
                config[key] = it
            }
        }
    }
}