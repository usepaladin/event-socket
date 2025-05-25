package paladin.socket.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import paladin.socket.service.subscription.SubscriptionService

@Configuration
@EnableWebSocket
class WebsocketConfig(
    private val subscriptionService: SubscriptionService
) : WebSocketConfigurer {

    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(subscriptionService, "/ws/subscription")
            .setAllowedOrigins("*") // Adjust CORS settings as needed
            .withSockJS() // Optional: Enable SockJS fallback options
    }
}