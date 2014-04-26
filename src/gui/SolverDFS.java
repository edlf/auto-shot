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
        searchSolutionAux();
        hasRun = true;
    }

    private void searchSolutionAux() throws Exception{
        solutionFound = gameBoard.isBoardSolved();

        /* Stop recursion if the solution has been found */
        if (solutionFound) {
            return;
        }

        /* Try available moves */
        for (int i = 0; i < gameBoard.getNumberOfAvailableMoves(); i++) {
            attemptMove(gameBoard.getAvailableMoves().get(i));
            solutionFound = gameBoard.isBoardSolved();
            searchSolutionAux();
        }

        /* No more moves and no solution? backtrack */
        if (!solutionFound) {
            backtrack();
        }
    }

    private void backtrack(){
        gameBoard.undoMove();
        moveStack.pop();
        numberOfBackTracks++;
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

    public boolean getSolutionFound(){
        return solutionFound;
    }
}
