package gui;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.SwipeEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Eduardo Fernandes
 */
public class GameWindowController extends GridPane implements Initializable {
    private Main application;
    private GameBoard gameBoard;
    private ImageView[][] spheres;
    private int selectedX = -1, selectedY = -1;

    @FXML
    Pane mainPane;

    @FXML
    GridPane gameGrid;

    EventHandler<MouseEvent> mouseEventHandler;
    EventHandler<SwipeEvent> swipeEventHandler;

    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            gameBoard = new GameBoard(7500);
            setupBoard();
            displayBoard();
            createEventHandlers();
            gameGrid.setOnMouseClicked(mouseEventHandler);
        } catch (Exception e) {
            Main.logSevereAndExit(e);
        }
    }

    private void createEventHandlers(){
        mouseEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int x = 0; x < GameBoard.horizontalSize; x++) {
                    for (int y = 0; y < GameBoard.verticalSize; y++) {
                        if (event.getTarget() == spheres[x][y] && event.getButton() == MouseButton.PRIMARY){
                            selectPiece(x,y);
                            return;
                        }
                    }
                }
                clearSelection();
            }
        };

        swipeEventHandler = new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                for (int x = 0; x < GameBoard.horizontalSize; x++) {
                    for (int y = 0; y < GameBoard.verticalSize; y++) {
                        if (event.getTarget() == spheres[x][y] && event.getEventType() == SwipeEvent.SWIPE_UP){
                            doMove(x, y, GameMove.MoveUp);
                            return;
                        }

                        if (event.getTarget() == spheres[x][y] && event.getEventType() == SwipeEvent.SWIPE_DOWN){
                            doMove(x, y, GameMove.MoveDown);
                            return;
                        }

                        if (event.getTarget() == spheres[x][y] && event.getEventType() == SwipeEvent.SWIPE_LEFT){
                            doMove(x, y, GameMove.MoveLeft);
                            return;
                        }

                        if (event.getTarget() == spheres[x][y] && event.getEventType() == SwipeEvent.SWIPE_RIGHT){
                            doMove(x, y, GameMove.MoveRight);
                            return;
                        }
                    }
                }
            }
        };
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

    private void displayBoard() {
        try {
            for (int x = 0; x < GameBoard.horizontalSize; x++) {
                for (int y = 0; y < GameBoard.verticalSize; y++) {
                    if (gameBoard.getBoardPiece(x, y)) {
                        if (selectedX == x && selectedY == y) {
                            spheres[x][y].setImage(Main.selectedSphereImage);
                        } else {
                            spheres[x][y].setImage(Main.sphereImage);
                        }
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

    private void clearSelection(){
        selectedX = -1;
        selectedY = -1;
        displayBoard();
    }

    private void selectPiece(int x, int y) {
        selectedX = x;
        selectedY = y;
        displayBoard();
    }

    private void doMove(int x, int y, char direction) {
        if (x == -1 && y == -1) {
            System.out.println("No piece selected");
            return;
        }

        try {
            GameMove move = new GameMove(x , y, direction);
            gameBoard.doMove(move, false);
            clearSelection();
        } catch (Exception e) {
            System.out.println("Invalid Move");
        }
    }

    public void handleUpButtonAction(){
        doMove(selectedX, selectedY, GameMove.MoveUp);
    }

    public void handleDownButtonAction(){
        doMove(selectedX, selectedY, GameMove.MoveDown);
    }

    public void handleLeftButtonAction(){
        doMove(selectedX, selectedY, GameMove.MoveLeft);
    }

    public void handleRightButtonAction(){
        doMove(selectedX, selectedY, GameMove.MoveRight);
    }

    public void handleUndoButtonAction() {
        gameBoard.undoMove();
        clearSelection();
    }

    public void handleRedoButtonAction() {
        gameBoard.redoMove();
        clearSelection();
    }

    /*FIX THIS*/
    public void handleExitButtonAction() {
        System.exit(0);
    }

}
