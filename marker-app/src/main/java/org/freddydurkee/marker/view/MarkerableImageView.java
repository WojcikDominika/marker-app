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
import org.freddydurkee.marker.model.Point;
import org.freddydurkee.marker.view.utils.ImageViewPositionMapper;

import java.util.HashMap;
import java.util.Map;

public class MarkerableImageView extends Pane {

    Map<Marker, Circle> markerCircleMap = new HashMap<>();

    private ObjectProperty<ImageView> imageViewProperty = new SimpleObjectProperty<ImageView>();


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

    public ImageView getImageView() {
        return imageViewProperty.get();
    }


    public void setImageView(ImageView imageView) {
        this.imageViewProperty.set(imageView);
    }

    public int calculateOriginalImageX(double currentX) {
        double currWidth = imageViewProperty.get().getBoundsInParent().getWidth();
        double orgWidth = imageViewProperty.get().getImage().getWidth();
        return ImageViewPositionMapper.mapViewPositionToImagePosition(currentX, currWidth, orgWidth);
    }

    public int calculateOriginalImageY(double currentY) {
        double currHeight = imageViewProperty.get().getBoundsInParent().getHeight();
        double orgHeight = imageViewProperty.get().getImage().getHeight();
        return ImageViewPositionMapper.mapViewPositionToImagePosition(currentY, currHeight, orgHeight);
    }

    @Override
    protected void layoutChildren() {
        ImageView imageView = imageViewProperty.get();
        if (imageView != null) {
            imageView.setFitWidth(getWidth());
            imageView.setFitHeight(getHeight());
            layoutInArea(imageView, 0, 0, getWidth(), getHeight(), 0, HPos.CENTER, VPos.CENTER);
        }
        markerCircleMap.entrySet().forEach(markerCircleEntry -> recalculateMarkerPosition(markerCircleEntry.getValue(), markerCircleEntry.getKey()));
        super.layoutChildren();
    }

    private void recalculateMarkerPosition(Circle circle, Marker marker) {
        circle.setCenterX(convertToCircleX(marker));
        circle.setCenterY(convertToCircleY(marker));
    }

    private void addMarker(Marker marker) {
        Circle circle = createCircleMarker(marker);
        this.getChildren().add(circle);
        markerCircleMap.put(marker, circle);

        marker.xProperty().addListener((observable) -> { circle.setCenterX(convertToCircleX(marker)); });
        marker.yProperty().addListener((observable) -> { circle.setCenterY(convertToCircleY(marker)); });
    }

    private double convertToCircleX(Marker marker) {
        double currWidth = imageViewProperty.get().getBoundsInParent().getWidth();
        double orgWidth = imageViewProperty.get().getImage().getWidth();
        return ImageViewPositionMapper.mapImagePositionToViewPosition(imageViewTopLeftCorner().getX(), marker.getX(), currWidth, orgWidth);
    }

    private double convertToCircleY(Marker marker) {
        double currHeight = imageViewProperty.get().getBoundsInParent().getHeight();
        double orgHeight = imageViewProperty.get().getImage().getHeight();
        return ImageViewPositionMapper.mapImagePositionToViewPosition(imageViewTopLeftCorner().getY(), marker.getY(), currHeight, orgHeight);
    }

    private Point imageViewTopLeftCorner(){
        double x = (this.getWidth() - imageViewProperty.get().getBoundsInParent().getWidth()) / 2;
        double y = (this.getHeight() - imageViewProperty.get().getBoundsInParent().getHeight()) / 2;
        return new Point(x, y);
    }

    private Circle createCircleMarker(Marker marker) {
        Circle circle = new Circle(convertToCircleX(marker), convertToCircleY(marker), marker.getR());
        circle.setFill(marker.getColor());
        circle.setOnMouseDragged(markerMoveEvent(marker));
        return circle;
    }

    private EventHandler<MouseEvent> markerMoveEvent(Marker marker) {
        return event -> {
            Point cursorPositionOnContainer = new Point(event.getX(), event.getY());
            Point imgViewTopLeftCorner = imageViewTopLeftCorner();
            if (cursorPositionOnContainer.getX() >= imgViewTopLeftCorner.getX() && cursorPositionOnContainer.getX() < imgViewTopLeftCorner.getX() + imageViewProperty.get().getBoundsInParent().getWidth()
                && cursorPositionOnContainer.getY() >= imgViewTopLeftCorner.getY() && cursorPositionOnContainer.getY() < imgViewTopLeftCorner.getY() + imageViewProperty.get().getBoundsInParent().getHeight()) {
                Point cursorPositionOnImageView = cursorPositionOnContainer.shift(-imageViewTopLeftCorner().getX(), -imgViewTopLeftCorner.getY());
                int imgOriginalX = calculateOriginalImageX(cursorPositionOnImageView.getX());
                int imgOriginalY = calculateOriginalImageY(cursorPositionOnImageView.getY());
                marker.setX(imgOriginalX);
                marker.setY(imgOriginalY);
            }
        };
    }
    
    public void addMarkerList(ObservableList<Marker> markers) {
        markers.addListener((ListChangeListener<Marker>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Marker marker : change.getAddedSubList()) {
                        addMarker(marker);
                    }
                }
                if (change.wasRemoved()) {
                    for (Marker marker : change.getRemoved()) {
                        this.getChildren().remove(markerCircleMap.remove(marker));
                    }
                }
            }
        });
    }
}
