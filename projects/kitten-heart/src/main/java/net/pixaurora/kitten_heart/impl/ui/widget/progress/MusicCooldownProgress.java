package net.pixaurora.kitten_heart.impl.ui.widget.progress;

import java.time.Duration;

import net.pixaurora.kitten_heart.impl.KitTunes;

public class MusicCooldownProgress implements ProgressProvider {
    private final long startingCooldown;

    public MusicCooldownProgress() {
        this.startingCooldown = KitTunes.MUSIC_LAYER.millisToNextSong();
    }

    @Override
    public double percentComplete() {
        return (double) KitTunes.MUSIC_LAYER.millisToNextSong() / this.startingCooldown;
    }

    @Override
    public Duration playedDuration() {
        return Duration.ofMillis(KitTunes.MUSIC_LAYER.millisToNextSong());
    }

    @Override
    public Duration totalDuration() {
        return Duration.ofMillis(startingCooldown);
    }

}
