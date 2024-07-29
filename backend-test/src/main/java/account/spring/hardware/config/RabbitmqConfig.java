package account.spring.hardware.config;

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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {

    @Value("${spring.rabbitmq.host}")
    private String host;

    @Value("${spring.rabbitmq.port}")
    private int port;

    @Value("${RABBITMQ_USERNAME}")
    private String username;

    @Value("${RABBITMQ_PASSWORD}")
    private String password;

    static final String exchange_name = "amp.topic";
    static final String queue_name = "sensor_queue";
    static final String routing_key = "raspberry.#";

    @Bean
    TopicExchange topicExchange() {
        // return new TopicExchange(exchange_name);
        return ExchangeBuilder.topicExchange(exchange_name)
                .ignoreDeclarationExceptions().build();
    }

    @Bean
    Queue queue() {
        return new Queue(queue_name);
    }

    @Bean
    Binding binding(TopicExchange topicExchange, Queue queue) {
        return BindingBuilder.bind(queue)
                .to(topicExchange)
                .with(routing_key);
    }

    @Bean
    ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
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