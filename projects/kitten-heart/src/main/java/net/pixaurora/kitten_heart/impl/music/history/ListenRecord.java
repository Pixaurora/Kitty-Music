package net.pixaurora.kitten_heart.impl.music.history;

import java.lang.reflect.Type;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_heart.impl.config.DualSerializer;
import net.pixaurora.kitten_heart.impl.music.AlbumImpl;
import net.pixaurora.kitten_heart.impl.music.TrackImpl;
import net.pixaurora.kitten_heart.impl.scrobble.ScrobblerId;
import net.pixaurora.kitten_heart.impl.scrobble.scrobbler.Scrobbler;

public class ListenRecord {
    private final Track track;
    private final Optional<Album> album;
    private final Instant timestamp;

    private final Duration listenedDuration;
    private final Duration fullDuration;

    private final List<ScrobblerId> succeededScrobblers;

    public ListenRecord(Track track, Optional<Album> album, Instant timestamp, Duration listenedDuration,
            Duration fullDuration, List<ScrobblerId> succeededScrobblers) {
        this.track = track;
        this.album = album;
        this.timestamp = timestamp;
        this.listenedDuration = listenedDuration;
        this.fullDuration = fullDuration;
        this.succeededScrobblers = succeededScrobblers;
    }

    public ListenRecord(Track track, ListeningProgress progress) {
        this(track, track.album(), progress.startTime(), progress.amountPlayed(), track.duration(), new ArrayList<>());
    }

    public Track track() {
        return this.track;
    }

    public Optional<Album> album() {
        return this.album;
    }

    public Instant timestamp() {
        return this.timestamp;
    }

    public Duration listenedDuration() {
        return this.listenedDuration;
    }

    public Duration fullDuration() {
        return this.fullDuration;
    }

    public List<ScrobblerId> succeededScrobblers() {
        return this.succeededScrobblers;
    }

    public void succeededFor(Scrobbler scrobbler) {
        this.succeededScrobblers.add(scrobbler.id());
    }

    public static class Serializer implements DualSerializer<ListenRecord> {
        @Override
        public JsonElement serialize(ListenRecord record, Type typeOfSrc, JsonSerializationContext context) {
            JsonObject json = new JsonObject();

            json.add("track", context.serialize(record.track.path(), ResourcePath.class));

            if (record.album.isPresent()) {
                json.add("album", context.serialize(record.album.get().path(), ResourcePath.class));
            }

            json.add("timestamp", context.serialize(record.timestamp));

            json.add("listened_for", context.serialize(record.listenedDuration));

            json.add("full_time", context.serialize(record.fullDuration));

            json.add("succeeded", context.serialize(record.succeededScrobblers));

            return json;
        }

        @Override
        public ListenRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                throws JsonParseException {
            JsonObject object = json.getAsJsonObject();

            TrackImpl.FromPath trackFromPath = context.deserialize(object.get("track"), TrackImpl.FromPath.class);
            Track track = trackFromPath.transform();

            Optional<AlbumImpl.FromPath> albumFromPath = Optional.ofNullable(object.get("album"))
                    .map(album0 -> context.deserialize(album0, AlbumImpl.FromPath.class));
            Optional<Album> album = albumFromPath.map(AlbumImpl.FromPath::transform);

            Instant timestamp = context.deserialize(object.get("timestamp"), Instant.class);

            Duration listenedDuration = context.deserialize(object.get("listened_for"), Duration.class);

            Duration fullDuration = context.deserialize(object.get("full_time"), Duration.class);

            List<ScrobblerId> succeededScrobblers = new ArrayList<>();
            for (JsonElement scrobblerId : object.get("succeeded").getAsJsonArray()) {
                succeededScrobblers.add(context.deserialize(scrobblerId, ScrobblerId.class));
            }

            return new ListenRecord(track, album, timestamp, listenedDuration, fullDuration,
                    succeededScrobblers);
        }
    }
}
