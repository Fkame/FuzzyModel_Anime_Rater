package app.controller;

import app.App;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzyRule;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;
import app.ui.RulesSchemeColors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class RulesBaseSceneController {

    public RulesBaseSceneController() { }

    private Stage stage;
    public void setStage(Stage stage) { this.stage = stage;}

    public double labelCornerRadii = 2;
    public double bottomMargin = 5;

    @FXML
    private ImageView imageView;

    @FXML
    private Pane imageBlackout;

    @FXML 
    private ScrollPane scrollPane;

    @FXML
    private VBox labelsContainer;

    @FXML 
    private Label exampleLabel;

    @FXML
    private Button backButton;

    public void backButtonClicked(ActionEvent event) {
        stage.setScene(App.mainScene);
        stage.centerOnScreen();
    }

    public void drawRules() {
        exampleLabel.setVisible(false);
        for (FuzzyRule rule : App.rules) {
            String ruleString = rule.toString();

            Label ruleLabel = new Label(ruleString);
            ruleLabel.setFont(exampleLabel.getFont());
            ruleLabel.setTextFill(exampleLabel.getTextFill());
            Color ruleColor = this.getColorByTermName(rule.getConclusionTerm());
            ruleLabel.setBackground(new Background(
                        new BackgroundFill(ruleColor, new CornerRadii(labelCornerRadii), new Insets(0, 0, bottomMargin, 0))
            ));
            labelsContainer.getChildren().add(ruleLabel);
        }
    }

    public Color getColorByTermName(FuzzySet term) {
        switch (term.getFuzzySetName()) {
            case "Very bad":
                return RulesSchemeColors.veryBad;
            case "Bad":
                return RulesSchemeColors.bad;
            case "Normal":
                return RulesSchemeColors.normal;
            case "Good":
                return RulesSchemeColors.good;
            case "Very good":
                return RulesSchemeColors.veryGood;
            default:
                return RulesSchemeColors.normal;
        }
    }

    public void setPictureAndBlackout(Image image, double blackoutValue) {
        imageBlackout.setOpacity(blackoutValue);
        imageView.setImage(image);
    }
}
