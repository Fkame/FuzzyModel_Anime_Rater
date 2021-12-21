package app.controller;

import app.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.stage.Stage;

public class FuzzyChartsController {

    private Stage stage;

    public void setStage(Stage stage) { this.stage = stage;}

    @FXML
    private LineChart<Number, Number> soundChart;

    @FXML
    private LineChart<Number, Number> animationChart;

    @FXML
    private LineChart<Number, Number> storyChart;

    @FXML
    private LineChart<Number, Number> charactersChart;

    public FuzzyChartsController() { 
        /*
        Axis<Number> xAxis;
        Axis<Number> yAxis;

        xAxis = soundChart.getXAxis();
        yAxis = soundChart.getYAxis();
        */
    }

    public void backButtonClicked(ActionEvent event) {
        stage.setScene(App.mainScene);
        stage.centerOnScreen();
    }

    public void drawSoundChart() {

    }

    public void drawAnimationChart() {
        
    }

    public void drawStoryChart() {
        
    }

    public void drawCharactersChart() {
        
    }

}