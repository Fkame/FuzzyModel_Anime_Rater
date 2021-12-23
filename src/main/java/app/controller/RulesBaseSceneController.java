package app.controller;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class RulesBaseSceneController {

    public RulesBaseSceneController() { }

    private Stage stage;
    public void setStage(Stage stage) { this.stage = stage;}

    @FXML
    private ImageView imageView;

    @FXML
    private Pane imageBlackout;

    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private VBox labesContainer;

    @FXML 
    private Label exampleLabel;

    @FXML
    private Button backButton;

    public void backButtonClicked(ActionEvent event) {
        stage.setScene(App.mainScene);
        stage.centerOnScreen();
    }

    public void drawRules() {
        
    }

    public void setPictureAndBlackout(Image image, double blackoutValue) {
        imageBlackout.setOpacity(blackoutValue);
        imageView.setImage(image);
    }
}
