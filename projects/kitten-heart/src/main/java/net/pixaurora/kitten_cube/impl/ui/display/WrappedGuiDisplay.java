package net.pixaurora.kitten_cube.impl.ui.display;

import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.text.Component;
import net.pixaurora.kitten_cube.impl.ui.widget.text.TextBox;

public class WrappedGuiDisplay implements GuiDisplay {
    private final GuiDisplay parent;

    WrappedGuiDisplay(GuiDisplay parent) {
        this.parent = parent;
    }

    public GuiDisplay parent() {
        return this.parent;
    }

    @Override
    public void drawTexture(ResourcePath path, int width, int height, int x, int y) {
        this.parent.drawTexture(path, width, height, x, y);
    }

    @Override
    public void drawGuiTextureSubsection(ResourcePath path, int width, int height, int x, int y, int subsectionWidth,
            int subsectionHeight, int offsetX, int offsetY) {
        this.parent.drawGuiTextureSubsection(path, width, height, x, y, subsectionWidth, subsectionHeight, offsetX,
                offsetY);
    }

    @Override
    public void drawText(Component text, Color color, int x, int y, boolean shadowed) {
        this.parent.drawText(text, color, x, y, shadowed);
    }

    @Override
    public void drawTextBox(TextBox box) {
        this.parent.drawTextBox(box);
    }
}
