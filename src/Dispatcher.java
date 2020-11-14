/*
    Spencer Peace
    CSC 460-001
    Dr. Gary Newell
    Project 2
 */

import java.net.*;
import java.io.*;

public class Dispatcher {
    public static void main(String[] args) {
        // Enter try-catch loop
        try {
            // Instantiate server listener
            ServerSocket serv = new ServerSocket(7788);
            // Declare client socket
            Socket client;

            // Infinite loop awaiting connections
            while(true) {
                // Listen for connections
                client = serv.accept();
                Server_thread clientConn = new Server_thread(client);
                // Start server_thread directly
                clientConn.start();
            }
        } catch (IOException e) {
            System.out.println("IOException on socket listen: " + e);
            e.printStackTrace();
        }
    }
}
