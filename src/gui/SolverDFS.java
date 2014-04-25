package gui;

import java.util.Stack;

/**
 * Depth First Search
 * Eduardo Fernandes
 */
public class SolverDFS {
    Stack<GameMove> moveStack;

    SolverDFS() throws Exception{
        searchSolution();
    }

    void searchSolution() throws Exception{
        GameBoard start = new GameBoard(7500);
        moveStack = new Stack<GameMove>();
        searchSolutionAux(start, 0);
    }

    void searchSolutionAux(GameBoard start, int level) throws Exception{
        if (start.getNumberOfAvailableMoves() > 0){
            System.out.println("Current level: " + Integer.toString(level));

            for (int i=0; i < start.getNumberOfAvailableMoves(); i++) {
                System.out.println("Trying solution: " + Integer.toString(i));

                moveStack.push(start.getAvailableMoves().get(i));

                start.doMove(start.getAvailableMoves().get(i), false);

                if(start.isBoardSolved()){
                    System.out.println("Solution found");

                    for (int j=0; j < moveStack.size(); j++) {
                        System.out.println(moveStack.get(j));
                    }

                    break;
                } else {
                    searchSolutionAux(start, level+1);
                }

            }

            moveStack.pop();
            start.undoMove();

        } else {

            if(start.isBoardSolved()){
                System.out.println("Solution found");
                for (int i=0; i < moveStack.size(); i++) {
                    System.out.println(moveStack.get(i));
                }

            } else {
                /* Backtrack */
                start.undoMove();
                moveStack.pop();
                return;
            }
        }
    }

}
