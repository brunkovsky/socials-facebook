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

@Component("VIDEO")
public class VideoType extends FacebookType {

    private final ObjectMapper objectMapper;

    VideoType(ObjectMapper objectMapper) {
        super("videos/uploaded",
                "permalink_url,source,picture,title,description,created_time");
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
                        "https://facebook.com" + data.getPermalinkUrl(),
                        data.getTitle(),
                        data.getDescription(),
                        data.getPicture(),
                        null,
                        data.getSource(),
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
            private String source;
            private String picture;
            private String title;
            private String description;
            @JsonProperty("created_time")
            private Instant createdTime;
        }

    }

}
