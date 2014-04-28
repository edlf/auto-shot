package gui;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Eduardo Fernandes
 */
public class MapSelectorController extends GridPane  implements Initializable {
    private GameBoard gameBoard;
    private ImageView[][] spheres;

    @FXML
    Pane mainPane;

    @FXML
    GridPane gameGrid;

    public void setApp(Main application) {
        Main application1 = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gameBoard = new GameBoard("level7500.map");
            setupBoard();
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
                    if (gameBoard.getBoardPiece(x, y)) {
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
}
