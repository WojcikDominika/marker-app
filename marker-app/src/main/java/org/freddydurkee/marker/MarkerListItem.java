package org.freddydurkee.marker;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;

import java.io.IOException;

public class MarkerListItem extends GridPane{

    public MarkerListItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("markerListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }
}
