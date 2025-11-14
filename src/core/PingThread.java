package core;

import java.io.IOException;
import java.net.Socket;

public class PingThread extends Thread {
    private String host;
    private int port;

    public PingThread(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void run() {
        try {
            while (true) {
                try (Socket socket = new Socket(host, port)) {
                    System.out.println("Pinged " + host + " on port " + port);
                } catch (IOException e) {
                    System.out.println("Ping to " + host + " on port " + port + " failed, retrying...");
                }

                try {
                    Thread.sleep(10000); // Wait for 10 seconds before retrying
                } catch (InterruptedException ie) {
                    System.out.println("Ping thread interrupted: " + ie.getMessage());
                    break; // Optional: exit the loop if the thread is interrupted
                }
            }

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}

