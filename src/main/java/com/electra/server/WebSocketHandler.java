package com.electra.server;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

    private Optional<WebSocketSession> mobileSession = Optional.empty();
    private Optional<WebSocketSession> deviceSession = Optional.empty();


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Parse the incoming JSON message
        String payload = message.getPayload();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(payload);

        if (mobileSession.isPresent() && deviceSession.isPresent()) {
//            System.out.println(jsonNode.toString());
            if (mobileSession.get().equals(session)) {
                deviceSession.get().sendMessage(message);
                System.out.println("from mobile -> " + jsonNode.toString());
            } else {
                System.out.println("from device -> " + jsonNode.toString());
                mobileSession.get().sendMessage(message);
            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("afterConnectionEstablished " + session);
        // Store session for later use
        if (mobileSession.isEmpty()) {
            mobileSession = Optional.of(session);
        }
        else
        {
            deviceSession = Optional.of(session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        System.out.println("afterConnectionClosed " + session + " " + status);
        mobileSession = Optional.empty();
        deviceSession = Optional.empty();
    }
}
