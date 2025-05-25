package com.scrooge.alddeulticon.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic"); // 클라이언트 구독 prefix
        config.setApplicationDestinationPrefixes("/app"); // 클라이언트가 보낼 prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/notifications") // WebSocket 연결 경로
                .setAllowedOriginPatterns("*")    // CORS 허용
                .withSockJS();                    // Flutter에서도 대응 가능
    }
}
