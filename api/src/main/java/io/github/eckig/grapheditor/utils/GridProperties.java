package io.github.eckig.grapheditor.utils;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleDoubleProperty;

public class GridProperties {

    private final BooleanProperty gridVisible =
            new SimpleBooleanProperty(this, "gridVisible");

    private final BooleanProperty snapToGrid =
            new SimpleBooleanProperty(this, "snapToGrid");

    private final DoubleProperty gridSpacing =
            new SimpleDoubleProperty(
                    this,
                    "gridSpacing",
                    GraphEditorProperties.DEFAULT_GRID_SPACING);

    public boolean isGridVisible() {
        return gridVisible.get();
    }

    public void setGridVisible(boolean value) {
        gridVisible.set(value);
    }

    public BooleanProperty gridVisibleProperty() {
        return gridVisible;
    }

    public boolean isSnapToGridOn() {
        return snapToGrid.get();
    }

    public void setSnapToGrid(boolean value) {
        snapToGrid.set(value);
    }

    public BooleanProperty snapToGridProperty() {
        return snapToGrid;
    }

    public double getGridSpacing() {
        return gridSpacing.get();
    }

    public void setGridSpacing(double value) {
        gridSpacing.set(value);
    }

    public DoubleProperty gridSpacingProperty() {
        return gridSpacing;
    }
}
