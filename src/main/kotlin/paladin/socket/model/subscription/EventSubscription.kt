package paladin.socket.model.subscription

import com.fasterxml.jackson.databind.JsonNode

data class EventSubscription(
    // Unique identifier for client the subscription
    val clientId: String,
    // Topic to which the client is subscribed
    val topic: String,
    // Unique identifier for the particular topic subscription (To handle multiple subscriptions to the same topic)
    val subscriptionId: Set<String>,
    // Predicate to filter events for the subscription
    val predicate: JsonNode,
)