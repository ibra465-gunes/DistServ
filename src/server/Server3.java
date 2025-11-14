package server;

import java.io.*;
import java.net.*;

import model.Abone;
import util.HealthLogger;
import core.PingThread;
import core.ClientHandler;
public class Server3 extends Thread{
    private static final int PORT = 5003; // Server3's port
    static final HealthLogger log = new HealthLogger();
    static Abone localAbone = new Abone();
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("server.Server3 is running on port " + PORT);
            // Ping other servers
            new PingThread("localhost", 5001).start(); // Ping server.Server1
            new PingThread("localhost", 5002).start(); // Ping server.Server2
            while (true) {
                try {
                    new ClientHandler(serverSocket.accept(),"localhost",5001,5002,localAbone, 3).start();
                } catch (IOException e) {
                    log.writer("WARN", "Server3", "Bağlantı kabul edilirken hata: " + e.getMessage());
                }
            }
        }
    }
}

