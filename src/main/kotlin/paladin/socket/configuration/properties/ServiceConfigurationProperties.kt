package paladin.socket.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "paladin")
data class ServiceConfigurationProperties(
    val includeStackTrace: Boolean = false
)
