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

    public double calculateOriginalImageX(double currentX) {
        double currWidth = imageViewProperty.get().getBoundsInParent().getWidth();
        double orgWidth = imageViewProperty.get().getImage().getWidth();
        return orgWidth / currWidth * currentX;
    }

    public double calculateOriginalImageY(double currentY) {
        double currHeight = imageViewProperty.get().getBoundsInParent().getHeight();
        double orgHeight = imageViewProperty.get().getImage().getHeight();
        return orgHeight / currHeight * currentY;
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
    }

    private double convertToCircleX(Marker marker) {
        double currWidth = imageViewProperty.get().getBoundsInParent().getWidth();
        double orgWidth = imageViewProperty.get().getImage().getWidth();
        return imgLeftTopCornerX() + (marker.getX() * currWidth / orgWidth);
    }

    private double convertToCircleY(Marker marker) {
        double currHeight = imageViewProperty.get().getBoundsInParent().getHeight();
        double orgHeight = imageViewProperty.get().getImage().getHeight();
        return imgLeftTopCornerY() + (marker.getY() * currHeight / orgHeight);
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
        circle.setOnMouseDragged(markerMoveEvent(marker));
        this.widthProperty().addListener((observable) -> { circle.setCenterX(convertToCircleX(marker));});
        this.heightProperty().addListener((observable) -> { circle.setCenterY(convertToCircleY(marker));});
        return circle;
    }

    private EventHandler<MouseEvent> markerMoveEvent(Marker marker) {
        return event -> {
            double cursorX = event.getX();
            double cursorY = event.getY();
            if (cursorX >= imgLeftTopCornerX() && cursorX < imgLeftTopCornerX() + imageViewProperty.get().getBoundsInParent().getWidth()
                && cursorY >= imgLeftTopCornerY() && cursorY < imgLeftTopCornerY() + imageViewProperty.get().getBoundsInParent().getHeight()) {
                double imgOriginalX = calculateOriginalImageX(fromContainerToImageX(cursorX));
                double imgOriginalY = calculateOriginalImageY(fromContainerToImageY(cursorY));
                marker.setX(imgOriginalX);
                marker.setY(imgOriginalY);
            }
        };
    }

    private double fromContainerToImageY(double cursorY) {
        return cursorY - imgLeftTopCornerY();
    }

    private double fromContainerToImageX(double cursorX) {
        return cursorX - imgLeftTopCornerX();
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
