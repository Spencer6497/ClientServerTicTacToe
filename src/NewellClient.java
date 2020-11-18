//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
// Test Client
//

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class NewellClient {
    private static Socket toserver;
    private static char[][] board;
    private static int row;
    private static int col;
    private static DataInputStream instream;
    private static DataOutputStream outstream;
    private static PrintWriter out;
    private static BufferedReader in;

    public NewellClient() {
    }

    public static void main(String[] var0) throws IOException {
        toserver = new Socket("localhost", 7788);
        instream = new DataInputStream(toserver.getInputStream());
        outstream = new DataOutputStream(toserver.getOutputStream());
        PrintWriter var1 = new PrintWriter(outstream, true);
        BufferedReader var2 = new BufferedReader(new InputStreamReader(instream));
        board = new char[3][3];

        // Initialize board values
        for(int var3 = 0; var3 <= 2; ++var3) {
            for(int var4 = 0; var4 <= 2; ++var4) {
                board[var3][var4] = ' ';
            }
        }

        row = -1;
        col = -1;
        playgame(var2, var1);
    }

    public static void playgame(BufferedReader var0, PrintWriter var1) throws IOException {
        // Scanner used for player input
        Scanner var2 = new Scanner(System.in);
        // control boolean, passes control between client and server_thread
        boolean var4 = false;

        for(boolean var5 = false; !var5; var4 = !var4) {
            // Give control to server_thread first
            if (!var4) {
                String var3 = var0.readLine();
                // If the server moves first
                if (!var3.equals("NONE")) {
                    String[] var6 = var3.split("\\s+");
                    // If the server response is anything but a move (i.e. a win/loss/tie message), print it and end game
                    if (var6.length > 3) {
                        row = Integer.parseInt(var6[1]);
                        col = Integer.parseInt(var6[2]);
                        if (!var6[3].equals("WIN") && row != -1) {
                            board[row][col] = 'X';
                        }

                        // Break down server response, pick out win/loss/tie keyword
                        String var7 = var6[3];
                        byte var8 = -1;
                        switch(var7.hashCode()) {
                            case 83056:
                                if (var7.equals("TIE")) {
                                    var8 = 1;
                                }
                                break;
                            case 85948:
                                if (var7.equals("WIN")) {
                                    var8 = 0;
                                }
                                break;
                            case 2342691:
                                if (var7.equals("LOSS")) {
                                    var8 = 2;
                                }
                        }

                        // Print message to user indicating win status
                        switch(var8) {
                            case 0:
                                System.out.println("\n\nCongratulations!!! You WON the game!");
                                break;
                            case 1:
                                System.out.println("\nThe game was a TIE!");
                                break;
                            case 2:
                                System.out.println("\nSORRY! You LOST the game!");
                        }

                        var5 = true;
                    // Otherwise, update the board for the server's move.
                    } else {
                        row = Integer.parseInt(var6[1]);
                        col = Integer.parseInt(var6[2]);
                        board[row][col] = 'X';
                    }
                // Player moves first
                } else {
                    System.out.println("\nYOU MOVE FIRST");
                }
            // Give control to client
            } else {
                while(true) {
                    do {
                        System.out.print("\nEnter Row : ");
                        row = var2.nextInt();
                        System.out.print("Enter Column : ");
                        col = var2.nextInt();
                    } while(row < 0);

                    if (row <= 2 && col <= 2 && col >= 0 && board[row][col] == ' ') {
                        board[row][col] = 'O';
                        var1.println("MOVE " + row + " " + col);
                        break;
                    }
                }
            }

            printboard();
        }

        System.out.println("\n\nHere is the final game board");
        printboard();
    }

    public static void printboard() {
        System.out.println("\n\nCLIENT PRINT");

        for(int var0 = 0; var0 <= 2; ++var0) {
            System.out.println(board[var0][0] + " | " + board[var0][1] + " | " + board[var0][2]);
            if (var0 != 2) {
                System.out.println("----------");
            }
        }

    }
}

