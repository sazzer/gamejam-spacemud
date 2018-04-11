package uk.co.grahamcox.space.websocket

import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry

/**
 * Spring configuration for the WebSockets
 */
@Configuration
@EnableWebSocket
class WebSocketConfig : WebSocketConfigurer {
    /**
     * Configure the WebSocket handlers
     */
    override fun registerWebSocketHandlers(registry: WebSocketHandlerRegistry) {
        registry.addHandler(WebSocketHandler(), "/ws/**")
                .setAllowedOrigins("*")
    }
}
