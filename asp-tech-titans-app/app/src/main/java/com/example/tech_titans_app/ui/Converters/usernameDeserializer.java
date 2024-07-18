package com.example.tech_titans_app.ui.Converters;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/*
This class is used to deserialize a list of usernames from a JSON object.
The userLikes and userUnlikes fields in the json received converts to a list of usernames.
*/

public class usernameDeserializer implements JsonDeserializer<List<String>> {
    @Override
    public List<String> deserialize(JsonElement json, Type typeOfT,
                                    JsonDeserializationContext context) throws JsonParseException {
        List<String> usernames = new ArrayList<>();
        JsonArray jsonArray = json.getAsJsonArray();
        for (JsonElement element : jsonArray) {
            JsonObject userObject = element.getAsJsonObject();
            String username = userObject.get("username").getAsString();
            usernames.add(username);
        }
        return usernames;
    }
}
