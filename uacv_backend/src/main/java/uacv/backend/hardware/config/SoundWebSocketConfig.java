package uacv.backend.hardware.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class SoundWebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/orin"); // topic
        config.setApplicationDestinationPrefixes("/sensor"); // app
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/socket/sound")
                .setAllowedOrigins("http://i11c202.p.ssafy.io")
                .withSockJS();
    }

    @Bean
    public Queue sensorQueue() {
//        return new Queue("sensor_queue", true);
        return new Queue("sensor_queue", true, false, false);
    }

}
