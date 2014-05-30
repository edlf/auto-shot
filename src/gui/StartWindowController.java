package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Eduardo Fernandes
 * <p/>
 * Main Window controller
 * Filipe Eiras
 */
public class StartWindowController extends GridPane implements Initializable {
    @FXML
    Button buttonPlay;
    @FXML
    MenuButton algorithmCombo;
    @FXML
    Button buttonAutoPlay;
    @FXML
    Button buttonCancel;
    private Main application;

    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handlePlayButtonAction(ActionEvent event) {
        if (application != null) {
            application.startGame();
        }
    }

    public void handleAutoPlayButtonAction(ActionEvent event) {
        if (application != null) {
            application.startGameAuto();
        }
    }

    public void handleLevelSelectButtonAction(ActionEvent event) {
        if (application != null) {
            application.startLevelSelector();
        }
    }

    public void handleExitButtonAction(ActionEvent event) {
        if (application != null) {
            application.exit();
        }
    }
}
