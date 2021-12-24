package app.controller;

import java.util.Arrays;
import java.util.List;

import app.App;
import app.RatingFuzzyModel.ModelResult;
import app.RatingFuzzyModel.RatingFuzzyModel;
import app.RatingFuzzyModel.data.FuzzyConclusionFigure;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FuzzyModelSceneController {
    
    private Stage stage = null;
    public void setStage(Stage stage) { this.stage = stage; }

    public static final Integer UPPER_LIMIT_FOR_VARIABLES = 10;
    public static final Integer BOTTOM_LIMIT_FOR_VARIABLES = 0;

    @FXML
    private Button backButton;

    @FXML 
    private ImageView imageView;

    @FXML
    private Pane imageBlackout;

    @FXML
    private AreaChart<Double, Double> conclusionsChart;

    @FXML
    private TextField soundField;

    @FXML
    private TextField animationField;

    @FXML
    private TextField storyField;

    @FXML
    private TextField charactersField;

    @FXML
    private TextArea resultsArea;

    @FXML
    private Button modelStartButton;

    @FXML
    private Button clearButton;

    public void clearButtonClicked(ActionEvent event) {
        conclusionsChart.getData().clear();
    }

    public void modelStartClicked(ActionEvent event) {
        Integer sound = null;
        Integer animation = null;
        Integer story = null;
        Integer characters = null;

        // Валидация
        String fieldText = "";
        String fieldName = "";
        try {
            fieldText = soundField.getText().strip();
            fieldName = "Звук";
            sound = Integer.parseInt(soundField.getText());
            
            fieldText = animationField.getText().strip();
            fieldName = "Анимация";
            animation = Integer.parseInt(animationField.getText());
            
            fieldText = storyField.getText().strip();
            fieldName = "Сюжет";
            story = Integer.parseInt(storyField.getText());
            
            fieldText = charactersField.getText().strip();
            fieldName = "Персонажи";
            characters = Integer.parseInt(charactersField.getText());
        } catch (Exception ex) {
            createFieldAlert(fieldName, fieldText).showAndWait();
            return;
        }

        if ((sound > UPPER_LIMIT_FOR_VARIABLES | sound < BOTTOM_LIMIT_FOR_VARIABLES) |
            (animation > UPPER_LIMIT_FOR_VARIABLES | animation < BOTTOM_LIMIT_FOR_VARIABLES) |
            (story > UPPER_LIMIT_FOR_VARIABLES | story < BOTTOM_LIMIT_FOR_VARIABLES) |
            (characters > UPPER_LIMIT_FOR_VARIABLES | characters < BOTTOM_LIMIT_FOR_VARIABLES)) 
        {
            numberNotInInterval().showAndWait();
        }
        
        // Модель
        RatingFuzzyModel fuzzyModel = new RatingFuzzyModel(App.rules, App.ratingVariable);
        ModelResult results = fuzzyModel.evaluteAnime(Arrays.asList((double)sound, (double)animation, (double)story, (double)characters));
        FuzzyConclusionFigure figure = fuzzyModel.getFuzzyConclusionFigure();
        this.drawConclusionFigure(figure.pointsX, figure.pointsY);
        this.printResultsToArea(results);
    }

    private void drawConclusionFigure(List<Double> pointsX, List<Double> pointsY) {
        XYChart.Series<Double, Double> ser = new XYChart.Series<>();
        int len = Math.min(pointsX.size(), pointsY.size());
        
        for (int i = 0; i < len; i++) {
            ser.getData().add(new XYChart.Data<Double, Double>(pointsX.get(i), pointsY.get(i)));
        }
        conclusionsChart.getData().add(ser);
    }

    private void printResultsToArea(ModelResult results) {
        StringBuilder builder = new StringBuilder();
        builder.append("--- Результаты оценки аниме нечёткой моделью ---\n");
        builder.append("Входные параметры: " + results.inputs.toString() + "\n");
        builder.append("Нечёткий результат: " + results.fuzzyScore + "\n");
        builder.append("Чёткий результат: " + results.outputScore);
        this.resultsArea.setText(builder.toString());
    }

    private Alert createFieldAlert(String fieldName, String enteredValue) {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Некорректные данные");
        alert.setContentText("Поле [" + fieldName + "] не может иметь значение =[" + enteredValue +"]!");
        return alert;
    }

    private Alert numberNotInInterval() {
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Некорректные данные");
        String text = "В одном из полей число находится вне допустимого интвервала!";
        alert.setContentText(text + " Допустимые значения от " + BOTTOM_LIMIT_FOR_VARIABLES + " до " + UPPER_LIMIT_FOR_VARIABLES + ".");
        return alert;
    }

    public void backButtonClicked(ActionEvent event) {
        stage.setScene(App.mainScene);
        stage.centerOnScreen();
    }

    public void setPictureAndBlackout(Image image, double blackoutValue) {
        imageBlackout.setOpacity(blackoutValue);
        imageView.setImage(image);
    }
}
