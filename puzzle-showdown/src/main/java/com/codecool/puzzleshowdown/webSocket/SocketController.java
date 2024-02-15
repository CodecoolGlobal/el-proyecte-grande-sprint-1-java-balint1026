package com.codecool.puzzleshowdown.webSocket;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class SocketController extends TextWebSocketHandler {

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public void handleTextMessage(WebSocketSession socketSession, TextMessage input) throws IOException {
        JSONObject request = new JSONObject(input.getPayload());

        JSONObject body = new JSONObject();
        if (!request.get("body").equals("null")) {
            body = new JSONObject(request.get("body").toString());
        }

        // Get key of body
        //System.out.println(body.get("lol"));

        switch (request.getString("endpoint")) {
            case "test":
                sendSocketMessage(socketSession, new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(), "{\"valami\": \"semmi\"}"));
                break;
        }
    }

    public void sendSocketMessage(WebSocketSession socketSession, SocketDTO message) throws IOException {
        socketSession.sendMessage(new TextMessage(objectWriter.writeValueAsString(message)));
    }
}
