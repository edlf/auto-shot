package gui;

import java.util.Stack;

/**
 * Depth First Search
 * Eduardo Fernandes
 */
public class SolverDFS {
    private Stack<GameMove> moveStack;
    private int numberOfMovesTried;
    private int numberOfBackTracks;
    private boolean hasRun;
    private boolean solutionFound;

    GameBoard gameBoard;

    SolverDFS(GameBoard input) throws Exception{
        gameBoard = input;
        hasRun = false;
        solutionFound = false;
        numberOfMovesTried = 0;
        numberOfBackTracks = 0;
    }

    public void searchSolution() throws Exception{
        moveStack = new Stack<>();
        searchSolutionAux(0);
        hasRun = true;
    }

    private void searchSolutionAux(int level) throws Exception{
        solutionFound = gameBoard.isBoardSolved();

        if (gameBoard.getNumberOfAvailableMoves() > 0){

            for (int i = 0; i < gameBoard.getNumberOfAvailableMoves(); i++) {
                attemptMove(gameBoard.getAvailableMoves().get(i));
                searchSolutionAux(level+1);
            }

            /* No more moves to test on this node */
            backtrack();

        } else {
            if (!solutionFound) {
                /* No more moves on this node */
                backtrack();
            }
        }

    }

    private void backtrack(){
        gameBoard.undoMove();
        moveStack.pop();
    }

    private void attemptMove(GameMove in) throws Exception {
        moveStack.push(in);
        gameBoard.doMove(in, false);
        numberOfMovesTried++;
    }

    public Stack<GameMove> getSolution(){
        if (hasRun && solutionFound) {
            return moveStack;
        } else {
            return null;
        }
    }

    public void printSolutionStack(){
        if (hasRun && solutionFound) {
            System.out.println("Number of backtracks: " + Integer.toString(numberOfBackTracks));
            System.out.println("Number of moves tried: " + Integer.toString(numberOfMovesTried));
            for (int i=0; i < moveStack.size(); i++) {
                System.out.println(moveStack.get(i));
            }
        }

        if (hasRun && !solutionFound) {
            System.out.println("No solution has found");
        }

        if (!hasRun && !solutionFound) {
            System.out.println("You must run the algorithm first");
        }
    }
}
