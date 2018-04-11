package uk.co.grahamcox.space.websocket

import org.slf4j.LoggerFactory
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.TextWebSocketHandler

/**
 * Handler for WebSocket connections
 */
class WebSocketHandler : TextWebSocketHandler() {
    companion object {
        /** The logger to use */
        private val LOG = LoggerFactory.getLogger(WebSocketHandler::class.java)
    }

    /**
     * Handler for when a new connection has been opened
     */
    override fun afterConnectionEstablished(session: WebSocketSession) {
        LOG.debug("New connection from {}", session)
    }

    /**
     * Handler for when the connection has been closed
     */
    override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
        LOG.debug("Disconnection from {}: {}", session, status)
    }

    /**
     * Handler for when a message is received
     */
    override fun handleTextMessage(session: WebSocketSession, message: TextMessage) {
        LOG.debug("Received message from {}: {}", session, message)
    }

}
