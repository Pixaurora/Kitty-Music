package net.pixaurora.kitten_heart.impl.listener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import net.pixaurora.kit_tunes.api.listener.MusicEventListener;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.music.history.ListenHistory;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;

public class HistoryWidgetUpdater implements MusicEventListener {
    public static List<ListenRecord> recentTracks(int limit) {
        ArrayList<ListenRecord> recentTracks = new ArrayList<>();

        int count = 0;

        outer: for (Collection<ListenRecord> records : Arrays.asList(currentTracks(), pastTracks())) {
            for (ListenRecord record : records) {
                count += 1;

                recentTracks.add(record);

                if (limit <= count) {
                    break outer;
                }
            }
        }

        return recentTracks;
    }

    private static Collection<ListenRecord> currentTracks() {
        ArrayList<ListenRecord> currentTracks = new ArrayList<>();

        for (PlayingSong song : EventHandling.playingSongs()) {
            if (!song.record().isPresent()) {
                continue;
            }

            currentTracks.add(song.record().get());
        }

        return currentTracks;
    }

    private static Collection<ListenRecord> pastTracks() {
        return KitTunes.LISTEN_HISTORY.get(ListenHistory::getHistory);
    }
}
