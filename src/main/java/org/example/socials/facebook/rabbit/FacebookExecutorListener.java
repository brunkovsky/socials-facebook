package org.example.socials.facebook.rabbit;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.socials.facebook.service.FacebookService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableRabbit
@AllArgsConstructor
public class FacebookExecutorListener {

    private final FacebookService facebookService;

    @RabbitListener(queues = "facebook-executor")
    public void catchMessage(String message) {
        log.debug("catching executing message: {}", message);
        new Thread(() -> facebookService.fetch(message)).start();
    }

}
