package gui;

import org.junit.Test;

import static org.junit.Assert.*;

public class GameBoardTest {

    @Test
    public void testGetAvailableMoves() throws Exception {

    }

    @Test
    public void testDoMove() throws Exception {

    }

    @Test
    public void testUndoMove() throws Exception {

    }

    @Test
    public void testRedoMove() throws Exception {

    }

    @Test
    public void testGetBoardPiece() throws Exception {

    }

    @Test
    public void testGetNumberOfPiecesLeft() throws Exception {

    }

    @Test
    public void testIsBoardSolvedOnVictory() throws Exception {
        boolean testBoard[][] = new boolean[GameBoard.horizontalSize][GameBoard.verticalSize];
        testBoard[1][3] = true;
        testBoard[4][3] = true;

        GameBoard test = new GameBoard(testBoard);

        if (test.isBoardSolved()) {
            throw new Exception("The Game is not solved!");
        }

        GameMove testMove = new GameMove(1,3,'r');

        test.doMove(testMove, false);

        if (!test.isBoardSolved()) {
            throw new Exception("The Game has been won!");
        }
    }

    @Test
    public void testIsBoardSolvedOnLoss() throws Exception {
        boolean testBoard[][] = new boolean[GameBoard.horizontalSize][GameBoard.verticalSize];
        testBoard[1][2] = true;
        testBoard[4][3] = true;

        GameBoard test = new GameBoard(testBoard);

        if (test.isBoardSolved()) {
            throw new Exception("The Game has been lost!");
        }
    }

    @Test
    public void testIsBoardLostOnVictory() throws Exception {
        boolean testBoard[][] = new boolean[GameBoard.horizontalSize][GameBoard.verticalSize];
        testBoard[1][3] = true;
        testBoard[4][3] = true;

        GameBoard test = new GameBoard(testBoard);

        if (test.isBoardLost()) {
            throw new Exception("The Game is still playable!");
        }

        GameMove testMove = new GameMove(1,3,'r');

        test.doMove(testMove, false);

        if (test.isBoardLost()) {
            throw new Exception("The Game has been won!");
        }
    }

    @Test
    public void testIsBoardLostOnLoss() throws Exception{
        boolean testBoard[][] = new boolean[GameBoard.horizontalSize][GameBoard.verticalSize];
        testBoard[1][2] = true;
        testBoard[4][3] = true;

        GameBoard test = new GameBoard(testBoard);

        if (!test.isBoardLost()) {
            throw new Exception("The Game is NOT playable!");
        }
    }
}