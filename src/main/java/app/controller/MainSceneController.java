package app.controller;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainSceneController {
    
    @FXML
    private Slider opacitySlider;

    @FXML
    private Pane imageBlackout;

    @FXML
    private ImageView imageView;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void goToChartsButtonClicked(ActionEvent event) {
        stage.setScene(App.fuzzyChartsScene);
        stage.centerOnScreen();
        System.out.println("--> goToChartsButtonClickedButtonClicked");
    }

    public void goToRulesBaseButtonClicked(ActionEvent event) {
        App.rulesBaseSceneController.setPictureAndBlackout(imageView.getImage(), imageBlackout.getOpacity());
        stage.setScene(App.rulesBaseScene);
        stage.centerOnScreen();
        System.out.println("goToRulesBaseButtonClicked");
    }

    public void goToModelButtonClicked(ActionEvent event) {
        App.fuzzyModelSceneController.setPictureAndBlackout(imageView.getImage(), imageBlackout.getOpacity());
        stage.setScene(App.fuzzyModelScene);
        stage.centerOnScreen();
        System.out.println("goToModelButtonClicked");
    }

    public void opacitySliderMoved(Number oldValue, Number newValue) {
        if (Math.abs(newValue.intValue() - oldValue.intValue()) == 0) return;
        imageBlackout.setOpacity(newValue.intValue() / 100.0);
    }

    public void activateSliderListening(int blackoutInPercents) {
        opacitySlider.valueProperty().addListener((obs, oldValue, newValue) -> opacitySliderMoved(oldValue, newValue));
        imageBlackout.setOpacity(blackoutInPercents / 100.0);
        opacitySlider.setValue(blackoutInPercents);
    }

    public Image getBackgroundImage() {
        return imageView.getImage();
    }

    public int getBlackoutValue() {
        return imageBlackout.opacityProperty().intValue();
    }
}
