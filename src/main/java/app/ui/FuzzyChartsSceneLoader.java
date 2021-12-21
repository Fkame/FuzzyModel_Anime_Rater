package app.ui;

import java.io.IOException;

import app.controller.FuzzyChartsController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class FuzzyChartsSceneLoader {
    
    public FuzzyChartsSceneLoader() { }

    public static final String pathToFXML = "/app/fxml/fuzzy_charts_scene.fxml";

    private Parent root = null;
    private FXMLLoader loader = null;

    public Scene loadScene() throws IOException {
        loader = new FXMLLoader(getClass().getResource(pathToFXML));
        root = loader.load();
        return new Scene(root);
    }

    public FXMLLoader getLoader() { return this.loader; }
    public Parent getRoot() { return this.root; }
    public FuzzyChartsController getController() { return this.loader.getController(); }
}
