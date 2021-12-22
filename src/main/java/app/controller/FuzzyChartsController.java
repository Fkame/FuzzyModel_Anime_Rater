package app.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import app.App;
import app.fuzzyModelCore.FuzzySet;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

public class FuzzyChartsController {

    private Stage stage;

    public void setStage(Stage stage) { this.stage = stage;}

    @FXML
    private LineChart<Double, Double> soundChart;

    @FXML
    private LineChart<Double, Double> animationChart;

    @FXML
    private LineChart<Double, Double> storyChart;

    @FXML
    private LineChart<Double, Double> charactersChart;

    @FXML
    private LineChart<Double, Double> ratingChart;
    
    public FuzzyChartsController() {  }

    public void backButtonClicked(ActionEvent event) {
        stage.setScene(App.mainScene);
        stage.centerOnScreen();
    }

    public List<XYChart.Series<Double,Double>> prepareSeries(List<FuzzySet> variable, BigDecimal step) {
        List<XYChart.Series<Double,Double>> series = new ArrayList<>();
        for (FuzzySet term : variable) {
            XYChart.Series<Double,Double> ds = new XYChart.Series<>();
            ds.setName(term.getFuzzySetName());

            BigDecimal x = BigDecimal.valueOf(term.getMinX());
            BigDecimal limitX = BigDecimal.valueOf(term.getMaxX());
            while (x.compareTo(limitX) != 1) {
                double y = term.calculateY(x.doubleValue());
                ds.getData().add(new XYChart.Data<Double,Double>(x.doubleValue(), y));
                x = x.add(step);
            }
            if (x.compareTo(limitX) == 1) {
                double y = term.calculateY(limitX.doubleValue());
                ds.getData().add(new XYChart.Data<Double,Double>(x.doubleValue(), y));
                x = x.add(step);
            }
            series.add(ds);
        }
        return series;
    }

    public void drawSoundChart(List<XYChart.Series<Double,Double>> series) {   
        /*
        List<FuzzySet> soundVariable = App.soundVariable;
        BigDecimal step = BigDecimal.valueOf(0.1);
       
        for (FuzzySet term : soundVariable) {
            XYChart.Series<Double,Double> ds = new XYChart.Series<>();
            ds.setName(term.getFuzzySetName());

            BigDecimal x = BigDecimal.valueOf(term.getMinX());
            BigDecimal limitX = BigDecimal.valueOf(term.getMaxX());
            while (x.compareTo(limitX) != 1) {
                double y = term.calculateY(x.doubleValue());
                ds.getData().add(new XYChart.Data<Double,Double>(x.doubleValue(), y));
                x = x.add(step);
            }
            soundChart.getData().add(ds);
        }*/
        soundChart.getData().addAll(series);
    }

    public void drawAnimationChart() {
        
    }

    public void drawStoryChart() {
        
    }

    public void drawCharactersChart() {
        
    }

    public void drawRatingChart(List<XYChart.Series<Double,Double>> series) {
        ratingChart.getData().addAll(series);
    }

}