package net.pixaurora.kitten_cube.impl.ui.widget.button;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_heart.impl.KitTunes;

public class ButtonBackground {
    public static final ButtonBackground NEUTRAL_SQUARE = new ButtonBackground(
            KitTunes.resource("textures/gui/sprites/widget/button/square/normal.png"),
            KitTunes.resource("textures/gui/sprites/widget/button/square/highlighted.png"),
            KitTunes.resource("textures/gui/sprites/widget/button/square/disabled.png"),
            Size.of(20, 20));

    public static final ButtonBackground NEUTRAL_RECTANGLE = new ButtonBackground(
            KitTunes.resource("textures/gui/sprites/widget/button/rectangle/normal.png"),
            KitTunes.resource("textures/gui/sprites/widget/button/rectangle/highlighted.png"),
            KitTunes.resource("textures/gui/sprites/widget/button/rectangle/disabled.png"),
            Size.of(200, 20));

    private final GuiTexture unhighlighted;
    private final GuiTexture highlighted;
    private final GuiTexture disabled;

    public ButtonBackground(GuiTexture unhighlighted, GuiTexture highlighted, GuiTexture disabled) {
        this.unhighlighted = unhighlighted;
        this.highlighted = highlighted;
        this.disabled = disabled;
    }

    public ButtonBackground(ResourcePath unhighlighted, ResourcePath highlighted, ResourcePath disabled, Size size) {
        this.unhighlighted = GuiTexture.of(unhighlighted, size);
        this.highlighted = GuiTexture.of(highlighted, size);
        this.disabled = GuiTexture.of(disabled, size);
    }

    public GuiTexture texture(boolean isDisabled, boolean isHighlighted) {
        if (isDisabled) {
            return this.disabled;
        } else if (isHighlighted) {
            return this.highlighted;
        } else {
            return this.unhighlighted;
        }
    }

    public Size size() {
        return this.unhighlighted.size();
    }
}
