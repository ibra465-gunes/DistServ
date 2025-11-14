package client;

import java.io.*;
import java.net.Socket;

public class Client {
    private static final String SERVER1_HOST = "localhost";
    private static final int SERVER1_PORT = 5001;
    private static final String SERVER2_HOST = "localhost";
    private static final int SERVER2_PORT = 5002;
    private static final String SERVER3_HOST = "localhost";
    private static final int SERVER3_PORT = 5003;

    public static void main(String[] args) {
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "ABONIPTAL");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "ABONIPTAL");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "ABONIPTAL");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "ABONIPTAL");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "ABONIPTAL");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "ABONIPTAL");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "ABONIPTAL");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "ABONOL");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "GIRIS");
        sendAndReceiveMessage(SERVER2_HOST, SERVER2_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER3_HOST, SERVER3_PORT, "CIKIS");
        sendAndReceiveMessage(SERVER1_HOST, SERVER1_PORT, "ABONIPTAL");
    }

    private static void sendAndReceiveMessage(String host, int port, String message) {
        try (Socket socket = new Socket(host, port);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            // Send a message to the server
            out.writeObject(message);
            // Receive the response from the server
            Object response = in.readObject();
            System.out.println("Response from server on port " + port + ": " + response);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error connecting to server on port " + port + ": " + e.getMessage());
        }
    }
}