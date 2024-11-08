package net.pixaurora.kitten_heart.impl.listener;

import java.io.IOException;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackMiddleEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class ScrobblingMusicListener implements MusicEventListener {
    @Override
    public void onTrackStart(TrackStartEvent event) {
        if (!event.record().isPresent()) {
            return;
        }

        ListenRecord record = event.record().get();

        KitTunes.SCROBBLER_CACHE.execute(
                scrobblers -> scrobblers.startScrobbling(KitTunes.CLIENT, record));
    }

    @Override
    public void onTrackMiddleReached(TrackMiddleEvent event) {
        if (!event.record().isPresent()) {
            return;
        }

        ListenRecord record = event.record().get();

        KitTunes.SCROBBLER_CACHE
                .execute(scrobblers -> scrobblers.completeScrobbling(KitTunes.CLIENT, record));
    }

    @Override
    public void onTrackEnd(TrackEndEvent event) {
        if (!event.record().isPresent()) {
            return;
        }

        ListenRecord record = event.record().get();

        KitTunes.LISTEN_HISTORY.execute(history -> history.record(record));
        try {
            KitTunes.LISTEN_HISTORY.save();
        } catch (IOException e) {
            KitTunes.LOGGER.error("Failed to save listen history while scrobbling!", e);
        }
    }
}
