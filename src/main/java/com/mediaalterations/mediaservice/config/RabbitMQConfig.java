package com.mediaalterations.mediaservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.process}")
    private String exchange;

    @Value("${rabbitmq.exchange.progress}")
    private String progressExchange;

    @Value("${rabbitmq.exchange.kill}")
    private String killExchange;

    @Value("${rabbitmq.queue.process}")
    private String queue;

    @Value("${rabbitmq.queue.process.routingKey}")
    private String routingKey;

    @Value("${rabbitmq.queue.kill.routingKey}")
    private String killRoutingKey;

    @Bean
    public Queue orderQueue() {
        // a durable queue is a queue whose metadata is stored on disk and that will
        // survive a broker (server) restart
        return QueueBuilder.durable(queue).build();
    }

    // Each instance creates its OWN anonymous queue and binds to the fanout
    // exchange
    @Bean
    public Queue killQueue() {
        // unique queue per instance, auto-deleted on shutdown
        return new AnonymousQueue();
    }

    // FanoutExchange, DirectExchange, HeadersExchange
    @Bean
    public TopicExchange orderExchange() {
        return new TopicExchange(exchange);
    }

    @Bean
    public TopicExchange progressExchange() {
        return new TopicExchange(progressExchange);
    }

    @Bean
    public FanoutExchange killExchange() {
        return new FanoutExchange(killExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(orderQueue())
                .to(orderExchange())
                .with(routingKey);
    }

    @Bean
    public Binding killBinding() {
        return BindingBuilder
                .bind(killQueue())
                .to(killExchange());
    }

    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

}
