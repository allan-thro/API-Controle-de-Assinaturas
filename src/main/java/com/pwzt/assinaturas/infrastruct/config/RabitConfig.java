package com.pwzt.assinaturas.infrastruct.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration

public class RabitConfig {
    public static final String NOME_FILA = "fila_eventos";

    @Bean
    public Queue queue() {
        return new Queue(NOME_FILA, true);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

}
