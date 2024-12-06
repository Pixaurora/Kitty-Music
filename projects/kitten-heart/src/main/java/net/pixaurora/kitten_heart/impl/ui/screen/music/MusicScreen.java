package net.pixaurora.kitten_heart.impl.ui.screen.music;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.Track;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.screen.Screen;
import net.pixaurora.kitten_cube.impl.ui.screen.WidgetContainer;
import net.pixaurora.kitten_cube.impl.ui.screen.align.Alignment;
import net.pixaurora.kitten_cube.impl.ui.screen.align.WidgetAnchor;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.StaticTexture;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PushableTextLines;
import net.pixaurora.kitten_heart.impl.EventHandling;
import net.pixaurora.kitten_heart.impl.KitTunes;
import net.pixaurora.kitten_heart.impl.music.control.MusicControls;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;
import net.pixaurora.kitten_heart.impl.music.progress.PlayingSong;
import net.pixaurora.kitten_heart.impl.ui.screen.KitTunesScreenTemplate;
import net.pixaurora.kitten_heart.impl.ui.widget.PauseButton;
import net.pixaurora.kitten_heart.impl.ui.widget.Timer;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.MusicCooldownProgress;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBar;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBarTileSet;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressBarTileSets;
import net.pixaurora.kitten_heart.impl.ui.widget.progress.ProgressProvider;

import static net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata.asComponent;

public class MusicScreen extends KitTunesScreenTemplate {
    private static final ProgressBarTileSet FILLED_TILE_SET = tileSet(
            KitTunes.resource("textures/gui/sprites/widget/music/progress_bar/filled.png"));
    private static final ProgressBarTileSet EMPTY_TILE_SET = tileSet(
            KitTunes.resource("textures/gui/sprites/widget/music/progress_bar/empty.png"));

    private static final ResourcePath DEFAULT_ALBUM_ART = KitTunes.resource("textures/icon.png");

    private static final ProgressBarTileSets PLAYING_SONG_TILE_SET = new ProgressBarTileSets(EMPTY_TILE_SET,
            FILLED_TILE_SET);

    Optional<DisplayMode> mode;

    public MusicScreen(Screen previous) {
        super(previous);

        this.mode = Optional.empty();
    }

    @Override
    protected Alignment alignmentMethod() {
        return Alignment.CENTER;
    }

    @Override
    protected void firstInit() {
        this.setupMode();
    }

    @Override
    public void tick() {
        if (!this.mode.isPresent()) {
            return;
        }

        DisplayMode mode = this.mode.get();

        if (!mode.isActive()) {
            mode.cleanup();

            this.setupMode();
        }
    }

    private void setupMode() {
        Optional<PlayingSong> progress = EventHandling.playingSongs().stream().findFirst();

        DisplayMode mode = progress.isPresent() ? this.createMusicDisplay(progress.get()) : this.createWaitingDisplay();
        this.mode = Optional.of(mode);
    }

    private static ProgressBarTileSet tileSet(ResourcePath texturePath) {
        return ProgressBarTileSet.create(
                GuiTexture.of(texturePath, Size.of(12, 4)),
                Point.ZERO, Size.of(4, 4), Point.of(4, 0), Size.of(4, 4), Point.of(8, 0), Size.of(4, 4));
    }

    public DisplayMode createMusicDisplay(PlayingSong song) {
        WidgetContainer<ProgressBar> progressBar = this.configProgressBar(song, PLAYING_SONG_TILE_SET);

        WidgetContainer<Timer> timer = this.addWidget(new Timer(song))
                .align(progressBar.relativeTo(WidgetAnchor.BOTTOM_MIDDLE))
                .anchor(WidgetAnchor.TOP_MIDDLE);

        Optional<Album> album = song.track().flatMap(Track::album);

        ResourcePath albumArtTexture = album
                .flatMap(Album::albumArtPath)
                .orElse(DEFAULT_ALBUM_ART);
        Size iconSize = Size.of(128, 128);
        WidgetContainer<StaticTexture> albumArt = this
                .addWidget(
                        new StaticTexture(Texture.of(albumArtTexture, iconSize)))
                .anchor(WidgetAnchor.MIDDLE_RIGHT)
                .at(Point.of(-10, 0));

        WidgetContainer<PushableTextLines> songInfo = this.addWidget(PushableTextLines.regular())
                .anchor(WidgetAnchor.MIDDLE_LEFT)
                .at(Point.of(10, 0));

        if (song.track().isPresent()) {
            Track track = song.track().get();

            songInfo.get().push(asComponent(track));
            songInfo.get().push(asComponent(track.artist()));
            if (album.isPresent()) {
                songInfo.get().push(asComponent(track.album().get()));
            }
        } else {
            songInfo.get().push(Component.literal("No track found :("));
        }

        WidgetContainer<PauseButton> pauseButton = this
                .addWidget(
                        new PauseButton(
                                () -> song.controls().playbackState(),
                                (button) -> {
                                    MusicControls controls = song.controls();

                                    PlaybackState state = controls.playbackState();

                                    if (state == PlaybackState.PAUSED) {
                                        controls.unpause();
                                    } else if (state == PlaybackState.PLAYING) {
                                        controls.pause();
                                    }
                                }))
                .align(progressBar.relativeTo(WidgetAnchor.BOTTOM_LEFT));

        return new MusicDisplayMode(song, Arrays.asList(progressBar, timer, albumArt, songInfo, pauseButton));
    }

    public DisplayMode createWaitingDisplay() {
        ProgressProvider progress = new MusicCooldownProgress();

        WidgetContainer<ProgressBar> progressBar = this.configProgressBar(progress, PLAYING_SONG_TILE_SET);

        return new WaitingDisplayMode(Arrays.asList(progressBar));
    }

    private WidgetContainer<ProgressBar> configProgressBar(ProgressProvider progress, ProgressBarTileSets tileSets) {
        return this.addWidget(new ProgressBar(progress, tileSets))
                .align(Alignment.CENTER_BOTTOM)
                .at(Point.of(0, -24))
                .anchor(WidgetAnchor.TOP_MIDDLE);
    }

    private abstract class DisplayMode {
        private final List<WidgetContainer<?>> widgets;

        public DisplayMode(List<WidgetContainer<?>> widgets) {
            this.widgets = widgets;
        }

        abstract boolean isActive();

        void cleanup() {
            for (WidgetContainer<?> widget : this.widgets) {
                MusicScreen.this.removeWidget(widget);
            }
        }
    }

    private class MusicDisplayMode extends DisplayMode {
        private final PlayingSong song;

        MusicDisplayMode(PlayingSong song, List<WidgetContainer<?>> widgets) {
            super(widgets);
            this.song = song;
        }

        @Override
        public boolean isActive() {
            return EventHandling.isTracking(song.progress());
        }
    }

    private class WaitingDisplayMode extends DisplayMode {
        WaitingDisplayMode(List<WidgetContainer<?>> widgets) {
            super(widgets);
        }

        @Override
        public boolean isActive() {
            return !EventHandling.isTrackingAnything();
        }
    }
}
