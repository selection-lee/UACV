package uacv.backend.hardware.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitmqProperties {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${RABBITMQ_USERNAME}")
    private String username;

    @Value("${RABBITMQ_PASSWORD}")
    private String password;

    private Map<String, DeviceConfig> devices = new HashMap<>();

    @Data
    public static class DeviceConfig {
        private String exchange = "amq.topic";
        private String queue;
        private String routingKey;
    }
}