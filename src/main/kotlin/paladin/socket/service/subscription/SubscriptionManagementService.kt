package paladin.socket.service.subscription

import io.github.oshai.kotlinlogging.KLogger
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import paladin.socket.model.subscription.WebsocketClientSession
import java.util.concurrent.ConcurrentHashMap

@Service
class SubscriptionManagementService {
    private val logger: KLogger = KotlinLogging.logger { }
    private val clients = ConcurrentHashMap<String, WebsocketClientSession>()

    fun subscribe() {}
    fun removeTopicSubscription() {}
    fun disconnect() {}

}