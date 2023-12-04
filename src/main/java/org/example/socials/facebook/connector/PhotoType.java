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

@Component("PHOTO")
public class PhotoType extends FacebookType {

    private final ObjectMapper objectMapper; // todo to move to parent

    PhotoType(ObjectMapper objectMapper) {
        super("photos",
                "link,images,name,created_time");
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
                        data.getLink(),
                        data.getName(),
                        null,
                        data.getImages().get(data.getImages().size() - 1).getSource(),
                        data.getImages().get(0).getSource(),
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
            private String link;
            private List<FacebookDataImage> images;
            private String name;
            @JsonProperty("created_time")
            private Instant createdTime;

            @Data
            static class FacebookDataImage {
                private Integer height;
                private String source;
                private Integer width;
            }
        }

    }

}
