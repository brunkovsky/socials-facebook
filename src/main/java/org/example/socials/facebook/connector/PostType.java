package org.example.socials.facebook.connector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import org.example.socials.facebook.model.FacebookAccount;
import org.example.socials.facebook.model.SocialItem;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component("POST")
public class PostType extends FacebookType {

    private final ObjectMapper objectMapper;

    PostType(ObjectMapper objectMapper) {
        super("posts",
                "permalink_url,full_picture,message,description,created_time");
        this.objectMapper = objectMapper;
    }

    @Override
    @SneakyThrows
    public List<SocialItem> parseResult(String json, FacebookAccount facebookAccount) {
        FacebookResponse facebookResponse = objectMapper.readValue(json, FacebookResponse.class);
        return facebookResponse.getData()
                .stream()
                .map(data -> new SocialItem(
                        data.getId(),
                        "FACEBOOK",
                        facebookAccount.getAccountName(),
                        data.getPermalinkUrl(),
                        data.getMessage(),
                        data.getDescription(),
                        null,
                        data.getFullPicture(),
                        null,
                        data.getCreatedTime(),
                        facebookAccount.isApprovedByDefault()))
                .collect(Collectors.toList());
    }

    @Data
    static class FacebookResponse {

        private List<FacebookData> data;

        @Data
        static class FacebookData {
            String id;
            @JsonProperty("permalink_url")
            private String permalinkUrl;
            @JsonProperty("full_picture")
            private String fullPicture;
            private String message;
            private String description;
            @JsonProperty("created_time")
            private Instant createdTime;
        }
    }

}
