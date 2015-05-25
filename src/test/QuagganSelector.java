package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** 
 * Application QuagganSelector.
 * @author Fabrice Bouyé
 */
public final class QuagganSelector extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        // Chargement de l'interface graphique.
        final ResourceBundle bundle = ResourceBundle.getBundle("test.QuagganSelector"); // NOI18N.
        final URL fxmlURL = getClass().getResource("QuagganSelector.fxml"); // NOI18N.
        final FXMLLoader fxmlLoader = new FXMLLoader(fxmlURL, bundle);
        final Parent root = fxmlLoader.load();
        // Affichage de la fenêtre.
        final Scene scene = new Scene(root);
        final URL cssURL = getClass().getResource("QuagganSelector.css"); // NOI18N.
        scene.getStylesheets().add(cssURL.toExternalForm());
        primaryStage.setTitle(bundle.getString("app.title")); // NOI18N.
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}
