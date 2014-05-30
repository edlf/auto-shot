package gui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * A* Algorithm Solver
 * Eduardo Fernandes
 * Filipe Eiras
 */
public class SolverAStar extends Solver {
    private int numberOfMovesTried;
    private int numberOfBackTracks;

    SolverAStar(GameBoard input) throws Exception {
        super(input);
        numberOfMovesTried = 0;
        numberOfBackTracks = 0;
    }

    protected void initializeSolver() {
        moveStack = new Stack<>();
        hasRun = false;
        solutionFound = gameBoard.isBoardSolved();
    }

    protected int linesChoice(int i, ArrayList<GameMove> availableMoves) throws Exception {
        gameBoard.doMove(availableMoves.get(i), false);
        int lines = getVerticalLines();
        gameBoard.undoMove();
        gameBoard.removeExtra();
        return lines;
    }

    protected void orderChoices(ArrayList<GameMove> availableMoves, List<MoveValues> moves) throws Exception {
        for (int i = 0; i < availableMoves.size(); i++) {
            moves.add(new MoveValues(i, linesChoice(i, availableMoves)));
        }
        Collections.sort(moves);
    }

    public void searchSolution() throws Exception {
        initializeSolver();
        if (!solutionFound) {
            searchSolutionAux();
        }
        hasRun = true;
    }

    public int getVerticalLines() {
        int lines = 0;
        for (int i = 0; i < gameBoard.getSize().getKey(); i++) {
            for (int j = 0; j < gameBoard.getSize().getValue(); j++) {
                try {
                    if (gameBoard.getBoardPiece(i, j)) {
                        lines++;
                        break;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return lines;
    }

    private void searchSolutionAux() throws Exception {
        //ArrayList<GameMove> availableMoves = gameBoard.getAvailableMoves();
        List<MoveValues> moves = new ArrayList<>();
        orderChoices(gameBoard.getAvailableMoves(), moves);

        for (int i = 0; i < moves.size(); i++) {
            if (solutionFound) {
                return;
            }
            attemptMove(gameBoard.getAvailableMoves().get(moves.get(i).number));
            solutionFound = gameBoard.isBoardSolved();

            /* Stop recursion if the solution has been found */
            if (solutionFound) {
                return;
            }

            searchSolutionAux();
        }

        /* No more moves and no solution? backtrack */
        if (!solutionFound) {
            backtrack();
        }
    }

    private void backtrack() {
        gameBoard.undoMove();
        moveStack.pop();
        numberOfBackTracks++;
    }

    private void attemptMove(GameMove in) throws Exception {
        moveStack.push(in);
        gameBoard.doMove(in, false);
        numberOfMovesTried++;
    }

    @Override
    public void printStatistics() {
        System.out.println("Number of backtracks: " + Integer.toString(numberOfBackTracks));
        System.out.println("Number of moves tried: " + Integer.toString(numberOfMovesTried));
    }

    public int getNumberOfBackTracks(){
        return numberOfBackTracks;
    }

    public int getNumberOfMovesTried(){
        return numberOfMovesTried;
    }

    @Override
    public String toString() {
        return "A* Algorithm";
    }

}
