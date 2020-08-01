package org.freddydurkee.marker.view;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import javafx.util.converter.NumberStringConverter;
import org.freddydurkee.marker.App;
import org.freddydurkee.marker.model.Marker;

import java.io.IOException;

public class MarkerListItem extends GridPane {


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


    public static MarkerListItem from(Marker marker, Image loadedImage, String pointName) {
        MarkerListItem item = new MarkerListItem();

        StringConverter<Number> converter = new NumberStringConverter();
        Bindings.bindBidirectional(item.xValue.textProperty(), marker.xProperty(), converter);
        Bindings.bindBidirectional(item.yValue.textProperty(), marker.yProperty(), converter);
        item.markListItemLabel.setTextFill(marker.getColor());
        item.markListItemLabel.setText(pointName);
        item.setPointConstraints((int) loadedImage.getHeight(), (int) loadedImage.getWidth());

        return item;
    }

    private void setPointConstraints(int height, int width) {
        addTextFieldNumberConstraint(xValue, 0, width);
        addTextFieldNumberConstraint(yValue, 0, height);
    }

    private void addTextFieldNumberConstraint(TextField textField, int minVal, int maxVal) {
        textField.setTextFormatter(new TextFormatter<Integer>(change -> {
            if (change.isDeleted()) {
                return change;
            }

            String txt = change.getControlNewText();

            if (txt.matches("0\\d+")) {
                return null;
            }
            try {
                int n = Integer.parseInt(txt);
                return minVal <= n && n <= maxVal ? change : null;
            } catch (NumberFormatException e) {
                return null;
            }
        }));
    }

}
