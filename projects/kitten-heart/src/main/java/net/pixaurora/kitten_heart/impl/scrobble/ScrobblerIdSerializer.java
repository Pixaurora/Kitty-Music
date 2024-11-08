package net.pixaurora.kitten_heart.impl.scrobble;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.api.scrobble.ScrobblerId;
import net.pixaurora.kitten_heart.impl.config.DualSerializer;

public class ScrobblerIdSerializer implements DualSerializer<ScrobblerId> {
    @Override
    public JsonElement serialize(ScrobblerId src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject json = new JsonObject();

        json.addProperty("username", src.username());
        json.addProperty("scrobbler_type", src.scrobblerType());

        return json;
    }

    @Override
    public ScrobblerId deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject object = json.getAsJsonObject();

        String username = object.getAsJsonPrimitive("username").getAsString();
        String scrobblerType = object.getAsJsonPrimitive("scrobbler_type").getAsString();

        return new ScrobblerId(username, scrobblerType);
    }
}
