package org.freddydurkee.marker.view;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.List;
import java.util.stream.Collectors;

public class ResizableImageView extends Pane {

    @Override
    protected void layoutChildren() {
        List<ImageView> imageViews = getChildren().stream().filter(node -> node instanceof ImageView).map(node -> (ImageView) node).collect(
            Collectors.toList());
        for (ImageView view : imageViews) {
            view.setFitWidth(getWidth());
            view.setFitHeight(getHeight());
            layoutInArea(view, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        super.layoutChildren();
    }

    public ResizableImageView() {
    }
}
