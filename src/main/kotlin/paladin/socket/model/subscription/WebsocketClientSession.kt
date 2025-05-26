package paladin.socket.model.subscription

import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.ConcurrentHashMap

data class WebsocketClientSession(
    val session: WebSocketSession,
    val subscriptions: ConcurrentHashMap<String, EventSubscription> = ConcurrentHashMap()
)