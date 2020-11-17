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
            out = new PrintWriter(outstream);
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

        // Continue playing until gameOver
        while (!gameOver) {
            // Player's Move
            if (turn) {
                try {
                    out.println("NONE");
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
                printBoard();

                // Check if server win or tie
                if (checkWin() || counter == 9) {
                    gameOver = true;
                    if (checkWin()) {
                        //TODO: Send LOSS message w/ correct row/col vals
                    } else {
                        //TODO: Send TIE message w/ correct row/col vals
                    }
                // Move did not win game
                } else {
                    //TODO: Send MOVE message w/ correct row/col vals
                }
                // Change turn from server to client
                turn = !turn;
            }
        }
    }

    // Method to print game board
    private void printBoard() {
        for (int i = 0; i < 3; i++) {
            // Print horizontal lines
            if (i >= 1) {

            }
        }
    }

    // Method to check if current board contains win
    private boolean checkWin() {
        //TODO: use board matrix to determine if 3 consecutive symbols appear
        return false;
    }

    // Method to make a server move
    private void makeMove() {
        //TODO: continually generate random row/col until blank space found in board
    }


}
