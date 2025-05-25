package paladin.socket.model.subscription

import com.fasterxml.jackson.databind.JsonNode

data class SubscriptionRegistrationRequest(
    val topic: String,
    val groupId: String,
    val predicate: JsonNode,
)