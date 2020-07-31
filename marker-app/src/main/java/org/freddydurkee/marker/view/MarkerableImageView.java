package org.freddydurkee.marker.view;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import org.freddydurkee.marker.model.Marker;
import utils.model.Point;

import java.util.HashMap;
import java.util.Map;

public class MarkerableImageView extends Pane {
    Map<Marker, Circle> markerCircleMap = new HashMap<>();

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

    public MarkerableImageView() {
        imageViewProperty.addListener((arg0, oldIV, newIV) -> {
            if (oldIV != null) {
                getChildren().remove(oldIV);
            }
            if (newIV != null) {
                getChildren().add(newIV);
            }
        });
    }

    public MarkerableImageView(ImageView imageView) {
        this();
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

    private void addMarker(Marker marker) {
        ImageView imageView = imageViewProperty.get();
        Point relativeCoor = calculateMarkerRelativePosition(marker, imageView);
        Circle circle = createCircleMarker(marker, relativeCoor);
        this.getChildren().add(circle);
        markerCircleMap.put(marker,circle);
        marker.xProperty().bindBidirectional(circle.centerXProperty());
        marker.yProperty().bindBidirectional(circle.centerYProperty());
    }

    private Circle createCircleMarker(Marker marker, Point relativeCoor) {
        Circle circle = new Circle(relativeCoor.getX(), relativeCoor.getY(), marker.getR());
        circle.setFill(marker.getColor());
        circle.setOnMouseDragged((new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            }
        }));
        return circle;
    }

    private Point calculateMarkerRelativePosition(Marker marker, ImageView imageView) {
        Point parentCenter = new Point(this.getWidth() / 2, this.getHeight() / 2);
        Point imgCenter = new Point(imageView.getBoundsInParent().getWidth() / 2, imageView.getBoundsInParent().getHeight() / 2);
        Point imgStartPoint = parentCenter.subtract(imgCenter);
        return imgStartPoint.add(new Point(marker.getX(), marker.getY()));
    }


    public void addMarkerList(ObservableList<Marker> markers) {
        markers.addListener((ListChangeListener) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Object marker : change.getAddedSubList()) {
                        addMarker((Marker) marker);
                    }
                }
            }
        });
    }
}
