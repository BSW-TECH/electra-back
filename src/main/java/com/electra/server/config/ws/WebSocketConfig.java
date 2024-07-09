package com.electra.server.config.ws;

import com.electra.server.service.ws.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    public static final String PATH_PREFIX = "/signal/";

    @Autowired
    private WebSocketService webSocketService;

//    @Bean
//    public ServletServerContainerFactoryBean createWebSocketContainer() {
//        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
//        container.setMaxTextMessageBufferSize(500000);
//        container.setMaxBinaryMessageBufferSize(500000);
//        return container;
//    }
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {

        // Register handler for forwarding data to destination WebSocket
        registry.addHandler(webSocketService, PATH_PREFIX + "{sessionId}")
                .setAllowedOrigins("*"); // Adjust origin as needed
    }
}
