package app.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;

public class MainSceneController {
    
    @FXML
    private Slider opacitySlider;

    @FXML
    private Pane imageBlackout;

    public void goToChartsButtonClicked(ActionEvent event) {
        System.out.println("goToChartsButtonClickedButtonClicked");
    }

    public void goToRulesBaseButtonClicked(ActionEvent event) {
        System.out.println("goToRulesBaseButtonClicked");
    }

    public void goToModelButtonClicked(ActionEvent event) {
        System.out.println("goToModelButtonClicked");
    }

    public void opacitySliderMoved(Number oldValue, Number newValue) {
        if (Math.abs(newValue.intValue() - oldValue.intValue()) == 0) return;
        System.out.println("newValue=" + newValue.intValue());
        imageBlackout.setOpacity(newValue.intValue() / 100.0);
    }

    public void activateSliderListening() {
        opacitySlider.valueProperty().addListener((obs, oldValue, newValue) -> opacitySliderMoved(oldValue, newValue));
    }

    public void setStartImageBlackout(double value) {
        imageBlackout.setOpacity(value);
        opacitySlider.setValue(value * 100);
    }
}
