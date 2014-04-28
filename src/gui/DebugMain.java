package gui;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Eduardo Fernandes
 * <p/>
 * Entry Point
 */
class DebugMain {


    /*
     * Entry Point
     */
    public static void main(String[] args) {
        try {
            GameBoard temp = new GameBoard("level7500.map");

            boolean exit = false;

            Scanner sc = new Scanner(System.in);
            while (!exit) {
                System.out.println("DEBUG MENU:");
                System.out.println(" 0) Choose level");
                System.out.println(" 1) Print board");
                System.out.println(" 2) Print Available Moves");
                System.out.println(" 3) Check if board has been solved");
                System.out.println(" 4) Check if the game has been lost");
                System.out.println(" 5) Do move");
                System.out.println(" 6) Undo");
                System.out.println(" 7) Redo");
                System.out.println("10) Attempt DFS");
                System.out.println("99) Exit");

                int option = sc.nextInt();
                switch (option) {
                    default:
                        System.out.println("Invalid option chosen!");
                        break;

                    case 0:
                        System.out.print("Input wanted Level:");
                        String levelMapName = sc.next();
                        temp = new GameBoard(levelMapName);
                        break;

                    case 1:
                        System.out.println(temp.toString());
                        break;

                    case 2:
                        ArrayList<GameMove> moves = temp.getAvailableMoves();
                        for (int i = 0; i < moves.size(); i++) {
                            GameMove move = moves.get(i);
                            System.out.println(Integer.toString(i) + " ) " + move.toString());
                        }
                        break;

                    case 3:
                        if (temp.isBoardSolved()) {
                            System.out.println("The board has been solved.");
                        } else {
                            System.out.println("You've got work to do.");
                        }
                        break;

                    case 4:
                        if (temp.isBoardLost()) {
                            System.out.println("The game has been lost.");
                        } else {
                            System.out.println("You've can still go on.");
                        }
                        break;

                    case 5:
                        System.out.print("Type move number: ");
                        int moveNo = sc.nextInt();
                        if (moveNo >= 0 && moveNo <= temp.getAvailableMoves().size()) {
                            temp.doMove(temp.getAvailableMoves().get(moveNo), false);
                        } else {
                            System.out.println("Invalid move chosen!");
                        }

                        break;

                    case 6:
                        temp.undoMove();
                        break;

                    case 7:
                        temp.redoMove();
                        break;

                    case 8:
                        temp = new GameBoard("level7500.map");
                        break;

                    case 10:
                        SolverDFS tempSolver = new SolverDFS(temp);
                        tempSolver.searchSolution();
                        tempSolver.printSolutionStack();
                        tempSolver.printStatistics();
                        break;

                    case 99:
                        exit = true;
                        break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.toString());
            System.exit(-1);
        }
        System.exit(0);
    }
}
