package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.math.Size;

public interface WindowOffsetAlignment extends Alignment {
    public int offsetX(Size window);

    public int offsetY(Size window);

    @Override
    default int alignX(int x, int y, Size window) {
        return x + offsetX(window);
    }

    @Override
    default int alignY(int x, int y, Size window) {
        return y + offsetY(window);
    }

    @Override
    default int inverseAlignX(int x, int y, Size window) {
        return x - offsetX(window);
    }

    @Override
    default int inverseAlignY(int x, int y, Size window) {
        return y - offsetY(window);
    }
}
