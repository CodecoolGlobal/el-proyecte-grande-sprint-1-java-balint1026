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
public class SocketControllerPuzzle extends TextWebSocketHandler {

    public void handleTextMessage(WebSocketSession session, TextMessage input) throws InterruptedException, IOException {
        JSONObject request = new JSONObject(input.getPayload());

        JSONObject body = new JSONObject();
        if (!request.get("body").equals("null")) {
            body = new JSONObject(request.get("body").toString());
        }

        //System.out.println(body.get("lol"));

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        switch (request.getString("endpoint")) {
            /*case "getPuzzle":
                session.sendMessage(new TextMessage(objectWriter.writeValueAsString(new SocketDTO("getPuzzle", objectWriter.writeValueAsString(puzzleService.getPuzzle())))));
                break;*/
            case "test":
                session.sendMessage(new TextMessage(objectWriter.writeValueAsString(new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(), "Hello World"))));
                break;
        }
    }

}
