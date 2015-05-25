package test.controller;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import test.query.QuaggansQuery;

/** 
 * Contrôleur pour l'IU QuagganSelector.
 * @author Fabrice Bouyé
 */
public final class QuagganSelectorController implements Initializable {

    @FXML
    private ComboBox<String> quagganIdCombo;
    @FXML
    private ImageView quagganImage;
    @FXML
    private ProgressIndicator progressIndicator;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        quagganImage.setVisible(false);
        //        
        progressIndicator.setVisible(false);
        progressIndicator.setProgress(0);
        //        
        quagganIdCombo.getSelectionModel().selectedItemProperty().addListener(observable -> {
            final String quagganId = quagganIdCombo.getSelectionModel().getSelectedItem();
            queryQuagganImage(quagganId);
        });
        // Chargement de la liste des Ids des quaggans.
        loadQuagganList();
    }

    /**
     * Charge la liste des Ids des quaggans de manière asynchrone.
     */
    private void loadQuagganList() {
        final Service<ObservableList<String>> query = new Service<ObservableList<String>>() {

            @Override
            protected Task<ObservableList<String>> createTask() {
                return new Task<ObservableList<String>>() {

                    @Override
                    protected ObservableList<String> call() throws Exception {
                        final List<String> quagganList = QuaggansQuery.list();
                        // On transforme la liste en liste observable.
                        final ObservableList<String> observableQuagganList = FXCollections.observableList(quagganList);
                        // On transforme la liste observable en liste observable triée.
                        final SortedList<String> sortedQuagganList = new SortedList<>(observableQuagganList, String::compareTo);
                        return sortedQuagganList;
                    }
                };
            }
        };
        query.setOnSucceeded(workerStateEvent -> {
            final ObservableList<String> guagganList = query.getValue();
            quagganIdCombo.setItems(guagganList);
        });
        query.setOnFailed(workerStateEvent -> {
            System.out.println(query.getException());
        });
        query.start();
    }

    /**
     * Chargement de l'URL de l'image sélectionnée de manière asynchrone.
     * @param quagganId Id à charger.
     */
    private void queryQuagganImage(final String quagganId) {
        if (quagganId == null) {
            return;
        }
        // Avant le début de la requête, on va modifier l'apparence de l'interface.
        quagganImage.setVisible(false);
        quagganImage.setImage(null);
        progressIndicator.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(true);
        //
        final Service<String> query = new Service<String>() {

            @Override
            protected Task<String> createTask() {
                return new Task<String>() {

                    @Override
                    protected String call() throws Exception {
                        return QuaggansQuery.imageURLForId(quagganId);
                    }
                };
            }
        };
        query.setOnSucceeded(workerStateEvent -> {
            // Lorsque l'URL de l'image a été récupérée, l'image est chargée de manière asynchrone.
            final String quagganURL = query.getValue();
            loadQuagganImage(quagganURL);
        });
        query.setOnFailed(workerStateEvent -> {
            System.out.println(query.getException());
        });
        query.start();
    }

    /**
     * Chargement de l'image sélectionnée de manière asynchrone.
     * @param quagganURL URL de l'image à charger.
     */
    private void loadQuagganImage(final String quagganURL) {
        final Image quagganIcon = new Image(quagganURL, true);
        quagganIcon.progressProperty().addListener(observable -> {
            displayQuagganImage(quagganIcon);
        });
        quagganIcon.exceptionProperty().addListener(observable -> {
            System.out.println(quagganIcon.getException());
        });
        // Si l'image est suffisament petite ou si la connexion est suffisament rapide, l'image est peut-être déjà chargée.
        displayQuagganImage(quagganIcon);
    }

    /**
     * Affiche l'image et restaure l'interface.
     * @param quagganIcon L'image à afficher.
     */
    private void displayQuagganImage(final Image quagganIcon) {
        if (quagganIcon.getProgress() == 1) {
            progressIndicator.setVisible(false);
            progressIndicator.setProgress(0);
            quagganImage.setVisible(true);
            quagganImage.setImage(quagganIcon);
        }
    }
}
