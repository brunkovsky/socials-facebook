package org.example.socials.facebook.connector;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.socials.facebook.model.FacebookAccount;
import org.example.socials.facebook.model.SocialItem;

import java.util.List;

@Data
@AllArgsConstructor
public abstract class FacebookType {

    private String endPoint;
    private String fields;
    private String type;

    FacebookType(String endPoint, String fields) {
        this.endPoint = endPoint;
        this.fields = fields;
    }

    public abstract List<SocialItem> parseResult(String json, FacebookAccount facebookAccount);

}
