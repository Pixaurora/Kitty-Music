package net.pixaurora.kitten_heart.impl.ui.widget;

import java.util.function.Supplier;

import net.pixaurora.kitten_cube.impl.MinecraftClient;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.sound.Sound;
import net.pixaurora.kitten_cube.impl.ui.widget.button.Button;
import net.pixaurora.kitten_cube.impl.ui.widget.button.ButtonBackground;
import net.pixaurora.kitten_cube.impl.ui.widget.button.ClickEvent;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.RectangularSurface;
import net.pixaurora.kitten_cube.impl.ui.widget.surface.WidgetSurface;
import net.pixaurora.kitten_heart.impl.music.control.PlaybackState;

public class PauseButton implements Button {
    private static final ButtonBackground BACKGROUND = ButtonBackground.NEUTRAL_SQUARE;
    private static final Size SIZE = Size.of(20, 20);

    private final Supplier<PlaybackState> playbackState;
    private final ClickEvent onClick;

    private final Point iconPos;

    private final WidgetSurface surface;

    public PauseButton(Supplier<PlaybackState> stateSupplier, ClickEvent onClick) {
        this.playbackState = stateSupplier;
        this.onClick = onClick;
        this.iconPos = Point.ZERO.offset(2, 2);
        this.surface = RectangularSurface.of(SIZE);
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        gui.drawGui(BACKGROUND.texture(this.isDisabled(), this.isWithinBounds(mousePos)), Point.ZERO);
        gui.drawGui(this.playbackState.get().icon(), this.iconPos);
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
        this.onClick.onClick(this);
        MinecraftClient.playSound(Sound.BUTTON_CLICK);
    }

    @Override
    public WidgetSurface surface() {
        return this.surface;
    }

    @Override
    public boolean isDisabled() {
        return this.playbackState.get() == PlaybackState.STOPPED;
    }

    @Override
    public void setDisabledStatus(boolean isDisabled) {
        throw new UnsupportedOperationException("Unimplemented method 'setDisabledStatus'");
    }
}
