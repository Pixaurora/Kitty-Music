package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class RelativeAlignment implements Alignment {
    private final Alignment baseAlignment;

    private final Widget widget;
    private final WidgetAnchor anchor;

    public RelativeAlignment(Alignment baseAlignment, Widget widget, WidgetAnchor anchor) {
        this.baseAlignment = baseAlignment;
        this.widget = widget;
        this.anchor = anchor;
    }

    @Override
    public int alignX(int x, int y, Size window) {
        return this.baseAlignment.alignX(x, y, window) + this.anchor.anchorX(this.widget);
    }

    @Override
    public int alignY(int x, int y, Size window) {
        return this.baseAlignment.alignY(x, y, window) + this.anchor.anchorY(this.widget);
    }

    @Override
    public int inverseAlignX(int x, int y, Size window) {
        return this.baseAlignment.inverseAlignX(x, y, window) - this.anchor.anchorX(this.widget);
    }

    @Override
    public int inverseAlignY(int x, int y, Size window) {
        return this.baseAlignment.inverseAlignY(x, y, window) - this.anchor.anchorY(this.widget);
    }
}
