package app.ui;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class ScenesLoader {

    private Parent root = null;
    private FXMLLoader loader = null;

    public Scene loadScene(String pathToFXML) {
        try {
            loader = new FXMLLoader(getClass().getResource(pathToFXML));
            root = loader.load();
            return new Scene(root);
        } catch (IOException ex) {
            System.out.println("Error loading " + pathToFXML + " scene!");
            return null;
        }     
    }

    public FXMLLoader getLoader() { return this.loader; }
    public Parent getRoot() { return this.root; }
}
