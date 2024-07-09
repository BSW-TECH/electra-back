package com.electra.server.service.ws;

import com.electra.server.config.ws.WebSocketConfig;
import com.electra.server.exception.BadRequestException;
import com.electra.server.service.api.DeviceApi;
import com.electra.server.service.api.SessionApi;
import com.electra.server.service.data.DeviceData;
import com.electra.server.service.ws.handler.SessionHandler;
import com.electra.server.service.ws.utils.SessionIdPool;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.*;

import org.slf4j.Logger;

@Service
public class WebSocketService extends TextWebSocketHandler implements SessionApi {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketService.class);

    private final Map<Integer, SessionHandler> sessionHandlers = new HashMap<>();

    @Autowired
    private SessionIdPool sessionIdPool;

    @Autowired
    private DeviceApi deviceApi;

    @Override
    public Integer requestNewSession(String deviceKey) {
        final DeviceData deviceData = deviceApi.get(deviceKey);
        final Optional<List<String>> linkedUsers = deviceData.getLinkedUsers();
        if (linkedUsers.isEmpty()) {
            throw new BadRequestException("Device not registered to any user");
        }
        final Integer sessionId = this.sessionIdPool.acquire();
        sessionHandlers.put(sessionId, new SessionHandler(deviceKey, linkedUsers.get()));
        return sessionId;
    }

    @Override
    public void closeSession(Integer sessionId) {
        if (sessionHandlers.containsKey(sessionId)) {
            sessionHandlers.remove(sessionId);
            sessionIdPool.release(sessionId);
        } else {
            logger.warn("Unable to close sessionId:" + sessionId);
        }
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        logger.info("handleTextMessage");
        final Integer sessionId = getSessionId(session);
        if (sessionHandlers.containsKey(sessionId)) {
            sessionHandlers.get(sessionId).handleTextMessage(session, message);
        } else {
            logger.warn("Unable to handleTextMessage, no handler present for sessionId:" + sessionId);
        }

        // Parse the incoming JSON message
//        String payload = message.getPayload();
//        ObjectMapper mapper = new ObjectMapper();
//        JsonNode jsonNode = mapper.readTree(payload);
//        System.out.println("from mobile -> " + jsonNode.toString());
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        logger.info("afterConnectionEstablished");

        final Integer sessionId = getSessionId(session);
        if (sessionHandlers.containsKey(sessionId)) {
            sessionHandlers.get(sessionId).handleConnectionEstablished(session);
        } else {
            logger.warn("Unable to afterConnectionEstablished, no handler present for sessionId:" + sessionId);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        logger.info("afterConnectionClosed");

        final Integer sessionId = getSessionId(session);
        if (sessionHandlers.containsKey(sessionId)) {
            sessionHandlers.get(sessionId).handleConnectionClosed(session);
            if (sessionHandlers.get(sessionId).isSessionCloseable()) {
//                this.closeSession(sessionId);
            }
        } else {
            logger.warn("Unable to afterConnectionEstablished, no handler present for sessionId:" + sessionId);
        }
    }

    private Integer getSessionId(WebSocketSession session) {
        final String sessionPath = Objects.requireNonNull(session.getUri()).getPath();
        final String sessionId = sessionPath.replace(WebSocketConfig.PATH_PREFIX, "");
        return Integer.parseInt(sessionId);
    }

}
