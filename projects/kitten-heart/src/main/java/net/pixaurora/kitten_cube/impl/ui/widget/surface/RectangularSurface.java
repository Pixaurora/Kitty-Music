package net.pixaurora.kitten_cube.impl.ui.widget.surface;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public class RectangularSurface implements WidgetSurface {
    private final Point startPos;
    private final Point endPos;

    private RectangularSurface(Point startPos, Point endPos) {
        this.startPos = startPos;
        this.endPos = endPos;
    }

    private RectangularSurface(Size size) {
        this.startPos = Point.ZERO;
        this.endPos = this.startPos.offset(size);
    }

    public static RectangularSurface of(Size size) {
        return new RectangularSurface(size);
    }

    @Override
    public Size size() {
        return this.endPos.offset(this.startPos.scaledBy(-1)).toSize();
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return this.startPos.lessThan(mousePos) && mousePos.lessThan(this.endPos);
    }
}
