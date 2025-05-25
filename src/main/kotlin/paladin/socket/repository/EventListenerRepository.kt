package paladin.socket.repository

import org.springframework.data.jpa.repository.JpaRepository
import paladin.socket.entity.listener.EventListenerConfigurationEntity
import java.util.*

interface EventListenerRepository : JpaRepository<EventListenerConfigurationEntity, UUID>