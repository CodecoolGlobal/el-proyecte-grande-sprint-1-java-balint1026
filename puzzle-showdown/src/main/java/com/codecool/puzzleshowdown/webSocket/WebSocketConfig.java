package com.codecool.puzzleshowdown.webSocket;

import com.codecool.puzzleshowdown.service.RaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RaceService raceService;
    @Autowired
    public WebSocketConfig(RaceService raceService) {
        this.raceService = raceService;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketController(raceService), "/ws");
    }

}
