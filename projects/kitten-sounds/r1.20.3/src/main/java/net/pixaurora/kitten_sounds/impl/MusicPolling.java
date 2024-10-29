package net.pixaurora.kitten_sounds.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.ChannelAccess;
import net.minecraft.client.sounds.SoundEventListener;
import net.minecraft.client.sounds.WeighedSoundEvents;
import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import net.minecraft.sounds.SoundSource;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.music.progress.PolledListeningProgress;
import net.pixaurora.kitten_heart.impl.music.progress.SongProgressTracker;

public class MusicPolling implements SoundEventListener {
    public static List<PolledSong<SoundInstance>> TRACKS_TO_POLL = new ArrayList<>();
    public static List<PolledSong<ChannelHandle>> POLLED_TRACKS = new ArrayList<>();

    @Override
    public void onPlaySound(SoundInstance sound, WeighedSoundEvents soundSet, float range) {
        SoundSource source = sound.getSource();
        if (source == SoundSource.MUSIC || source == SoundSource.RECORDS) {
            MusicControlsImpl controls = new MusicControlsImpl();

            PolledListeningProgress progress = EventHandling.handleTrackStart(
                    SoundEventsUtils.minecraftTypeToInternalType(sound.getSound().getPath()), controls);

            TRACKS_TO_POLL.add(new PolledSong<SoundInstance>(sound, progress, controls));
        }
    }

    public static void pollTrackProgress(Map<SoundInstance, ChannelAccess.ChannelHandle> instanceToChannel) {
        TRACKS_TO_POLL.removeIf((polledSong) -> {
            Optional<ChannelHandle> channel = Optional.ofNullable(instanceToChannel.get(polledSong.polled()));

            if (channel.isPresent()) {
                ChannelHandle channelHandle = channel.get();

                polledSong.controls().channel(channelHandle);
                POLLED_TRACKS.add(new PolledSong<>(channelHandle, polledSong));

                return true;
            } else {
                return false;
            }
        });

        POLLED_TRACKS.removeIf((polledSong) -> {
            if (polledSong.polled().isStopped()) {
                EventHandling.handleTrackEnd(polledSong.progress());

                return true;
            } else {
                polledSong.polled().execute(
                        channel -> polledSong.progress().measureProgress((SongProgressTracker) (Object) channel));

                return false;
            }
        });
    }
}
