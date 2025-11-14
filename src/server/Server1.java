package server;

import java.io.*;
import java.net.*;

import model.Abone;
import util.HealthLogger;
import core.PingThread;
import core.ClientHandler;

public class Server1 extends Thread {
    private static final int PORT = 5001;// Server1's port
    public static final HealthLogger log = new HealthLogger();
    static Abone localAbone = new Abone();

    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("server.Server1 is running on port " + PORT);
            new PingThread("localhost", 5002).start(); // Ping server.Server2
            new PingThread("localhost", 5003).start(); // Ping server.Server3
            while (true) {
                try {
                    new ClientHandler(serverSocket.accept(), "localhost", 5002, 5003, localAbone, 1).start();
                } catch (IOException e) {
                    log.writer("WARN", "Server1", "Bağlantı kabul edilirken hata: " + e.getMessage());
                }
            }
        }
    }
}

