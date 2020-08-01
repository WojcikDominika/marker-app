package org.freddydurkee.marker;

import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.freddydurkee.marker.model.Marker;
import org.freddydurkee.marker.view.MarkerListItem;
import org.freddydurkee.marker.view.MarkerListView;
import org.freddydurkee.marker.view.MarkerableImageView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class MainViewController {

    @FXML
    private Button loadImageBtn;

    @FXML
    private MarkerListView markersList;

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

    private Image loadedImage;

    @FXML
    public void initialize() {
        imgContainer1.addMarkerList(markers);
        imgContainer2.addMarkerList(markers);
        imgContainer3.addMarkerList(markers);
        imgContainer4.addMarkerList(markers);
        markers.addListener(registerMarkerListItem(markers));
    }

    public ListChangeListener<Marker> registerMarkerListItem(ObservableList<Marker> markers) {
        return change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (Marker marker : change.getAddedSubList()) {
                        markersList.addMarkerListItem(marker, loadedImage);
                    }
                }
                if (change.wasRemoved()) {
                    for (Marker marker : change.getRemoved()) {
                        markersList.removeMarkerListItem(marker);

                    }
                }
            }
        };
    }


    @FXML
    public void openFileChooser(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(loadImageBtn.getScene().getWindow());
        if (selectedFile != null) {
            markers.clear();
            markersList.resetMarkerCounter();

            loadedImage = new Image(selectedFile.toURI().toString());

            imageView1.setImage(loadedImage);
            imageView2.setImage(loadedImage);
            imageView3.setImage(loadedImage);
            imageView4.setImage(loadedImage);
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
