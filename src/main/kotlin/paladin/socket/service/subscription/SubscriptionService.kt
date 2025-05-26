package paladin.socket.service.subscription

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler
import paladin.socket.model.subscription.EventSubscription
import paladin.socket.service.event.EventFilteringService
import paladin.socket.service.listener.EventListenerRegistry
import java.util.concurrent.ConcurrentHashMap

@Service
class SubscriptionService(
    private val eventListenerRegistry: EventListenerRegistry,
    private val authenticationService: AuthenticationService,
    private val subscriptionManagementService: SubscriptionManagementService,
    private val eventFilteringService: EventFilteringService
) : TextWebSocketHandler() {
    private val logger = KotlinLogging.logger {}
    private val topicSubscriptions = ConcurrentHashMap<String, MutableSet<EventSubscription>>()

    override fun afterConnectionEstablished(session: WebSocketSession) {
        logger.info { "Connection established for session ${session.id}" }
        super.afterConnectionEstablished(session)
    }

    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        logger.info { "Connection closed for session ${session.id} with status $status" }
        super.afterConnectionClosed(session, status)
    }

    /**
     * Handles incoming messages from WebSocket clients to update client subscription requests.
     */
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        logger.info { "Received message from session ${session.id}: ${message.payload}" }
        super.handleTextMessage(session, message)
    }

    override fun handleTransportError(session: WebSocketSession, exception: Throwable) {
        logger.error(exception) { "Transport error in WebSocket session: ${session.id}" }
        super.handleTransportError(session, exception)
    }
}