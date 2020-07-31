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
        Circle circle = createCircleMarker(marker);
        this.getChildren().add(circle);
        markerCircleMap.put(marker, circle);

        marker.xProperty().addListener((observable) -> { circle.setCenterX(convertToCircleX(marker)); });
        marker.yProperty().addListener((observable) -> { circle.setCenterY(convertToCircleY(marker)); });
        circle.centerXProperty().addListener((observable) -> { marker.setX(convertToMarkerX(circle)); });
        circle.centerYProperty().addListener((observable) -> { marker.setY(convertToMarkerY(circle)); });
    }

    private double convertToMarkerX(Circle circle) {
        return circle.getCenterX() - imgLeftTopCornerX();
    }

    private double convertToMarkerY(Circle circle) {
        return circle.getCenterY() - imgLeftTopCornerY();
    }

    private double convertToCircleX(Marker marker) {
        return imgLeftTopCornerX() + marker.getX();
    }


    private double convertToCircleY(Marker marker) {
        return imgLeftTopCornerY() + marker.getY();
    }

    private double imgLeftTopCornerX() {
        return (this.getWidth() - imageViewProperty.get().getBoundsInParent().getWidth()) / 2;
    }

    private double imgLeftTopCornerY() {
        return (this.getHeight() - imageViewProperty.get().getBoundsInParent().getHeight()) / 2;
    }


    private Circle createCircleMarker(Marker marker) {
        Circle circle = new Circle(convertToCircleX(marker), convertToCircleY(marker), marker.getR());
        circle.setFill(marker.getColor());
        circle.setOnMouseDragged(circleMoveEvent(circle));
        return circle;
    }

    private EventHandler<MouseEvent> circleMoveEvent(Circle circle) {
        return event -> {
            double cursorX = event.getX();
            double cursorY = event.getY();
            if (cursorX >= imgLeftTopCornerX() && cursorX < imgLeftTopCornerX() + imageViewProperty.get().getBoundsInParent().getWidth()
                && cursorY >= imgLeftTopCornerY() && cursorY < imgLeftTopCornerY() + imageViewProperty.get().getBoundsInParent().getHeight()) {
                circle.setCenterX(event.getX());
                circle.setCenterY(event.getY());
            }
        };
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
