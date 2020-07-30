package org.freddydurkee.marker;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import org.freddydurkee.marker.view.ResizableImageView;

import java.io.File;

public class MainViewController {


    @FXML
    private Button loadImageBtn;

    @FXML
    private GridPane grid;

    @FXML
    private ResizableImageView imgContainer1;

    @FXML
    private ImageView imageView1;

    @FXML
    private ResizableImageView imgContainer2;

    @FXML
    private ImageView imageView2;

    @FXML
    private ResizableImageView imgContainer3;

    @FXML
    private ImageView imageView3;

    @FXML
    private ResizableImageView imgContainer4;

    @FXML
    private ImageView imageView4;


    @FXML
    public void openFileChooser(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource Image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg"));
        File selectedFile = fileChooser.showOpenDialog(loadImageBtn.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());

            imageView1.setImage(image);
            imageView2.setImage(image);
            imageView3.setImage(image);
            imageView4.setImage(image);
        }
    }

    @FXML
    public void createMarker(MouseEvent mouseEvent) {
        imgContainer1.addMarker(mouseEvent);
    }
}
