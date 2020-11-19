/*
    Spencer Peace
    CSC 460-001
    Dr. Gary Newell
    Project 2
 */

import java.net.*;
import java.io.*;
import java.util.Scanner;

public class Client {
    private static Socket toServer;
    private static char[][] board;
    private static int row, col;
    private static DataInputStream instream;
    private static DataOutputStream outstream;
    private static PrintWriter out;
    private static BufferedReader in;

    public Client() {}

    public static void main(String[] args) {
        try {
            toServer = new Socket("localhost", 7788);
            instream = new DataInputStream(toServer.getInputStream());
            outstream = new DataOutputStream(toServer.getOutputStream());
            out = new PrintWriter(outstream, true);
            in = new BufferedReader(new InputStreamReader(instream));
            board = new char[3][3];
        } catch (IOException e) {
            System.out.println("Problem initializing Client: " + e);
            e.printStackTrace();
        }

        // Initialize board and row/col values
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
        row = col = -1;

        // Begin game
        ticTacToe(in, out);
    }

    public static void ticTacToe(BufferedReader in, PrintWriter out) {
        Scanner scn = new Scanner(System.in);
        // Boolean variable to yield control of game to server or client
        boolean ctrl = false;

        // Begin iteration over game
        for (boolean bool = false; !bool; ctrl = !ctrl) {
            // Give control to server first
            if (!ctrl) {
                String response = "";
                try {
                    response = in.readLine();
                } catch (IOException e) {
                    System.out.println("Unable to read server response: " + e);
                    e.printStackTrace();
                }
                // Server has chosen to move first
                if (!response.equals("NONE")) {
                    String[] splitResponse = response.split("\\s+");
                    // If server response is a win/tie/loss message
                    if (splitResponse.length > 3) {
                        row = Integer.parseInt(splitResponse[1]);
                        col = Integer.parseInt(splitResponse[2]);
                        // If tie or loss, still print server's X
                        if (!splitResponse[3].equals("WIN") && row != -1) {
                            board[row][col] = 'X';
                        }

                        // Break down server response, pick out game end keyword (win/loss/tie)
                        String endCondition = splitResponse[3];
                        if (endCondition.equals("TIE")) {
                            System.out.println("\nThe game was a TIE!");
                            break;
                        } else if (endCondition.equals("WIN")) {
                            System.out.println("\n\nCongratulations!!! You WON the game!");
                            break;
                        } else if (endCondition.equals("LOSS")) {
                            System.out.println("\nSORRY! You LOST the game!");
                            break;
                        }

                        bool = true;
                    // Server's response was a move message; update the board accordingly
                    } else {
                        row = Integer.parseInt(splitResponse[1]);
                        col = Integer.parseInt(splitResponse[2]);
                        board[row][col] = 'X';
                        System.out.println("\n\nSERVER'S MOVE: ");
                        printBoard();
                    }
                // Player moves first
                } else {
                    System.out.println("\nYOU MOVE FIRST");
                    printBoard();
                }
            // Pass control to client
            } else {
                while (true) {
                    do {
                        System.out.print("\nEnter Row: ");
                        row = scn.nextInt();
                        System.out.print("Enter Column: ");
                        col = scn.nextInt();
                    } while (row < 0);

                    if (row <= 2 && col <= 2 && col >= 0 && board[row][col] == ' ') {
                        board[row][col] = 'O';
                        System.out.println("\n\nYOUR MOVE: ");
                        printBoard();
                        out.println("MOVE " + row + " " + col);
                        break;
                    }
                }
            }
        }

        System.out.println("\n\nHere is the final game board: ");
        printBoard();
    }

    private static void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println(board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            // Print horizontal lines
            if (i != 2) {
                System.out.println("----------");
            }
        }
    }
}
