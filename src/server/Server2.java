package server;

import java.io.*;
import java.net.*;
import core.ClientHandler;
import model.Abone;
import util.HealthLogger;
import core.PingThread;

public class Server2 extends Thread{
    private static final int PORT = 5002;
    public static final HealthLogger log = new HealthLogger();
    static Abone localAbone = new Abone();
    public static void main(String[] args) throws IOException {
        // Ping other servers
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("server.Server2 is running on port " + PORT);
            new PingThread("localhost", 5001).start(); // Ping server.Server1
            new PingThread("localhost", 5003).start(); // Ping server.Server3
            while (true) {
                try {
                    new ClientHandler(serverSocket.accept(),"localhost", 5001, 5003, localAbone, 2).start();
                } catch (IOException e) {
                    log.writer("WARN", "Server2", "Bağlantı kabul edilirken hata: " + e.getMessage());
                }
            }
        }
    }
}

