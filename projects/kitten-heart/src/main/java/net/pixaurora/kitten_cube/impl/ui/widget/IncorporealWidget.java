package net.pixaurora.kitten_cube.impl.ui.widget;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public interface IncorporealWidget extends Widget {
    public default Size size() {
        return Size.of(0, 0);
    }

    public default Point pos() {
        return Point.ZERO;
    }
}
