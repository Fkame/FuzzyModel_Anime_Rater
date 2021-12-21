package app;

import app.controller.MainSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * JavaFX App
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        stage.centerOnScreen();

        // System.out.println(getClass().getResource("/app/fxml/main_scene.fxml").getFile());
        FXMLLoader loader = null;
        Parent root = null;
        try {
            loader = new FXMLLoader(getClass().getResource("/app/fxml/main_scene.fxml"));
            root = loader.load();
        } catch (Exception e) { e.printStackTrace(); }

        MainSceneController mainController = loader.getController();
        mainController.activateSliderListening();
        mainController.setStartImageBlackout(0.66);

        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }


}