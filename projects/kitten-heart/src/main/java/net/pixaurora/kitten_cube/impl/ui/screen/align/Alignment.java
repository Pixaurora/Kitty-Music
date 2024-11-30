package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public interface Alignment {
    public static final Alignment CENTER = new BasicAlignments.TrueCenterAlignment();
    public static final Alignment CENTER_TOP = new BasicAlignments.TopCenterAlignment();
    public static final Alignment CENTER_BOTTOM = new BasicAlignments.BottomCenterAlignment();
    public static final Alignment TOP_LEFT = new BasicAlignments.TopLeftAlignment();

    public int alignX(int x, int y, Size window);

    public int alignY(int x, int y, Size window);

    public int inverseAlignX(int x, int y, Size window);

    public int inverseAlignY(int x, int y, Size window);

    public default int alignX(Point pos, Size window) {
        return this.alignX(pos.x(), pos.y(), window);
    }

    public default int alignY(Point pos, Size window) {
        return this.alignY(pos.x(), pos.y(), window);
    }

    public default int inverseAlignX(Point pos, Size window) {
        return this.inverseAlignX(pos.x(), pos.y(), window);
    }

    public default int inverseAlignY(Point pos, Size window) {
        return this.inverseAlignY(pos.x(), pos.y(), window);
    }

    public default Point inverseAlign(Point pos, Size window) {
        return Point.of(this.inverseAlignX(pos, window), this.inverseAlignY(pos, window));
    }
}
