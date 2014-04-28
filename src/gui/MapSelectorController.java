package gui;

import javafx.beans.InvalidationListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

/**
 * Eduardo Fernandes
 */
public class MapSelectorController extends GridPane  implements Initializable {
    private ImageView[][] spheres;
    private long[][] levels;
    private int selDif = 0;
    private int selLevel = 0;
    private Vector<String> difficulties;
    private Vector<String> levelNumbers;
    Main application1;
    boolean isLoaded = false;

    @FXML
    Pane mainPane;

    @FXML
    GridPane gameGrid;

    @FXML
    ComboBox difficultySel;

    @FXML
    ComboBox levelSel;

    @FXML
    private void handleCheckBoxAction() {
        if (isLoaded){
            updateSelection();
        }
    }

    public void setApp(Main application) {
        application1 = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadLevels();
            Main.selectedBoard = new GameBoard(levels[selDif][selLevel]);
            setupBoard();
            fillSelectors();
            updateGUI();
            isLoaded = true;
        } catch (Exception e) {
            Main.logSevereAndExit(e);
        }
    }

    private void setupBoard() {
        spheres = new ImageView[GameBoard.horizontalSize][GameBoard.verticalSize];

        for (int x = 0; x < GameBoard.horizontalSize; x++) {
            for (int y = 0; y < GameBoard.verticalSize; y++) {
                spheres[x][y] = new ImageView(Main.sphereImage);
                spheres[x][y].setVisible(false);
                gameGrid.add(spheres[x][y], x, y);
            }
        }
    }

    private void updateGUI() {
        updateBoard();
    }

    private void updateBoard(){
        Main.selectedBoard = new GameBoard(levels[selDif][selLevel]);
        try {
            for (int x = 0; x < GameBoard.horizontalSize; x++) {
                for (int y = 0; y < GameBoard.verticalSize; y++) {
                    if (Main.selectedBoard.getBoardPiece(x, y)) {
                        spheres[x][y].setVisible(true);
                    } else {
                        spheres[x][y].setVisible(false);
                    }
                }
            }
        } catch (Exception e) {
            Main.logSevereAndExit(e);
        }
    }

    private void fillSelectors(){
        difficulties = new Vector<>();
        difficulties.add("Novice");
        difficulties.add("Normal");
        difficulties.add("Expert");
        difficulties.add("Master");
        difficulties.add("Insane");
        difficulties.add("Impossible");
        difficulties.add("Amazing");

        levelNumbers = new Vector<>(500);
        for (int i = 0; i < 500;i++) {
            levelNumbers.add(i, Integer.toString(i+1));
        }

        ObservableList<String> dItems = FXCollections.observableArrayList(difficulties);
        difficultySel.setItems(dItems);
        difficultySel.setValue(dItems.get(0));

        ObservableList<String> lItems = FXCollections.observableArrayList(levelNumbers);
        levelSel.setItems(lItems);
        levelSel.setValue(lItems.get(0));
    }

    private void loadLevels(){
        levels = new long[7][500];

        try {
            loadLevelsAux(0, "0_Novice.txt");
            loadLevelsAux(1, "1_Normal.txt");
            loadLevelsAux(2, "2_Expert.txt");
            loadLevelsAux(3, "3_Master.txt");
            loadLevelsAux(4, "4_Insane.txt");
            loadLevelsAux(5, "5_Impossible.txt");
            loadLevelsAux(6, "6_Amazing.txt");
        } catch (Exception e) {
            Main.logSevereAndExit("Problem while loading levels");
        }

    }

    private void loadLevelsAux(int arrayPos, String packName) throws Exception {
        InputStream file = Main.class.getResourceAsStream("originalLevels/" + packName);

        if (file == null) {
            throw new Exception("Problem while loading level");
        }

        BufferedReader reader = new BufferedReader(new InputStreamReader(file));
        String line;
        int readLines = 0;

        while ((line = reader.readLine()) != null) {
            levels[arrayPos][readLines] = Long.parseLong(line);
            readLines++;
        }

        file.close();
    }

    public void updateSelection(){
        selDif = difficultySel.getSelectionModel().getSelectedIndex();
        selLevel = levelSel.getSelectionModel().getSelectedIndex();
        updateBoard();
    }

    public void loadButtonHandler(){
        application1.gotoStartWindow();
    }
}
