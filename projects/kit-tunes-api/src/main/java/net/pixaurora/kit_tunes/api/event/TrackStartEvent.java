package net.pixaurora.kit_tunes.api.event;

import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.ListeningProgress;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;

public interface TrackStartEvent {
    public Optional<Track> track();

    public Optional<ListenRecord> record();

    public ResourcePath searchPath();

    public ListeningProgress progress();
}
