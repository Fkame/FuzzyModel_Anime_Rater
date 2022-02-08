package app.controller;

import java.io.File;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class MainSceneController {
    
    @FXML
    private Slider opacitySlider;

    @FXML
    private Pane imageBlackout;

    @FXML
    private ImageView imageView;

    private Stage stage;

    @FXML
    private Button changeBackgroundBtn;

    private FileChooser fchos;

    public MainSceneController() { }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void goToChartsButtonClicked(ActionEvent event) {
        stage.setScene(App.fuzzyChartsScene);
        stage.centerOnScreen();
        //System.out.println("--> goToChartsButtonClickedButtonClicked");
    }

    public void goToRulesBaseButtonClicked(ActionEvent event) {
        App.rulesBaseSceneController.setPictureAndBlackout(imageView.getImage(), imageBlackout.getOpacity());
        stage.setScene(App.rulesBaseScene);
        stage.centerOnScreen();
        //System.out.println("goToRulesBaseButtonClicked");
    }

    public void goToModelButtonClicked(ActionEvent event) {
        App.fuzzyModelSceneController.setPictureAndBlackout(imageView.getImage(), imageBlackout.getOpacity());
        stage.setScene(App.fuzzyModelScene);
        stage.centerOnScreen();
        //System.out.println("goToModelButtonClicked");
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

    public void setChangeBackgroundButtonText(String text) {
        this.changeBackgroundBtn.setMaxWidth(-1);
        this.changeBackgroundBtn.setMaxHeight(-1);
        this.changeBackgroundBtn.setPrefHeight(-1);
        this.changeBackgroundBtn.setPrefWidth(-1);
        this.changeBackgroundBtn.setText(text);
    }

    public void changeBackgroundClicked(ActionEvent event) {
        if (this.fchos == null) this.createFileChooserForImage();
        File file = fchos.showOpenDialog(this.stage);
        if (file == null) {
            Alert alert = this.alertImgNotFound();
            alert.show();
            return;
        }
    
        try {
            Image newimg = new Image(file.getAbsolutePath());
            this.imageView.setImage(newimg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            Alert alert = this.alertImgNotFound();
            alert.show();
            return;
        }
    }

    private Alert alertImgNotFound() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Проблемы с загрузкой изображения");
        alert.setContentText("Почему-то не получилось загрузить изображение :(");
        return alert;
    }

    private void createFileChooserForImage() {
        fchos = new FileChooser();
        fchos.setTitle("Выберите новое фоновое изображение");
        fchos.setInitialDirectory(new File(System.getProperty("user.home")));
        fchos.getExtensionFilters().addAll(
            new FileChooser.ExtensionFilter("images (jpeg, jpg, png, gif)", "*.jpeg", "*.jpg", "*.png", "*gif")
        );
    }

    public boolean setChangeBackgroundButtonImg(String pathToImg) {
        try {
            Image img = new Image(pathToImg);      
            ImageView imgView = new ImageView(img);
            imgView.setFitWidth(this.changeBackgroundBtn.getPrefWidth());
            imgView.setFitHeight(this.changeBackgroundBtn.getPrefHeight());
            imgView.setPreserveRatio(true);
            imgView.setSmooth(true);
            changeBackgroundBtn.setGraphic(imgView);
           
            return true;
        } catch (NullPointerException e1) { 
            System.out.println(e1.getMessage() + "\n");
            e1.printStackTrace();
        } catch (IllegalArgumentException e2) {
            System.out.println(e2.getMessage() + "\n");
            e2.printStackTrace();
        }
        return false;
    }
}
