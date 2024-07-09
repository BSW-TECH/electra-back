package com.electra.server.service.ws.handler;

import com.electra.server.service.ws.WebSocketService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class SessionHandler {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private Optional<WebSocketSession> deviceSession = Optional.empty();
    private Optional<WebSocketSession> applicationSession = Optional.empty();

    private final String devicePrincipal;
    private final List<String> applicationPrincipals;

    public SessionHandler(String deviceKey, List<String> linkedUsers) {
        this.devicePrincipal = deviceKey;
        this.applicationPrincipals = linkedUsers;
    }

    public void handleConnectionEstablished(WebSocketSession session) {
        if (deviceSession.isEmpty()) {
            logger.info("Device Session new Connection: " + session);
            deviceSession = Optional.of(session);
        } else if (applicationSession.isEmpty()) {
            logger.info("Application Session new Connection: " + session);
            applicationSession = Optional.of(session);
        } else {
            logger.warn("Unable to handle new connection: " + session);
        }
    }

    public void handleConnectionClosed(WebSocketSession session) {
        if (deviceSession.isPresent() && deviceSession.get().getId().equals(session.getId())) {
            logger.info("Device closed Connection: " + session);
            deviceSession = Optional.empty();
        } else if (applicationSession.isPresent() && applicationSession.get().getId().equals(session.getId())) {
            logger.info("Application closed Connection: " + session);
            deviceSession = Optional.empty();
        } else {
            logger.warn("Unable to handle closed connection: " + session);
        }
    }

    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        if (deviceSession.isPresent() && applicationSession.isPresent()) {
            if (deviceSession.get().getId().equals(session.getId())) {
                applicationSession.get().sendMessage(message);
                logger.info("handleMessage device -> application");
            } else if (applicationSession.get().getId().equals(session.getId())) {
                deviceSession.get().sendMessage(message);
                logger.info("handleMessage application -> device");
            } else {
                logger.warn("Unable to handleTextMessage ");
            }
        } else {
            logger.warn("Missing one client");
        }
    }

    public Boolean isSessionCloseable() {
        return deviceSession.isEmpty() && applicationSession.isEmpty();
    }
}
