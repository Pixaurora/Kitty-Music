package net.pixaurora.kitten_cube.impl.ui.screen.align;

import net.pixaurora.kitten_cube.impl.math.Size;

public interface NestedAlignment extends Alignment {
    public Alignment parent();

    public int offsetX();

    public int offsetY();

    @Override
    public default int alignX(int x, int y, Size window) {
        return this.parent().alignX(x, y, window) + this.offsetX();
    }

    @Override
    public default int alignY(int x, int y, Size window) {
        return this.parent().alignY(x, y, window) + this.offsetY();
    }

    @Override
    public default int inverseAlignX(int x, int y, Size window) {
        return this.parent().inverseAlignX(x, y, window) - this.offsetX();
    }

    @Override
    public default int inverseAlignY(int x, int y, Size window) {
        return this.parent().inverseAlignY(x, y, window) - this.offsetY();
    }
}
