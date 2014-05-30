package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Eduardo Fernandes
 * Filipe Eiras
 */
public class Main extends Application {
    /* Constants */
    private static final String mainWindowTitle = "Auto-Shot";

    private static final String mainWindowXml = "StartWindow.fxml";
    private static final String gameWindowXml = "GameWindow.fxml";
    private static final String gameWindowAutoXml = "GameWindowAuto.fxml";
    private static final String levelSelectorXml = "MapSelector.fxml";

    public static Image sphereImage;
    public static Image selectedSphereImage;
    public static GameBoard selectedBoard = null;

    /* JavaFX */
    private Stage stage;

    /**
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        logInfo("Application has started");
        Application.launch(Main.class, args);
    }

    public static void exit() {
        logInfo("Application is exiting");
        System.exit(0);
    }

    public static void logInfo(String in) {
        Logger.getLogger(Main.class.getName()).log(Level.INFO, in, (Object) null);
    }

    public static void logSevereAndExit(String in) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, in, (Object) null);
        System.exit(-1);
    }

    public static void logSevereAndExit(Exception ex) {
        Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        System.exit(-1);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        loadResources();

        try {
            stage = primaryStage;
            stage.setTitle(mainWindowTitle);
            stage.setResizable(false);
            gotoStartWindow();
            primaryStage.show();
        } catch (Exception ex) {
            logSevereAndExit(ex);
        }
    }

    private void loadResources() {
        logInfo("Loading resources...");
        try {
            logInfo("Loading wSphere.png...");
            sphereImage = new Image(Main.class.getResourceAsStream("resources/wSphere.png"));
            logInfo("Loading cSphere.png...");
            selectedSphereImage = new Image(Main.class.getResourceAsStream("resources/cSphere.png"));
        } catch (IllegalArgumentException e) {
            logSevereAndExit("Problems while loading resources, exiting.");
        }

    }

    public void gotoStartWindow() {
        try {
            StartWindowController startWindowController = (StartWindowController) replaceSceneContent(mainWindowXml);
            startWindowController.setApp(this);
        } catch (Exception ex) {
            logSevereAndExit(ex);
        }
    }

    public void startGame() {
        logInfo("Loading game scene");
        gotoGameWindow();
    }

    private void gotoGameWindow() {
        try {
            GameWindowController gameWindowController = (GameWindowController) replaceSceneContent(gameWindowXml);
            gameWindowController.setApp(this);
        } catch (Exception ex) {
            logSevereAndExit(ex);
        }
    }

    public void startLevelSelector() {
        logInfo("Loading level selector scene");
        gotoLevelSelectWindow();
    }

    private void gotoLevelSelectWindow() {
        try {
            MapSelectorController mapSelectorController = (MapSelectorController) replaceSceneContent(levelSelectorXml);
            mapSelectorController.setApp(this);
        } catch (Exception ex) {
            logSevereAndExit(ex);
        }
    }

    public void startGameAuto() {
        logInfo("Loading automatic game solver scene");
        gotoGameWindowAuto();
    }

    private void gotoGameWindowAuto() {
        try {
            GameWindowAutoController gameWindowAutoController = (GameWindowAutoController) replaceSceneContent(gameWindowAutoXml);
            gameWindowAutoController.setApp(this);
        } catch (Exception ex) {
            logSevereAndExit(ex);
        }
    }

    /**
     * @param fxml XML scene file to load
     */
    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        GridPane page;
        try {
            page = loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

}
