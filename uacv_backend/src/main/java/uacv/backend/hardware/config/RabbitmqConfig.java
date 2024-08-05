package uacv.backend.hardware.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class RabbitmqConfig {

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Autowired
    private Environment env;

    @Bean
    public Map<String, TopicExchange> topicExchanges() {
        Map<String, TopicExchange> exchanges = new HashMap<>();

        for (Map.Entry<String, RabbitmqProperties.DeviceConfig> entry : rabbitmqProperties.getDevices().entrySet()) {
            String deviceName = entry.getKey();
            RabbitmqProperties.DeviceConfig config = entry.getValue();
            exchanges.put(deviceName, ExchangeBuilder.topicExchange(config.getExchange())
                    .ignoreDeclarationExceptions().build());
        }
        return exchanges;
    }

    @Bean
    public Map<String, Queue> queues() {
        Map<String, Queue> queues = new HashMap<>();
        for (Map.Entry<String, RabbitmqProperties.DeviceConfig> entry : rabbitmqProperties.getDevices().entrySet()) {
            String deviceName = entry.getKey();
            RabbitmqProperties.DeviceConfig config = entry.getValue();
            queues.put(deviceName, new Queue(config.getQueue()));
        }
        return queues;
    }

    @Bean
    public Map<String, Binding> bindings(Map<String, TopicExchange> exchanges, Map<String, Queue> queues) {
        Map<String, Binding> bindings = new HashMap<>();
        for (Map.Entry<String, RabbitmqProperties.DeviceConfig> entry : rabbitmqProperties.getDevices().entrySet()) {
            String deviceName = entry.getKey();
            RabbitmqProperties.DeviceConfig config = entry.getValue();
            bindings.put(deviceName, BindingBuilder.bind(queues.get(deviceName))
                    .to(exchanges.get(deviceName))
                    .with(config.getRoutingKey()));
        }
        return bindings;
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(rabbitmqProperties.getHost());
        connectionFactory.setPort(rabbitmqProperties.getPort());
        connectionFactory.setUsername(rabbitmqProperties.getUsername());
        connectionFactory.setPassword(rabbitmqProperties.getPassword());
        return connectionFactory;
    }

    @Bean
    MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();

    }

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter messageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(messageConverter);
        return rabbitTemplate;
    }

}