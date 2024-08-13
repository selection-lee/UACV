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
@ConfigurationProperties(prefix = "spring.rabbitmq")
public class RabbitmqProperties {

    // spring 에서  yml 꺼를 자동 mapping
    private String host;

    private int port;

    private String username;

    private String password;

//    @Value("${spring.rabbitmq.exchange}")
    private String exchange;

    private Map<String, QueueConfig> queues = new HashMap<>();

    @Data
    public static class QueueConfig {
        private String name;
        private List<String> routingKeys;
    }
}