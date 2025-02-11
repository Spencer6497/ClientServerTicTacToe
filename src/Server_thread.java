/*
    Spencer Peace
    CSC 460-001
    Dr. Gary Newell
    Project 2
 */

import java.io.*;
import java.net.*;
import java.util.Random;

public class Server_thread extends Thread {
    private Socket toClient;
    private DataInputStream instream;
    private DataOutputStream outstream;
    private PrintWriter out;
    private BufferedReader in;
    private Random gen;
    private char[][] board;
    private int row, col;

    // Class constructor
    public Server_thread(Socket c) {
        try {
            toClient = c;
            gen = new Random();
            instream = new DataInputStream(toClient.getInputStream());
            outstream = new DataOutputStream(toClient.getOutputStream());
            out = new PrintWriter(outstream, true);
            in = new BufferedReader(new InputStreamReader(instream));
            board = new char[3][3];
            // Nested loop over board to initialize values as blanks
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    board[i][j] = ' ';
                }
            }
            row = col = -1;
        } catch (IOException e) {
            System.out.println("IOException on client socket: " + e);
            e.printStackTrace();
        }
    }

    // Run method override
    public void run() {
        // # moves made in game
        int counter = 0;
        // Response from client
        String response = new String("");
        boolean gameOver = false;
        boolean turn = false;

        // Flip coin to decide who moves first
        int rand = gen.nextInt();
        turn = rand % 2 == 0;

        // if it's the player's turn first, alert the player of this
        if (turn) {
            out.println("NONE");
        }

        // Continue playing until gameOver
        while (!gameOver) {
            // Player's Move
            if (turn) {
                try {
                    response = in.readLine();
                } catch (IOException e) {
                    System.out.println("Some sort of read error on socket in server thread.");
                }
                // Split response string into distinct words
                String[] data = response.split("\\s+");
                row = Integer.parseInt(data[1]);
                col = Integer.parseInt(data[2]);
                // Place player's O into specified matrix spot
                board[row][col] = 'O';
                System.out.println("\n\nPLAYER'S MOVE: ");
                printBoard();
                counter++;

                // Check if player win or tie
                if (checkWin() || counter == 9) {
                    gameOver = true;
                    if (checkWin()) {
                        out.println("MOVE -1 -1 WIN");
                    } else {
                        out.println("MOVE -1 -1 TIE");
                    }
                }
            // Server move
            } else {
                makeMove();
                counter++;
                board[row][col] = 'X';
                System.out.println("\n\nSERVER'S MOVE: ");
                printBoard();

                // Check if server win or tie
                if (checkWin() || counter == 9) {
                    gameOver = true;
                    if (checkWin()) {
                        out.println("MOVE " + row + " " + col + " LOSS");
                    } else {
                        out.println("MOVE " + row + " " + col + " TIE");
                    }
                // Move did not win game
                } else {
                    out.println("MOVE " + row + " " + col);
                }
            }
            // Change turn from server to client
            turn = !turn;
        }
    }

    // Method to print game board
    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println(board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            // Print horizontal lines
            if (i != 2) {
                System.out.println("----------");
            }
        }
    }

    // Method to check if current board contains win
    private boolean checkWin() {
        // Check for top left -> bottom right diagonal win
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != ' ') {
            return true;
        // Check for bottom left -> top right diagonal win
        } else if (board[2][0] == board[1][1] && board[1][1] == board[0][2] && board[2][0] != ' ') {
            return true;
        } else {
            for (int i = 0; i < 3; i++) {
                // Check for row-win
                if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != ' ') {
                    return true;
                    // Check for col-win
                } else if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != ' ') {
                    return true;
                }
            }
        }
        return false;
    }

    // Method to make a server move
    private void makeMove() {
        boolean notFound = true;
        // So long as a blank space is not found, keep generating random spaces
        while (notFound) {
            int rowNum = gen.nextInt(3);
            int colNum = gen.nextInt(3);
            // if the generated indices are blank in the matrix, update global variables and exit loop
            if (board[rowNum][colNum] == ' ') {
                notFound = false;
                row = rowNum;
                col = colNum;
            }
        }
    }


}
