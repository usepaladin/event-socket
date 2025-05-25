package paladin.socket.service.subscription

import org.springframework.stereotype.Service
import org.springframework.web.socket.handler.TextWebSocketHandler
import paladin.socket.service.listener.EventListenerRegistry

@Service
class SubscriptionService(
    private val eventListenerRegistry: EventListenerRegistry
) : TextWebSocketHandler()