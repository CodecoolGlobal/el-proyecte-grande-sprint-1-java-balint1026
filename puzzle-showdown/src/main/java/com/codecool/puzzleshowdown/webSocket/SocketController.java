package com.codecool.puzzleshowdown.webSocket;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.codecool.puzzleshowdown.repository.model.User;
import com.codecool.puzzleshowdown.service.RaceService;
import com.codecool.puzzleshowdown.service.UserService;
import com.codecool.puzzleshowdown.stateFul.ActiveRace;
import com.codecool.puzzleshowdown.stateFul.GameState;
import com.codecool.puzzleshowdown.stateFul.PlayerInActiveRace;
import com.codecool.puzzleshowdown.utils.EloCalculator;
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

    private final RaceService raceService;

    private final UserService userService;

    public SocketController(RaceService raceService, UserService userService) {
        this.raceService = raceService;
        this.userService = userService;
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
                        new HashSet<>(List.of(new PlayerInActiveRace(socketSession, body.getString("username"), body.getString("userId")))),
                        new HashSet<>(),
                        Integer.parseInt(body.getString("timeframe")),
                        new GameState(),
                        rand.nextInt(100),
                        rand.nextInt(15) + 5
                );

                raceService.addActiveRace(raceId, spectateId, newRace);

                sendSocketMessage(socketSession, new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                        "{\"raceId\": \""+newRace.raceId()+"\", \"spectateId\": \""+newRace.spectateId()+"\"}"
                ));
                break;

            case "getPlayersInRace":
                String usernamesToSend = "[";
                for (PlayerInActiveRace player : raceService.getActiveRaceByRaceId(body.getString("raceId")).players()) {
                    usernamesToSend += "[\"" + player.username() + "\", \"" + player.userId() + "\"],";
                }
                usernamesToSend = usernamesToSend.substring(0, usernamesToSend.length() - 1) + "]";
                sendSocketMessage(socketSession, new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                        "{\"players\": "+usernamesToSend+"}"
                ));
                break;

            case "joinRace":
                if (!raceService.isRaceIdValid(body.getString("raceId"))) {
                    throwError(socketSession);
                    break;
                }

                if (!raceService.getActiveRaceByRaceId(body.getString("raceId")).gameState().isPending()) {
                    throwError(socketSession);
                    break;
                }

                raceService.addPlayerToActiveRace(body.getString("raceId"), socketSession, body.getString("username"), body.getString("userId"));
                broadcastSocketMessageToPlayers(body.getString("raceId"), new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                        "{\"username\": \""+body.getString("username")+"\", \"userId\": \""+body.getString("userId")+"\"}"
                ));
                break;

            case "startRace":
                if (!raceService.isRaceIdValid(body.getString("raceId"))) {
                    throwError(socketSession);
                    break;
                }

                raceService.startActiveRace(body.getString("raceId"));

                broadcastSocketMessageToPlayers(body.getString("raceId"), new SocketDTO("startCountdown", request.get("identifier").toString(), "{}"));

                JSONObject finalBody = body;
                new Timer(true).schedule(new TimerTask() {
                    @Override
                    public void run() {
                        try {
                            broadcastSocketMessageToPlayers(finalBody.getString("raceId"), new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                                    "{\"startAt\": \""+System.currentTimeMillis()+"\", \"raceLength\": \""+raceService.getActiveRaceByRaceId(finalBody.getString("raceId")).timeframe()+"\", \"first\": \""+raceService.getActiveRaceFirstPuzzle(finalBody.getString("raceId"))+"\", \"step\": \""+raceService.getActiveRacePuzzleStep(finalBody.getString("raceId"))+"\"}"
                            ));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, 5000);
                break;

            case "completeRacePuzzle":
                broadcastSocketMessageToPlayers(body.getString("raceId"), new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(),
                        "{\"success\": \""+body.getString("success")+"\", \"userId\": \""+body.getString("userId")+"\"}"
                ));
                break;

            case "handleRaceEnd":
                //sendSocketMessage(socketSession, new SocketDTO(request.getString("endpoint"), request.get("identifier").toString(), "{\"valami\": \"semmi\"}"));
                if (raceService.isRaceOver(body.getString("raceId"))) {
                    break;
                }
                raceService.setRaceOver(body.getString("raceId"));
               String[] userIdString = body.getString("names").split("&");
               long[] userIdLong = new long[userIdString.length];
                for (int i = 0; i < userIdString.length;i++) {
                    userIdLong[i] = Long.parseLong(userIdString[i]);
                }

               double[] ratings = new double[userIdLong.length];
               User[] users = new User[userIdLong.length];
                System.out.println(Arrays.toString(userIdLong));
               for (int i = 0; i < userIdLong.length; i++) {
                   users[i] = userService.getUser(userIdLong[i]);
                   ratings[i] = users[i].getRating();
               }
                double[] newRatings = EloCalculator.calculate(ratings);
                for (int i = 0; i < newRatings.length; i++) {
                    userService.patchRating(userIdLong[i], (int) newRatings[i]);
                    System.out.println(newRatings[i]);
                }
                broadcastSocketMessageToPlayers(body.getString("raceId"), new SocketDTO("notifyGameOver", "0", "{}"));
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
