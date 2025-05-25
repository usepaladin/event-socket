package paladin.socket.controller.subscription

import org.springframework.stereotype.Controller
import paladin.socket.service.subscription.SubscriptionService

@Controller
class SubscriptionController(private val subscriptionService: SubscriptionService)