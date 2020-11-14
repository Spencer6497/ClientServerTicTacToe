/*
    Spencer Peace
    CSC 460-001
    Dr. Gary Newell
    Project 2
 */

import java.io.IOException;
import java.net.Socket;

public class Server_thread extends Thread{
    private Socket client;

    // Class constructor
    public Server_thread(Socket c) {
        this.client = c;
    }

    // Run method override
    public void run() {
        try {

        } catch (IOException e) {
            System.out.println("IOException on socket listen: " + e);
            e.printStackTrace();
        }
    }
}
