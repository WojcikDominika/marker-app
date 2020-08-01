package org.freddydurkee.marker.view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.freddydurkee.marker.App;
import org.freddydurkee.marker.model.Marker;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class MarkerListItem extends GridPane {

    private static final AtomicInteger ITEM_COUNTER = new AtomicInteger();

    @FXML
    TextField xValue;

    @FXML
    TextField yValue;

    @FXML
    Label markListItemLabel;


    private MarkerListItem() {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("markerListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }


    public static MarkerListItem from(Marker marker) {
        MarkerListItem item = new MarkerListItem();

        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(item.xValue.textProperty(), marker.xProperty(), converter);
        Bindings.bindBidirectional(item.yValue.textProperty(), marker.yProperty(), converter);
        item.markListItemLabel.setTextFill(marker.getColor());
        item.markListItemLabel.setText("Point " + ITEM_COUNTER.getAndIncrement());

        return item;
    }
}
