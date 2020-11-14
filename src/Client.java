/*
    Spencer Peace
    CSC 460-001
    Dr. Gary Newell
    Project 2
 */

import java.net.*;
import java.io.*;

public class Client {
    public static void main(String[] args) {
        try {
            System.out.println("Starting new game of Tic-Tac-Toe...");
            System.out.println("Attempting to connect to server on port 7788...");
            Socket toServ = new Socket("localhost", 7788);
        } catch (IOException e) {
            // Placeholder for error handling
        }
    }
}
