package com.codecool.puzzleshowdown.webSocket;

import com.codecool.puzzleshowdown.service.RaceService;
import com.codecool.puzzleshowdown.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final RaceService raceService;
    private final UserService userService;
    @Autowired
    public WebSocketConfig(RaceService raceService, UserService userService) {
        this.raceService = raceService;
        this.userService = userService;
    }

    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketController(raceService, userService), "/ws").setAllowedOrigins("*");
    }

}
