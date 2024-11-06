package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;

public class RelativeAlignment implements AlignmentStrategy {
    private final AlignmentStrategy baseAlignment;
    private final Point offset;

    public RelativeAlignment(AlignmentStrategy baseAlignment, Point offset) {
        this.baseAlignment = baseAlignment;
        this.offset = offset;
    }

    @Override
    public Point align(Point original, Size window) {
        return baseAlignment.align(original, window).offset(offset);
    }

    @Override
    public Point inverseAlign(Point aligned, Size window) {
        return baseAlignment.inverseAlign(aligned, window).offset(offset.scaledBy(-1));
    }
}
