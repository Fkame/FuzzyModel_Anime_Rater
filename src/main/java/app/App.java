package app;

import java.math.BigDecimal;
import java.util.List;

import app.Factory.FuzzySetsCreatingFactory;
import app.Factory.RulesCreatingFactory;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzyRule;
import app.RatingFuzzyModel.fuzzyModelCore.FuzzySet;
import app.controller.FuzzyChartsController;
import app.controller.MainSceneController;
import app.controller.RulesBaseSceneController;
import app.ui.PathsToFXML;
import app.ui.ScenesLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {

    public static final String pathToIcon = "/app/images/icon.jpg";
    
    // Сцены
    public static Scene mainScene = null;
    public static Scene fuzzyChartsScene = null;
    public static Scene rulesBaseScene = null;
    public static Scene fuzzyModelScene = null;

    // Контроллеры
    public static MainSceneController mainSceneController = null;
    public static FuzzyChartsController fuzzyChartsSceneController = null;
    public static RulesBaseSceneController rulesBaseSceneController = null;

    // Нечёткие переменные
    public static List<FuzzySet> soundVariable;
    public static List<FuzzySet> animationVariable;
    public static List<FuzzySet> storyVariable;
    public static List<FuzzySet> charactersVariable;
    public static List<FuzzySet> ratingVariable;

    // База правил
    public static List<FuzzyRule> rules;

    @Override
    public void start(Stage stage) {
        // System.out.println(getClass().getResource("/app/fxml/main_scene.fxml").getFile());

        // Scenes creating
        this.prepareMainScene(stage);
        this.prepareFuzzyChartScene(stage);
        this.prepareRulesBaseScene(stage);
        this.prepareFuzzyModelScene(stage);

        this.loadIcon(stage);

        // Scenes filling
        this.prepareFuzzyModel();
        this.drawVariablesOnFuzzyCharts();
        this.drawRuleBase();

        // Stage configs
        stage.setTitle("Fuzzy anime rater - Main menu");
        stage.setScene(App.mainScene);
        stage.centerOnScreen();
        stage.setResizable(false);

        stage.show();
    }

    private void prepareMainScene(Stage stage) {
        ScenesLoader loader = new ScenesLoader();
        App.mainScene = loader.loadScene(PathsToFXML.MainScenePath);
        if (App.mainScene == null) System.exit(-1);
        
        App.mainSceneController = loader.getLoader().getController();
        App.mainSceneController.activateSliderListening(66);
        App.mainSceneController.setStage(stage);
    }

    private void prepareFuzzyChartScene(Stage stage) {
        ScenesLoader loader = new ScenesLoader();
        App.fuzzyChartsScene = loader.loadScene(PathsToFXML.FuzzyChartPath);
        if (App.fuzzyChartsScene == null) System.exit(-1);  
        App.fuzzyChartsSceneController = loader.getLoader().getController();
        App.fuzzyChartsSceneController.setStage(stage);
    }

    private void prepareRulesBaseScene(Stage stage) {
        ScenesLoader loader = new ScenesLoader();
        App.rulesBaseScene = loader.loadScene(PathsToFXML.RulesBasePath);
        if (App.fuzzyChartsScene == null) System.exit(-1);
        App.rulesBaseSceneController = loader.getLoader().getController();
        App.rulesBaseSceneController.setStage(stage);
    }

    private void drawVariablesOnFuzzyCharts() {
        // drawing Sound Variable
        var series = App.fuzzyChartsSceneController.prepareSeries(App.soundVariable, new BigDecimal(0.1));
        App.fuzzyChartsSceneController.drawSoundChart(series);

        // drawing Animation Variable
        series = App.fuzzyChartsSceneController.prepareSeries(App.animationVariable, new BigDecimal(0.05));
        App.fuzzyChartsSceneController.drawAnimationChart(series);

        // drawing Story Variable
        series = App.fuzzyChartsSceneController.prepareSeries(App.storyVariable, new BigDecimal(0.01));
        App.fuzzyChartsSceneController.drawStoryChart(series);

        // drawing Characters Variable
        series = App.fuzzyChartsSceneController.prepareSeries(App.charactersVariable, new BigDecimal(0.05));
        App.fuzzyChartsSceneController.drawCharactersChart(series);

        // drawing Sound Variable
        series = App.fuzzyChartsSceneController.prepareSeries(App.ratingVariable, new BigDecimal(0.1));
        App.fuzzyChartsSceneController.drawRatingChart(series);

    }

    private void prepareFuzzyModel() {
       App.soundVariable = FuzzySetsCreatingFactory.getSoundVariable();     
       App.animationVariable = FuzzySetsCreatingFactory.getAnimationVariable();   
       App.storyVariable = FuzzySetsCreatingFactory.getStoryVariable();
       App.charactersVariable = FuzzySetsCreatingFactory.getCharactersVariable();
       App.ratingVariable = FuzzySetsCreatingFactory.getRatingVariable();

       App.rules = RulesCreatingFactory.getRulesBase(ratingVariable, soundVariable, animationVariable, 
                                            storyVariable, charactersVariable);
    }

    private void drawRuleBase() {
        App.rulesBaseSceneController.drawRules();
    }

    private void prepareFuzzyModelScene(Stage stage) {

    }

    private void loadIcon(Stage stage) {
        try {
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/app/images/icon.jpg")));
        } catch (Exception ex) { 
            System.out.println("Icon not found!");
            ex.printStackTrace(); 
        }
    }

    public static void main(String[] args) {
        launch();
    }
}