package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Stack;
import java.util.Vector;

/**
 * Eduardo Fernandes
 */
public class GameWindowAutoController extends GridPane implements Initializable {
    Main application1;

    private GameBoard gameBoard;
    private ImageView[][] spheres;
    private int selectedX = -1, selectedY = -1;
    private Vector<String> movesListVector;

    @FXML
    Pane mainPane;

    @FXML
    GridPane gameGrid;

    @FXML
    Label lblNumberOfMovesDone;
    @FXML
    Label lblNumberOfAvailableMoves;
    @FXML
    Label lblIsSolved;
    @FXML
    Label lblIsLost;

    @FXML
    ListView listViewMoves;

    EventHandler<KeyEvent> keyboardEventHandler;
    EventHandler<MouseEvent> mouseEventHandler;
    EventHandler<SwipeEvent> swipeEventHandler;

    public void setApp(Main application) {
        application1 = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            if (Main.selectedBoard != null){
                gameBoard = Main.selectedBoard;
            } else {
                gameBoard = new GameBoard("level7500.map");
            }
            setupBoard();
            updateGUI();
            createEventHandlers();
            gameGrid.setOnMouseClicked(mouseEventHandler);
        } catch (Exception e) {
            Main.logSevereAndExit(e);
        }
    }

    private void createEventHandlers() {
        mouseEventHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                for (int x = 0; x < GameBoard.horizontalSize; x++) {
                    for (int y = 0; y < GameBoard.verticalSize; y++) {
                        if (event.getTarget() == spheres[x][y] && event.getButton() == MouseButton.PRIMARY) {
                            selectPiece(x, y);
                            return;
                        }
                    }
                }
                clearSelection();
            }
        };

        keyboardEventHandler = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (isSelected()) {
                    if (event.getEventType() == KeyEvent.KEY_TYPED) {
                        if (event.getCode() == KeyCode.UP) {
                            doMove(selectedX, selectedY, GameMove.MoveUp);
                            return;
                        }

                        if (event.getCode() == KeyCode.DOWN) {
                            doMove(selectedX, selectedY, GameMove.MoveDown);
                            return;
                        }
                        if (event.getCode() == KeyCode.LEFT) {
                            doMove(selectedX, selectedY, GameMove.MoveLeft);
                            return;
                        }
                        if (event.getCode() == KeyCode.RIGHT) {
                            doMove(selectedX, selectedY, GameMove.MoveRight);
                            return;
                        }
                    }
                }
            }
        };

        swipeEventHandler = new EventHandler<SwipeEvent>() {
            @Override
            public void handle(SwipeEvent event) {
                for (int x = 0; x < GameBoard.horizontalSize; x++) {
                    for (int y = 0; y < GameBoard.verticalSize; y++) {
                        if (event.getTarget() == spheres[x][y]) {
                            if (event.getEventType() == SwipeEvent.SWIPE_UP) {
                                doMove(x, y, GameMove.MoveUp);
                                return;
                            }

                            if (event.getEventType() == SwipeEvent.SWIPE_DOWN) {
                                doMove(x, y, GameMove.MoveDown);
                                return;
                            }

                            if (event.getEventType() == SwipeEvent.SWIPE_LEFT) {
                                doMove(x, y, GameMove.MoveLeft);
                                return;
                            }

                            if (event.getEventType() == SwipeEvent.SWIPE_RIGHT) {
                                doMove(x, y, GameMove.MoveRight);
                                return;
                            }
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

    private void updateGUI() {
        updateBoard();
        updateStats();
    }

    private void updateBoard(){
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

    private void updateStats(){
        lblNumberOfMovesDone.setText("Number of moves: " + Integer.toString(gameBoard.getNumberOfMovesMade()));

        lblNumberOfAvailableMoves.setText("Number of available moves: " + Integer.toString(gameBoard.getNumberOfAvailableMoves()));

        if (gameBoard.isBoardSolved()) {
            lblIsSolved.setText("Is the board solved? Yes");
        } else {
            lblIsSolved.setText("Is the board solved? No");
        }

        if (gameBoard.isBoardLost()) {
            lblIsLost.setText("Is the game lost? Yes");
        } else {
            lblIsLost.setText("Is the game lost? No");
        }
    }

    private void clearSelection() {
        selectedX = -1;
        selectedY = -1;
        updateGUI();
    }

    private void selectPiece(int x, int y) {
        selectedX = x;
        selectedY = y;
        updateGUI();
    }

    private boolean isSelected() {
        if (selectedX == -1 && selectedY == -1) {
            return false;
        } else {
            return true;
        }
    }

    private void doMove(int x, int y, char direction) {
        if (!isSelected()) {
            System.out.println("No piece selected");
            return;
        }

        try {
            GameMove move = new GameMove(x, y, direction);
            gameBoard.doMove(move, false);
            clearSelection();
        } catch (Exception e) {
            System.out.println("Invalid Move");
        }
    }

    public void handleUpButtonAction() {
        doMove(selectedX, selectedY, GameMove.MoveUp);
    }

    public void handleDownButtonAction() {
        doMove(selectedX, selectedY, GameMove.MoveDown);
    }

    public void handleLeftButtonAction() {
        doMove(selectedX, selectedY, GameMove.MoveLeft);
    }

    public void handleRightButtonAction() {
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

    public void handleFindSolutionsButtonAction(){
        try {
            movesListVector = new Vector<>();
            SolverDFS tempSolver = new SolverDFS(gameBoard);
            tempSolver.searchSolution();
            if (tempSolver.getIsSolutionFound()){
                Stack<GameMove> solutions = tempSolver.getSolution();

                for (int i=0; i < solutions.size(); i++) {
                    movesListVector.add(solutions.get(i).toString());

                }

                ObservableList<String> dItems = FXCollections.observableArrayList(movesListVector);
                listViewMoves.setItems(dItems);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateGUI();
    }

    public void handleExitButtonAction() {
        application1.gotoStartWindow();
    }

}
