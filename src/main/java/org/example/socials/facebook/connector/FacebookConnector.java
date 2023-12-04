package org.example.socials.facebook.connector;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.socials.facebook.model.FacebookAccount;
import org.example.socials.facebook.model.SocialItem;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class FacebookConnector {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private final RestTemplate restTemplate = new RestTemplate(); // TODO: to use bean creation using restTemplateBuilder?


    @SneakyThrows
    public void loadData(FacebookAccount facebookAccount, FacebookType facebookType) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity(generateUrl(facebookAccount, facebookType), String.class);
        if (forEntity.getStatusCode().isError()) {
            log.error("ERROR");
            return;
        }
        List<SocialItem> socialItems = facebookType.parseResult(forEntity.getBody(), facebookAccount);
        log.debug("Got {} items from facebook", socialItems.size());
        rabbitTemplate.convertAndSend("facebook-socials-storage",
                "facebook-routing",
                objectMapper.writeValueAsString(socialItems));
    }

    private String generateUrl(FacebookAccount facebookAccount, FacebookType facebookType) {
        return "https://graph.facebook.com/v15.0/" +
                facebookAccount.getGroupId() +
                "/" + facebookType.getEndPoint() +
                "?access_token=" + facebookAccount.getAccessToken() +
                "&fields=" + facebookType.getFields() +
                "&type=" + facebookType.getType();
    }

}
