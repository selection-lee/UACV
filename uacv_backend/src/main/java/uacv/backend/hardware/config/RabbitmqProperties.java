package uacv.backend.hardware.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitmqProperties {

    @Value("${RABBITMQ_HOST}")
    private String host;

    @Value("${RABBITMQ_PORT}")
    private int port;

    @Value("${RABBITMQ_USERNAME}")
    private String username;

    @Value("${RABBITMQ_PASSWORD}")
    private String password;

    @Value("${spring.rabbitmq.exchange}")
    private String exchangeName;

    private Map<String, QueueConfig> queues = new HashMap<>();

    @Data
    public static class QueueConfig {
        private String queueName;
        private List<String> routingKeys;
    }
}