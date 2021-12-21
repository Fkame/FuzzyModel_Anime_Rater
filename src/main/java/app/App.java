package app;

import java.io.IOException;

import app.controller.MainSceneController;
import app.ui.FuzzyChartsSceneLoader;
import app.ui.MainSceneLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    public static final String pathToIcon = "/app/images/icon.jpg";
    
    public static Scene mainScene = null;
    public static MainSceneController mainSceneController = null;
    public static Scene fuzzyChartsScene = null;
    public static Scene rulesBaseScene = null;
    public static Scene fuzzyModelScene = null;

    @Override
    public void start(Stage stage) {
        // System.out.println(getClass().getResource("/app/fxml/main_scene.fxml").getFile());

        this.prepareMainScene(stage);
        this.prepareFuzzyChartScene(stage);
        this.prepareRulesBaseScene(stage);
        this.prepareFuzzyModelScene(stage);
        this.loadIcon(stage);

        stage.setTitle("Fuzzy anime rater - Main menu");
        stage.setResizable(false);

        stage.setScene(App.mainScene);
        stage.centerOnScreen();

        stage.show();
    }

    private void prepareMainScene(Stage stage) {
        MainSceneLoader loader = new MainSceneLoader();
        try { 
            App.mainScene = loader.loadScene(); 
        }
        catch (Exception e) { 
            System.out.println("Error while loading Main Scene!"); 
            e.printStackTrace(); 
            return;
        }
        App.mainSceneController = loader.getController();
        App.mainSceneController.activateSliderListening(66);
        App.mainSceneController.setStage(stage);
    }

    private void prepareFuzzyChartScene(Stage stage) {
        FuzzyChartsSceneLoader fuzzLoader = new FuzzyChartsSceneLoader();
        try {
            App.fuzzyChartsScene = fuzzLoader.loadScene();
        } catch (IOException ex) { 
            System.out.println("Cannot load new Scene - FuzzyChartSceneLoader!");
            ex.printStackTrace();
            return;
        }
        fuzzLoader.getController().setStage(stage);
    }

    private void prepareRulesBaseScene(Stage stage) {

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