/*
    Spencer Peace
    CSC 460-001
    Dr. Gary Newell
    Project 2
 */

import java.net.*;
import java.io.*;

public class Dispatcher {
    // Declare static variable to hold ServerSocket
    static ServerSocket port;

    public static void main(String[] args) {
        // Declare Server_thread object
        Server_thread clientConn;

        // Declare client socket
        Socket client;

        // Enter try-catch loop
        try {
            // Instantiate server listener
            port = new ServerSocket(7788);

            // Infinite loop awaiting connections
            while(true) {
                // Listen for connections
                client = port.accept();
                // Assign newly-dispatched thread to previously defined server_thread
                clientConn = new Server_thread(client);
                // Start server_thread directly
                clientConn.start();
            }
        } catch (IOException e) {
            System.out.println("IOException on socket listen: " + e);
            e.printStackTrace();
        }
    }
}
