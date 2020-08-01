package org.freddydurkee.marker.view;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import org.freddydurkee.marker.model.Marker;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MarkerListView extends VBox{

    private final AtomicInteger markerCounter = new AtomicInteger();
    Map<Marker, MarkerListItem> markerListItemMap = new HashMap<>();

    public void resetMarkerCounter() {
        markerCounter.set(0);
    }

    public void addMarkerListItem(Marker marker, Image loadedImage) {
        String markerName = "Point " + markerCounter.getAndIncrement();
        MarkerListItem item = MarkerListItem.from(marker, loadedImage, markerName);
        markerListItemMap.put(marker, item);
        this.getChildren().add(item);
    }

    public void removeMarkerListItem(Marker marker) {
        this.getChildren().remove(markerListItemMap.remove(marker));
    }
}
