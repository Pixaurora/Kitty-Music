package net.pixaurora.kitten_heart.impl.ui.widget.history;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.widget.IncorporealWidget;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PositionedText;

public class HistoryWidgetEntry implements IncorporealWidget {
    private final Point iconPos;
    private final Texture icon;

    private final PositionedText title;
    private final PositionedText artist;

    public HistoryWidgetEntry(Point iconPos, Texture icon, PositionedText title, PositionedText artist) {
        this.iconPos = iconPos;
        this.icon = icon;
        this.title = title;
        this.artist = artist;
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        gui.draw(this.icon, this.iconPos);

        gui.drawText(this.title.text(), this.title.color(), this.title.pos());
        gui.drawText(this.artist.text(), this.artist.color(), this.artist.pos());
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }
}
