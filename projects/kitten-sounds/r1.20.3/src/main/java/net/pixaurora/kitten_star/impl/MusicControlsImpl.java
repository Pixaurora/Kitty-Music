package net.pixaurora.kitten_star.impl;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.mojang.blaze3d.audio.Channel;

import net.minecraft.client.sounds.ChannelAccess.ChannelHandle;
import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;

public class MusicControlsImpl implements MusicControls {
    private Optional<ChannelHandle> channel = Optional.empty();

    public void channel(ChannelHandle channel) {
        this.channel = Optional.of(channel);
    }

    @Override
    public void pause() {
        if (this.channel.isPresent()) {
            this.channel.get().execute(Channel::pause);
        }
    }

    @Override
    public void unpause() {
        if (this.channel.isPresent()) {
            this.channel.get().execute(Channel::unpause);
        }
    }

    @Override
    public PlaybackState playbackState() {
        if (this.channel.isEmpty() || this.channel.get().isStopped()) {
            return PlaybackState.STOPPED;
        }

        CompletableFuture<PlaybackState> playbackState = new CompletableFuture<PlaybackState>().orTimeout(1,
                TimeUnit.MINUTES);

        this.channel.get().execute(channel -> {
            if (channel.playing()) {
                playbackState.complete(PlaybackState.PLAYING);
            } else {
                playbackState.complete(PlaybackState.PAUSED);
            }
        });

        try {
            return playbackState.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Couldn't get playback state!", e);
        }
    }

}
