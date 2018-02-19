package de.hpi.parser.config;

import de.hpi.parser.config.queue.RabbitReceiver;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitConfig {

    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private RabbitReceiver receiver;
    @Getter(AccessLevel.PRIVATE) @Setter(AccessLevel.PRIVATE) private RabbitTemplate template;
    @Getter(AccessLevel.PRIVATE) private static final String QUEUE_NAME = "matchingDoneMessages";

    @Autowired
    public RabbitConfig(RabbitReceiver receiver, RabbitTemplate template) {
        setReceiver(receiver);
        setTemplate(template);
    }

    @Bean
    Queue queue() {
        Map<String, Object> args = new HashMap<>();
        args.put("x-overflow", "reject-publish");
        return new Queue(getQUEUE_NAME(), true, false, false, args);
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory, MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(getQUEUE_NAME());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    MessageListenerAdapter listenerAdapter(RabbitReceiver receiver) {
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }
}