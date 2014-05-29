package gui;

import java.util.Stack;

/**
 * A* Algorithm Solver
 * Eduardo Fernandes
 */
public class SolverAStar extends Solver {
    private int numberOfMovesTried;
    private int numberOfBackTracks;

    SolverAStar(GameBoard input) throws Exception{
        super(input);
        numberOfMovesTried = 0;
        numberOfBackTracks = 0;
    }

    protected void initializeSolver(){
        moveStack = new Stack<>();
        hasRun = false;
        solutionFound = gameBoard.isBoardSolved();
    }

    protected int linesChoice(int i) throws Exception{
        attemptMove(gameBoard.getAvailableMoves().get(i));
        numberOfMovesTried--;
        int lines = getHorizontalLines();
        backtrack();
        numberOfBackTracks--;
        return lines;
    }

    protected int bestChoice() throws Exception{
        int choice = 0;
        int lines = gameBoard.getSize().getKey()+1;
        for (int i = 0; i < gameBoard.getNumberOfAvailableMoves();i++){
            if (lines > linesChoice(i)){
                choice = i;
                lines = linesChoice(i);
            }
        }
        return choice;
    }

    public void searchSolution() throws Exception{
        initializeSolver();
        if (!solutionFound) {
            searchSolutionAux();
        }
        hasRun = true;
    }

    public int getHorizontalLines(){
        int lines = 0;
        for( int i = 0; i < gameBoard.getSize().getKey(); i++ ) {
            for (int j = 0; j < gameBoard.getSize().getValue(); j++){
                try {
                    if (gameBoard.getBoardPiece(i, j)){
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

    /* TODO Adapt to AStar */
    private void searchSolutionAux() throws Exception{
        /* Try available moves */
        int choice = 0;
        if(gameBoard.getNumberOfAvailableMoves()>0) {
            choice = bestChoice();
            attemptMove(gameBoard.getAvailableMoves().get(choice));

            solutionFound = gameBoard.isBoardSolved();
            if (solutionFound) return;
            searchSolutionAux();
        }
        if (!solutionFound){
            backtrack();

            for (int i = 0; i < gameBoard.getNumberOfAvailableMoves(); i++) {
                if(i != choice) {
                    attemptMove(gameBoard.getAvailableMoves().get(i));
                    solutionFound = gameBoard.isBoardSolved();

                    /* Stop recursion if the solution has been found */
                    if (solutionFound) {
                        return;
                    }

                    searchSolutionAux();
                }
            }

        /* No more moves and no solution? backtrack */
            if (!solutionFound) {
                backtrack();
            }
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

    @Override
    public void printStatistics(){
        System.out.println("Number of backtracks: " + Integer.toString(numberOfBackTracks));
        System.out.println("Number of moves tried: " + Integer.toString(numberOfMovesTried));
    }

    @Override
    public String toString(){
        return "A* Algorithm";
    }

}
