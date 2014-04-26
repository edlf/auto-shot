package gui;

import java.util.Stack;

/**
 * Solver class, to be extended by a class that implements the algorithms
 * Eduardo Fernandes
 */
public class Solver {
    protected GameBoard gameBoard;
    protected Stack<GameMove> moveStack;
    protected boolean hasRun;
    protected boolean solutionFound;

    Solver(GameBoard input){
        gameBoard = input;
        hasRun = false;
        solutionFound = false;
    }

    public void searchSolution() throws Exception{

    }

    protected void initializeSolver(){
        moveStack = new Stack<>();
        hasRun = false;
        solutionFound = gameBoard.isBoardSolved();
    }

    public Stack<GameMove> getSolution(){
        if (hasRun && solutionFound) {
            return moveStack;
        } else {
            return null;
        }
    }

    public boolean getIsSolutionFound(){
        return solutionFound;
    }

    public void printSolutionStack(){
        if (hasRun && solutionFound) {
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

    public void printStatistics(){
    }
}
