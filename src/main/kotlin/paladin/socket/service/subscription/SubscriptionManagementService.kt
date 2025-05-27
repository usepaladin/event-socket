package paladin.socket.service.subscription

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import paladin.socket.exceptions.ClientNotFoundException
import paladin.socket.model.subscription.EventSubscription
import paladin.socket.model.subscription.WebsocketClientSession
import java.util.concurrent.ConcurrentHashMap

@Service
class SubscriptionManagementService {
    private val logger: KLogger = KotlinLogging.logger { }

    // Stores active WebSocket client sessions and their subscriptions
    private val clients = ConcurrentHashMap<String, WebsocketClientSession>()

    // Stores active event subscriptions by topic
    private val sessions = ConcurrentHashMap<String, MutableSet<EventSubscription>>()

    fun subscribe() {}
    fun removeTopicSubscription() {}
    fun disconnect() {}
    fun getSessionsForTopic(topic: String): Set<EventSubscription> {
        logger.info { "Retrieving sessions for topic: $topic" }
        return sessions[topic] ?: emptySet()
    }

    fun getClient(clientId: String): WebsocketClientSession {
        logger.info { "Retrieving subscriptions for client: $clientId" }
        return clients[clientId] ?: throw ClientNotFoundException("Client with ID $clientId not found")
    }

}