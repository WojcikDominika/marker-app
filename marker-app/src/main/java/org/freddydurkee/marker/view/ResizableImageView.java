package org.freddydurkee.marker.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import utils.model.Point;

import static utils.view.Palette.randomColor;

public class ResizableImageView extends Pane {

    private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();

    public ObjectProperty<ImageView> imageViewProperty() {
        return imageViewProperty;
    }

    public ImageView getImageView() {
        return imageViewProperty.get();
    }

    public void setImageView(ImageView imageView) {
        this.imageViewProperty.set(imageView);
    }


    @Override
    protected void layoutChildren() {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setFitWidth(getWidth());
            imageView.setFitHeight(getHeight());
            layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        super.layoutChildren();
    }

    public void addMarker(MouseEvent mouseEvent) {
        ImageView imageView = imageViewProperty.get();
        Point markerPosition = calculateMarkerRelativePosition(mouseEvent, imageView);
        Circle marker = new Circle(markerPosition.getX(), markerPosition.getY(), 5);
        marker.setFill(randomColor());
        this.getChildren().add(marker);
    }

    private Point calculateMarkerRelativePosition(MouseEvent mouseEvent, ImageView imageView) {
        Point parentCenter = new Point(this.getWidth() / 2, this.getHeight() / 2);
        Point imgCenter = new Point(imageView.getBoundsInParent().getWidth() / 2, imageView.getBoundsInParent().getHeight() / 2);
        Point imgStartPoint = parentCenter.subtract(imgCenter);
        return imgStartPoint.add(new Point(mouseEvent.getX(), mouseEvent.getY()));
    }

    public ResizableImageView() {
        imageViewProperty.addListener((arg0, oldIV, newIV) -> {
            if (oldIV != null) {
                getChildren().remove(oldIV);
            }
            if (newIV != null) {
                getChildren().add(newIV);
            }
        });
    }

    public ResizableImageView(ImageView imageView) {
        this();
        this.imageViewProperty.set(imageView);
    }
}
