package paladin.socket.exceptions

class ProducerNotFoundException(message: String) : RuntimeException(message)
class ActiveListenerException(message: String) : RuntimeException(message)
class ListenerNotFoundException(message: String) : RuntimeException(message)