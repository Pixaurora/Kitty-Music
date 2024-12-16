package net.pixaurora.kitten_cube.impl.ui.tile;

import java.util.List;

public enum TileRow {
    TOP,
    MIDDLE,
    BOTTOM;

    public static <T> TileRow fromIndex(List<T> elements, int index) {
        int first = 0;
        int last = elements.size() - 1;

        if (index == last) {
            return TileRow.BOTTOM;
        } else if (index == first) {
            return TileRow.TOP;
        } else {
            return TileRow.MIDDLE;
        }
    }
}
