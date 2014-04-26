package gui;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Eduardo Fernandes
 */
public class GameBoard {
    /* Constants */
    public static final int horizontalSize = 7;
    public static final int verticalSize = 7;
    public static final int arrayOffset = 1;
    public static final int minimumDistanceFromEdge = 2;

    private boolean[][] board;

    /* Save GameBoards + Moves done (GameBoards +1 offset) */
    private int currentMoveIndex;
    private Vector<boolean[][]> gameBoards;
    private Vector<GameMove> gameMoves;

    /* Empty board constructor */
    GameBoard() {
        board = new boolean[horizontalSize][verticalSize];
        clearBoard();
        initializeVectors();

        /* Add original board to vector */
        gameBoards.add(getBoardCopy());
    }

    GameBoard(boolean[][] in) {
        board = in;
        initializeVectors();

        /* Add original board to vector */
        gameBoards.add(getBoardCopy());
    }

    /* Level constructor */
    GameBoard(int boardId) throws Exception {
        board = new boolean[horizontalSize][verticalSize];
        clearBoard();
        initializeVectors();

        if (boardId < 1000 || boardId > 7500) {
            throw new Exception("Invalid boardId!");
        }

        int level = boardId % 1000;
        int pack = boardId / 1000;

        switch (pack) {
            case 1:
                if (level == 1) {
                    level1001();
                }
                break;

            case 7:
                if (level == 500) {
                    level7500();
                }
                break;

            default:
                throw new Exception("Level: " + Integer.toString(boardId) + " not found!");
        }

        /* Add original board to vector */
        gameBoards.add(getBoardCopy());
    }

    private void level1001() throws Exception {
        if (board != null) {
            board[1][3] = true;
            board[4][3] = true;
        } else {
            throw new Exception(new InvalidParameterException("board is null!"));
        }
    }

    private void level7500() throws Exception {
        if (board != null) {
            board[3][0] = true;
            board[4][0] = true;
            board[2][1] = true;
            board[3][1] = true;
            board[1][2] = true;
            board[3][2] = true;
            board[0][3] = true;
            board[1][3] = true;
            board[1][4] = true;
            board[5][4] = true;
            board[3][6] = true;
        } else {
            throw new Exception(new InvalidParameterException("board is null!"));
        }
    }

    private void initializeVectors() {
        currentMoveIndex = 0;
        gameBoards = new Vector<boolean[][]>();
        gameMoves = new Vector<GameMove>();
    }

    private void clearBoard() {
        for (int x = 0; x < horizontalSize; x++) {
            for (int y = 0; y < verticalSize; y++) {
                board[x][y] = false;
            }
        }
    }

    private boolean[][] getBoardCopy() {
        boolean[][] boardCopy = new boolean[horizontalSize][verticalSize];
        for (int x = 0; x < horizontalSize; x++) {
            System.arraycopy(board[x], 0, boardCopy[x], 0, verticalSize);
        }
        return boardCopy;
    }

    private ArrayList<GameMove> getAvailableMovesOnPosition(int x, int y) throws Exception {
        ArrayList<GameMove> moves = new ArrayList<GameMove>(0);

        /* Check if there is a sphere on the position */
        if (!board[x][y]) {
            return moves;
        }

        /* Verify left */
        if (x >= minimumDistanceFromEdge) {
            /* Check if there is a piece blocking our move */
            if (!board[x - 1][y]) {
                for (int i = x - 2; i >= 0; i--) {
                    if (board[i][y]) {
                        GameMove tempMove = new GameMove(x, y, GameMove.MoveLeft);
                        moves.add(tempMove);
                        break;
                    }
                }
            }
        }

        /* Verify right */
        if (x <= (horizontalSize - arrayOffset - minimumDistanceFromEdge)) {
            /* Check if there is a piece blocking our move */
            if (!board[x + 1][y]) {
                for (int i = x + 2; i < horizontalSize; i++) {
                    if (board[i][y]) {
                        GameMove tempMove = new GameMove(x, y, GameMove.MoveRight);
                        moves.add(tempMove);
                        break;
                    }
                }
            }
        }

        /* Verify up */
        if (y >= minimumDistanceFromEdge) {
            /* Check if there is a piece blocking our move */
            if (!board[x][y - 1]) {
                for (int i = y - 2; i >= 0; i--) {
                    if (board[x][i]) {
                        GameMove tempMove = new GameMove(x, y, GameMove.MoveUp);
                        moves.add(tempMove);
                        break;
                    }
                }
            }
        }

        /* Verify down */
        if (y <= (verticalSize - arrayOffset - minimumDistanceFromEdge)) {
            /* Check if there is a piece blocking our move */
            if (!board[x][y + 1]) {
                for (int i = y + 2; i < verticalSize; i++) {
                    if (board[x][i]) {
                        GameMove tempMove = new GameMove(x, y, GameMove.MoveDown);
                        moves.add(tempMove);
                        break;
                    }
                }
            }
        }

        return moves;
    }

    public ArrayList<GameMove> getAvailableMoves() {
        ArrayList<GameMove> moves = new ArrayList<GameMove>(0);

        /* Get all possible gameMoves from the board */
        for (int y = 0; y < verticalSize; y++) {
            for (int x = 0; x < horizontalSize; x++) {
                try {
                    moves.addAll(getAvailableMovesOnPosition(x, y));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return moves;
    }

    public void doMove(GameMove move, boolean redo) throws Exception {
        int x = move.getPieceX();
        int y = move.getPieceY();

        boolean canContinue = false;

        ArrayList<GameMove> movesAvailable = getAvailableMovesOnPosition(x, y);

        for (int i = 0; i < movesAvailable.size(); i++) {
            if (move.equals(movesAvailable.get(i))) {
                /* Move is valid */
                canContinue = true;
                break;
            }
        }

        if (!canContinue) {
            throw new Exception("Invalid Move, doing nothing.");
        }

        if (!redo) {
            /* Clear 'future' gameMoves if there are any */
            if (currentMoveIndex < gameMoves.size()) {
                for (int i = gameMoves.size(); i > currentMoveIndex; i--) {
                    gameMoves.remove(i - 1);
                }
            }
        }

        /* Clear 'future' gameBoards if there are any */
        if ((currentMoveIndex + 1) < gameBoards.size()) {
            for (int i = gameBoards.size(); i > currentMoveIndex + 1; i--) {
                gameBoards.remove(i - 1);
            }
        }

        currentMoveIndex++;
        if (!redo) {
            /* Save the current board and the move */
            gameMoves.add(move);
        }

        switch (move.getDirection()) {
            case GameMove.MoveUp:
                doMoveAuxUp(x, y);
                break;

            case GameMove.MoveDown:
                doMoveAuxDown(x, y);
                break;

            case GameMove.MoveLeft:
                doMoveAuxLeft(x, y);
                break;

            case GameMove.MoveRight:
                doMoveAuxRight(x, y);
                break;

            default:
                Main.logSevereAndExit("Invalid move on doMove()");
        }

        /* Add new board to vector */
        gameBoards.add(getBoardCopy());
    }

    private void doMoveAuxUp(int x, int y) {
        /* Check if we are propagating a collision */
        if (y > 0 && board[x][y - 1]) {
            doMoveAuxUp(x, y - 1);
            return;
        }

        /* Move piece */
        for (int i = y; i >= 0; i--) {
            /* Piece has left the board */
            if (i == 0) {
                board[x][y] = false;
                return;
            }

            if (board[x][i - 1]) {
                board[x][i] = true;
                board[x][y] = false;
                doMoveAuxUp(x, i - 1);
                return;
            }
        }
    }

    private void doMoveAuxDown(int x, int y) {
        /* Check if we are propagating a collision */
        if (y < (verticalSize - arrayOffset - 1) && board[x][y + 1]) {
            doMoveAuxDown(x, y + 1);
            return;
        }

        /* Move piece */
        for (int i = y; i < verticalSize; i++) {
            /* Piece has left the board */
            if (i == verticalSize - arrayOffset) {
                board[x][y] = false;
                return;
            }

            if (board[x][i + 1]) {
                board[x][i] = true;
                board[x][y] = false;
                doMoveAuxDown(x, i + 1);
                return;
            }
        }
    }

    private void doMoveAuxLeft(int x, int y) {
        /* Check if we are propagating a collision */
        if (x > 0 && board[x - 1][y]) {
            doMoveAuxLeft(x - 1, y);
            return;
        }

        /* Move piece */
        for (int i = x; i >= 0; i--) {
            /* Piece has left the board */
            if (i == 0) {
                board[x][y] = false;
                return;
            }

            if (board[i - 1][y]) {
                board[i][y] = true;
                board[x][y] = false;
                doMoveAuxLeft(i - 1, y);
                return;
            }
        }
    }

    private void doMoveAuxRight(int x, int y) {
        /* Check if we are propagating a collision */
        if (x < (horizontalSize - arrayOffset - 1) && board[x + 1][y]) {
            doMoveAuxRight(x + 1, y);
            return;
        }

        /* Move piece */
        for (int i = x; i < horizontalSize; i++) {
            /* Piece has left the board */
            if (i == horizontalSize - arrayOffset) {
                board[x][y] = false;
                return;
            }

            /* We have a collision */
            if (board[i + 1][y]) {
                board[i][y] = true;
                board[x][y] = false;
                doMoveAuxRight(i + 1, y);
                return;
            }
        }
    }

    public void undoMove() {
        if (currentMoveIndex > 0 && gameMoves.size() > 0) {
            restoreBoard(gameBoards.get(currentMoveIndex - 1));
            currentMoveIndex--;
        }
    }

    public void redoMove() {
        /* Save GameBoards + Moves done (GameBoards +1 offset) */
        if (currentMoveIndex < gameMoves.size()) {
            try {
                doMove(gameMoves.get(currentMoveIndex), true);
            } catch (Exception e) {
                Main.logSevereAndExit(e);
            }
        }
    }

    private void restoreBoard(boolean[][] restore) {
        for (int x = 0; x < horizontalSize; x++) {
            System.arraycopy(restore[x], 0, board[x], 0, verticalSize);
        }
    }

    public boolean[][] getGameBoard() {
        return board;
    }

    public boolean getBoardPiece(int x, int y) throws Exception {
        if ((x >= 0 && x < horizontalSize) && (y >= 0 && y < verticalSize)) {
            return board[x][y];
        }

        throw new Exception("Invalid location!");
    }

    public int getNumberOfPiecesLeft() {
        int numberOfPieces = 0;

        for (int y = 0; y < verticalSize; y++) {
            for (int x = 0; x < horizontalSize; x++) {
                if (board[x][y]) {
                    numberOfPieces++;
                }
            }
        }

        return numberOfPieces;
    }

    public boolean isBoardSolved() {
        return getNumberOfPiecesLeft() == 1;
    }

    public boolean isBoardLost() {
        ArrayList<GameMove> temp = null;
        try {
            temp = getAvailableMoves();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (temp.size() > 0) {
            return false;
        } else {
            return !isBoardSolved();
        }
    }

    @Override
    public String toString(){
        String output = "";
        for (int y = 0; y < verticalSize; y++) {
            for (int x = 0; x < horizontalSize; x++) {
                if (board[x][y]) {
                    output+="O";
                } else {
                     output+=" ";
                }
            }
            output+="\n";
        }
        return output;
    }

    public int getNumberOfMovesMade(){
        return currentMoveIndex;
    }

    public int getNumberOfAvailableMoves(){
        return getAvailableMoves().size();
    }
}
