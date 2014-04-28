package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Eduardo Fernandes
 */
public class MapSelectorController extends GridPane  implements Initializable {
    private ImageView[][] spheres;
    private long[][] levels;
    private int selDif = 0;
    private int selLevel = 0;

    @FXML
    Pane mainPane;

    @FXML
    GridPane gameGrid;

    @FXML
    ChoiceBox difficultySel;

    @FXML
    ChoiceBox levelSel;

    public void setApp(Main application) {
        Main application1 = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            loadLevels();
            Main.selectedBoard = new GameBoard(levels[selDif][selLevel]);
            setupBoard();
            fillSelectors();
            updateGUI();
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
/*        difficultySel;
        "Novice"
        "Normal"
        "Expert"
        "Master"
        "Insane"
        "Impossible"
        "Amazing"*/

        for (int i = 1; i <= 500;i++) {
            //levelSel.
        }
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
        InputStream file = GameBoard.class.getResourceAsStream("originalLevels/" + packName);

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

    public void loadButtonHandler(){
        // return to main menu
    }
}
