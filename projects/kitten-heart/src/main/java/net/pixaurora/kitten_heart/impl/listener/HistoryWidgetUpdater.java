package net.pixaurora.kitten_heart.impl.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import net.pixaurora.kit_tunes.api.event.TrackEndEvent;
import net.pixaurora.kit_tunes.api.event.TrackStartEvent;
import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.music.history.ListenHistory;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;
import net.pixaurora.kitten_heart.impl.ui.widget.history.HistoryWidget;

public class HistoryWidgetUpdater implements MusicEventListener {
    public static final AtomicReference<HistoryWidget> LISTENING_WIDGET = new AtomicReference<>();

    public static List<ListenRecord> recentTracks(int limit) {
        ArrayList<ListenRecord> recentTracks = new ArrayList<>();

        int count = 0;

        outer: for (List<ListenRecord> records : Arrays.asList(currentTracks(), pastTracks())) {
            int lastIndex = records.size() - 1;

            for (int i = 0; i < records.size(); i++) {
                ListenRecord record = records.get(lastIndex - i);

                count += 1;

                recentTracks.add(record);

                if (limit <= count) {
                    break outer;
                }
            }
        }

        return recentTracks;
    }

    private static List<ListenRecord> currentTracks() {
        ArrayList<ListenRecord> currentTracks = new ArrayList<>();

        for (PlayingSong song : EventHandling.playingSongs()) {
            if (!song.record().isPresent()) {
                continue;
            }

            currentTracks.add(song.record().get());
        }

        return currentTracks;
    }

    private static List<ListenRecord> pastTracks() {
        return KitTunes.LISTEN_HISTORY.get(ListenHistory::getHistory);
    }

    @Override
    public void onTrackStart(TrackStartEvent event) {
        HistoryWidget listeningWidget = LISTENING_WIDGET.get();

        if (listeningWidget != null) {
            listeningWidget.update();
        }
    }

    @Override
    public void onTrackEnd(TrackEndEvent event) {
        HistoryWidget listeningWidget = LISTENING_WIDGET.get();

        if (listeningWidget != null) {
            listeningWidget.update();
        }
    }
}
