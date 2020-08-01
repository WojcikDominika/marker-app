package org.freddydurkee.marker;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.freddydurkee.marker.model.Marker;
import org.freddydurkee.marker.view.MarkerListItem;
import org.freddydurkee.marker.view.MarkerableImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MainViewController {


    @FXML
    private Button loadImageBtn;

    @FXML
    private VBox markersList;

    @FXML
    private MarkerableImageView imgContainer1;

    @FXML
    private ImageView imageView1;

    @FXML
    private MarkerableImageView imgContainer2;

    @FXML
    private ImageView imageView2;

    @FXML
    private MarkerableImageView imgContainer3;

    @FXML
    private ImageView imageView3;

    @FXML
    private MarkerableImageView imgContainer4;

    @FXML
    private ImageView imageView4;
    ObservableList<Marker> markers = FXCollections.observableArrayList();
    Map<Marker, MarkerListItem> markerListItemMap = new HashMap<>();

    @FXML
    public void initialize() {
        imgContainer1.addMarkerList(markers);
        imgContainer2.addMarkerList(markers);
        imgContainer3.addMarkerList(markers);
        imgContainer4.addMarkerList(markers);

        markers.addListener((ListChangeListener<Marker>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Marker marker : change.getAddedSubList()) {
                        MarkerListItem item = MarkerListItem.from(marker);
                        markerListItemMap.put(marker, item);
                        markersList.getChildren().add(item);
                    }
                }
                if (change.wasRemoved()) {
                    for (Marker marker : change.getRemoved()) {
                        markersList.getChildren().remove(markerListItemMap.remove(marker));
                    }
                }
            }

        });
    }

    @FXML
    public void openFileChooser(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(loadImageBtn.getScene().getWindow());
        if (selectedFile != null) {
            markers.clear();

            Image image = new Image(selectedFile.toURI().toString());

            imageView1.setImage(image);
            imageView2.setImage(image);
            imageView3.setImage(image);
            imageView4.setImage(image);
        }
    }

    @FXML
    public void onMarkerableImageClickedAddMarker(MouseEvent mouseEvent) {
        ImageView imgView = (ImageView) mouseEvent.getSource();
        if (imgView.getImage() != null) {
            MarkerableImageView markerableImageView = (MarkerableImageView) imgView.getParent();
            int imgOriginalX = markerableImageView.calculateOriginalImageX(mouseEvent.getX());
            int imgOriginalY = markerableImageView.calculateOriginalImageY(mouseEvent.getY());
            Marker marker = new Marker(imgOriginalX, imgOriginalY);
            markers.add(marker);
        }
    }
}
