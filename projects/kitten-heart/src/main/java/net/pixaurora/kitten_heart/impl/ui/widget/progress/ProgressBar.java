package net.pixaurora.kitten_heart.impl.ui.widget.progress;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import net.pixaurora.kitten_cube.impl.math.Point;
import net.pixaurora.kitten_cube.impl.math.Size;
import net.pixaurora.kitten_cube.impl.ui.controls.MouseButton;
import net.pixaurora.kitten_cube.impl.ui.display.GuiDisplay;
import net.pixaurora.kitten_cube.impl.ui.tile.InnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.PositionedInnerTile;
import net.pixaurora.kitten_cube.impl.ui.tile.TileColumn;
import net.pixaurora.kitten_cube.impl.ui.widget.Widget;

public class ProgressBar implements Widget {
    private final List<PositionedInnerTile> tiles;
    private Optional<Size> window;
    private int progressWidth;

    private final ProgressProvider progressProvider;
    private final ProgressBarTileSets tilesets;

    private Size size;

    public ProgressBar(ProgressProvider progressProvider, ProgressBarTileSets tilesets) {
        this.tiles = new ArrayList<>();
        this.window = Optional.empty();
        this.progressProvider = progressProvider;
        this.tilesets = tilesets;
    }

    @Override
    public void onWindowUpdate(Size window) {
        this.window = Optional.of(window);
        this.update(true);
    }

    @Override
    public void tick() {
        this.update(false);
    }

    private void update(boolean windowChanged) {
        if (!this.window.isPresent()) {
            return;
        }

        Size window = this.window.get();
        double songProgress = this.progressProvider.percentComplete();

        int newProgressWidth = (int) (window.width() * songProgress);

        if (newProgressWidth != progressWidth || windowChanged) {
            int evenWidth = window.width() / 4 * 4 - 16;
            createTiles(newProgressWidth, evenWidth);
        }
    }

    private void createTiles(int progressWidth, int barWidth) {
        this.tiles.clear();

        this.createTiles0(progressWidth, barWidth, tilesets.filled(),
                (tile, placement, goalWidth, startsBeforeGoal, endsBeforeGoal) -> {
                    Optional<PositionedInnerTile> placedTile = Optional.empty();

                    if (startsBeforeGoal && endsBeforeGoal) {
                        placedTile = Optional.of(tile.atPos(placement));
                    } else if (startsBeforeGoal && !endsBeforeGoal) {
                        int partialWidth = goalWidth - placement.x();
                        InnerTile partialTile = new InnerTile(tile.texture(), tile.textureOffset(),
                                tile.size().withX(partialWidth));

                        placedTile = Optional.of(partialTile.atPos(placement));
                    }

                    return placedTile;
                });

        this.createTiles0(progressWidth, barWidth, tilesets.empty(),
                (tile, placement, goalWidth, startsBeforeGoal, endsBeforeGoal) -> {
                    Optional<PositionedInnerTile> placedTile = Optional.empty();

                    if (!startsBeforeGoal && !endsBeforeGoal) {
                        placedTile = Optional.of(tile.atPos(placement));
                    } else if (startsBeforeGoal && !endsBeforeGoal) {
                        int partialWidth = placement.x() + tile.size().width() - goalWidth;
                        Point offset = Point.ZERO.withX(tile.size().width() - partialWidth);
                        InnerTile partialTile = new InnerTile(tile.texture(), tile.textureOffset().offset(offset),
                                tile.size().withX(partialWidth));

                        placedTile = Optional.of(partialTile.atPos(placement.offset(offset)));
                    }

                    return placedTile;
                });
    }

    private void createTiles0(int progressWidth, int barWidth,
            ProgressBarTileSet tileSet, TilePlacementMethod tileMethod) {
        int middleTileCount = (barWidth - (tileSet.left().width() + tileSet.right().width()))
                / tileSet.middle().width();

        this.size = Size.of(
                tileSet.left().width() + middleTileCount * tileSet.middle().width() + tileSet.right().width(),
                tileSet.left().height());

        Point placement = Point.ZERO;

        int goalX = progressWidth;

        for (TileColumn tilePosition : TileColumn.values()) {
            InnerTile tile = tileSet.get(tilePosition);
            int tileCount = tilePosition == TileColumn.MIDDLE ? middleTileCount : 1;

            for (int i = 0; i < tileCount; i++) {
                boolean startsBeforeGoal = placement.x() < goalX;
                boolean endsBeforeGoal = placement.x() + tile.size().width() < goalX;

                Optional<PositionedInnerTile> placedTile = tileMethod.place(tile, placement, goalX,
                        startsBeforeGoal, endsBeforeGoal);

                if (placedTile.isPresent()) {
                    this.tiles.add(placedTile.get());
                }

                placement = placement.offset(tile.size().width(), 0);
            }
        }
    }

    @Override
    public void draw(GuiDisplay gui, Point mousePos) {
        for (PositionedInnerTile tile : tiles) {
            tile.draw(gui);
        }
    }

    @Override
    public void onClick(Point mousePos, MouseButton button) {
        // TODO: Add clicking support to change the position in the song maybe???
        // That's probably hard...
    }

    @Override
    public boolean isWithinBounds(Point mousePos) {
        return false;
    }

    private static interface TilePlacementMethod {
        public Optional<PositionedInnerTile> place(InnerTile tile, Point at, int goalX, boolean startsBeforeGoal,
                boolean endsBeforeGoal);
    }

    @Override
    public Size size() {
        return this.size;
    }
}
