package org.example.socials.facebook.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AMQPConfig {

//    @Bean
//    DirectExchange storageExchange(@Value("${socials.rabbit.exchange.storage.name}") String exchangeName) {
//        return new DirectExchange(exchangeName);
//    }


    @Bean
    public DirectExchange facebookSocialsStorageExchange() {
            return new DirectExchange("facebook-socials-storage");
    }

    @Bean
    public Queue socialsStorageQueue() {
        return new Queue("socials-storage");
    }

    @Bean
    public Binding facebookStorageBinding(DirectExchange facebookSocialsStorageExchange, Queue socialsStorageQueue) {
        return BindingBuilder.bind(socialsStorageQueue)
                .to(facebookSocialsStorageExchange)
                .with("facebook-routing");
    }

}
