package uacv.backend.hardware.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
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

@Configuration
public class RabbitmqConfig {

    @Autowired
    private RabbitmqProperties rabbitmqProperties;

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(rabbitmqProperties.getExchange());
    }

    @Bean
    public Map<String, Queue> queues() {
        Map<String, Queue> queues = new HashMap<>();
        for (Map.Entry<String, RabbitmqProperties.QueueConfig> entry : rabbitmqProperties.getQueues().entrySet()) {
            String queueName = entry.getKey();
            RabbitmqProperties.QueueConfig config = entry.getValue();
            queues.put(queueName, new Queue(config.getName()));
        }
        return queues;
    }

    @Bean
    public Map<String, Binding> bindings(TopicExchange exchange) {
        Map<String, Binding> bindings = new HashMap<>();
        for (Map.Entry<String, RabbitmqProperties.QueueConfig> entry : rabbitmqProperties.getQueues().entrySet()) {
            String queueName = entry.getKey();
            RabbitmqProperties.QueueConfig config = entry.getValue();
            Queue queue = queues().get(queueName);
            for (String routingKey : config.getRoutingKeys()) {
                String bindingKey = queueName + "." + routingKey;
                bindings.put(bindingKey, BindingBuilder.bind(queue).to(exchange).with(routingKey));
            }
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