package net.pixaurora.kitten_cube.impl.ui.widget.surface;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public interface WidgetSurface {
    public boolean isWithinBounds(Point mousePos);

    public Size size();
}
