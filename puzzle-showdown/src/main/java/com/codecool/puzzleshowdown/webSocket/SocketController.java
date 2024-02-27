package com.codecool.puzzleshowdown.webSocket;

import java.io.IOException;

import java.util.HashSet;
import java.util.List;
import java.util.Random;

import com.codecool.puzzleshowdown.service.RaceService;
import com.codecool.puzzleshowdown.stateFul.ActiveRace;
import com.codecool.puzzleshowdown.stateFul.PlayerInActiveRace;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import static java.lang.Integer.parseInt;

@Component
public class SocketController extends TextWebSocketHandler {

    private final ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

    private final RaceService raceService;

    public SocketController(RaceService raceService) {
        this.raceService = raceService;
    }

    public void handleTextMessage(WebSocketSession socketSession, TextMessage input) throws IOException {
        JSONObject request = new JSONObject(input.getPayload());

        JSONObject body = new JSONObject();
        if (!request.get("body").equals("null")) {
            body = new JSONObject(request.get("body").toString());
        }

        switch (request.getString("endpoint")) {
            case "test":
                sendSocketMessage(socketSession, new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(), "{\"valami\": \"semmi\"}"));
                break;

            case "createRace":
                Random rand = new Random();
                String raceId = Long.toString(System.currentTimeMillis() + rand.nextInt(10000));
                String spectateId = Long.toString(System.currentTimeMillis() + rand.nextInt(10000));

                ActiveRace newRace = new ActiveRace(
                        raceId,
                        spectateId,
                        body.getString("username"),
                        new HashSet<>(List.of(new PlayerInActiveRace(socketSession, body.getString("username")))),
                        new HashSet<>(),
                        Integer.parseInt(body.getString("timeframe"))
                );

                raceService.addActiveRace(raceId, spectateId, newRace);

                sendSocketMessage(socketSession, new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                        "{\"raceId\": "+newRace.raceId()+", \"spectateId\": "+newRace.spectateId()+"}"
                ));
                break;

            case "getPlayersInRace":
                if (!raceService.isRaceIdValid(body.getString("raceId"))) {
                    throwError(socketSession);
                    break;
                }

                String usernamesToSend = "[";
                for (PlayerInActiveRace player : raceService.getActiveRaceByRaceId(body.getString("raceId")).players()) {
                    usernamesToSend += "\"" + player.username() + "\",";
                }
                usernamesToSend = usernamesToSend.substring(0, usernamesToSend.length() - 2) + "\"]";
                sendSocketMessage(socketSession, new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                        "{\"players\": "+usernamesToSend+"}"
                ));
                break;

            case "joinRace":
                if (!raceService.isRaceIdValid(body.getString("raceId"))) {
                    throwError(socketSession);
                    break;
                }

                raceService.addPlayerToActiveRace(body.getString("raceId"), socketSession, body.getString("username"));
                broadcastSocketMessageToPlayers(body.getString("raceId"), new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                        "{\"username\": \""+body.getString("username")+"\"}"
                ));
                break;
        }
    }

    public void sendSocketMessage(WebSocketSession socketSession, SocketDTO message) throws IOException {
        socketSession.sendMessage(new TextMessage(objectWriter.writeValueAsString(message)));
    }

    public void broadcastSocketMessageToPlayers(String raceId, SocketDTO message) throws IOException {
        for (PlayerInActiveRace player : raceService.getActiveRaceByRaceId(raceId).players()) {
            player.socketSession().sendMessage(new TextMessage(objectWriter.writeValueAsString(message)));
        }
    }

    public void broadcastSocketMessageToPlayersExceptOne(String raceId, WebSocketSession exceptionPlayer, SocketDTO message) throws IOException {
        for (PlayerInActiveRace player : raceService.getActiveRaceByRaceId(raceId).players()) {
            if (!player.socketSession().toString().equals(exceptionPlayer.toString())) {
                player.socketSession().sendMessage(new TextMessage(objectWriter.writeValueAsString(message)));
            }
        }
    }

    private void throwError(WebSocketSession socketSession) throws IOException {
        sendSocketMessage(socketSession, new SocketDTO("error", "0", "{}"));
    }
}
