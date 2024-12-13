package net.pixaurora.kitten_heart.impl.ui.widget.history;

import java.util.EnumMap;

import net.pixaurora.kit_tunes.api.music.Album;
import net.pixaurora.kit_tunes.api.music.history.ListenRecord;
import net.pixaurora.kit_tunes.api.resource.ResourcePath;
import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.text.Color;
import net.pixaurora.kitten_cube.impl.ui.texture.GuiTexture;
import net.pixaurora.kitten_cube.impl.ui.texture.Texture;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.TileRow;
import net.pixaurora.kitten_cube.impl.ui.widget.text.PositionedText;
import net.pixaurora.kitten_heart.impl.ui.toast.MeowPlayingToast;

import static net.pixaurora.kitten_heart.impl.music.metadata.MusicMetadata.asComponent;

public class HistoryTileSet {
    private final Row top;
    private final Row middle;
    private final Row bottom;

    private final EnumMap<TileRow, Row> tileMap;

    public HistoryTileSet(Row top, Row middle, Row bottom) {
        this.top = top;
        this.middle = middle;
        this.bottom = bottom;
        this.tileMap = new EnumMap<>(TileRow.class);

        this.tileMap.put(TileRow.TOP, top);
        this.tileMap.put(TileRow.MIDDLE, middle);
        this.tileMap.put(TileRow.BOTTOM, bottom);
    }

    public static HistoryTileSet of(
            GuiTexture texture,
            RowData infoPlacements,
            Point topTileOffset, Size topTileSize,
            Point middleTileOffset, Size middleTileSize,
            Point bottomTileOffset, Size bottomTileSize) {
        Row topRow = new Row(new InnerTile(texture, topTileOffset, topTileSize), infoPlacements);

        Row middleRow = new Row(new InnerTile(texture, middleTileOffset, middleTileSize), infoPlacements);

        Row bottomRow = new Row(new InnerTile(texture, bottomTileOffset, bottomTileSize), infoPlacements);

        return new HistoryTileSet(topRow, middleRow, bottomRow);
    }

    public Row top() {
        return this.top;
    }

    public Row middle() {
        return this.middle;
    }

    public Row bottom() {
        return this.bottom;
    }

    public Row get(TileRow tileRow) {
        return this.tileMap.get(tileRow);
    }

    public int width() {
        return this.top.tile().texture().size().width();
    }

    public static class Row {
        private final InnerTile tile;
        private final RowData data;

        public Row(InnerTile tile, RowData data) {
            this.tile = tile;
            this.data = data;
        }

        public HistoryWidgetEntry entryAt(Point pos, ListenRecord record) {
            Point iconPos = this.data.iconPos.offset(pos);

            ResourcePath iconPath = record.album().flatMap(Album::albumArtPath).orElse(MeowPlayingToast.DEFAULT_ICON);
            Texture icon = Texture.of(iconPath, this.data.iconSize);

            PositionedText title = new PositionedText(asComponent(record.track()), Color.YELLOW,
                    this.data.titlePos.offset(pos));
            PositionedText artist = new PositionedText(asComponent(record.track().artist()), Color.WHITE,
                    this.data.artistPos.offset(pos));

            return new HistoryWidgetEntry(iconPos, icon, title, artist);
        }

        public InnerTile tile() {
            return this.tile;
        }

        public RowData data() {
            return this.data;
        }
    }

    public static class RowData {
        private final Point iconPos;
        private final Size iconSize;

        private final Point titlePos;
        private final Point artistPos;

        public RowData(Point iconPos, Size iconSize, Point titlePos, Point artistPos) {
            this.iconPos = iconPos;
            this.iconSize = iconSize;
            this.titlePos = titlePos;
            this.artistPos = artistPos;
        }

        public Point iconPos() {
            return iconPos;
        }

        public Size iconSize() {
            return iconSize;
        }

        public Point titlePos() {
            return titlePos;
        }

        public Point artistPos() {
            return artistPos;
        }
    }
}
