package paladin.socket.configuration.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "encryption")
data class EncryptionConfigurationProperties(
    val requireDataEncryption: Boolean = false,
    val key: String?
)