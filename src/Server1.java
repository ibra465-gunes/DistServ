import java.io.*;
import java.net.*;


public class Server1 {
    private static final int PORT = 5001; // Server1's port

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("Server1 is running on port " + PORT);

        new PingThread("localhost", 5002).start(); // Ping Server2
        new PingThread("localhost", 5003).start(); // Ping Server3


        // Listen for client connections
        try {
            while (true) {
                // Ping other servers

                new ClientHandler(serverSocket.accept()).start();
            }
        } finally {
            serverSocket.close();
        }
    }

    private static class ServerHandler {
        public static void Send(String host, int port, int port1) {
            Abone abone = new Abone();
            try {
                Socket socket = new Socket(host, port);
                Socket socket1 = new Socket(host, port1);
                OutputStream out = socket.getOutputStream();
                OutputStream out1 = socket1.getOutputStream();
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
                ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(out1);
                if (abone.getLastUpdatedEpochMiliSeconds() < ClientHandler.get_EpochMiliSeconds()) {
                    abone.setLastUpdatedEpochMiliSeconds(ClientHandler.get_EpochMiliSeconds());
                    abone.setAbone(ClientHandler.isAbon());
                    abone.setGiris(ClientHandler.isGiris());
                    objectOutputStream.writeObject(abone);
                    objectOutputStream1.writeObject(abone);
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }
    }

    // Thread to handle client requests
    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private String host = "localhost";
        private int port = 5002;
        private int port1 = 5003;
        private static long _EpochMiliSeconds = 0;
        private static boolean abon;
        private static boolean giris;

        public static boolean isAbon() {
            return abon;
        }

        public static void setAbon(boolean abon) {
            ClientHandler.abon = abon;
        }

        public static boolean isGiris() {
            return giris;
        }

        public static void setGiris(boolean giris) {
            ClientHandler.giris = giris;
        }

        public static long get_EpochMiliSeconds() {
            return _EpochMiliSeconds;
        }

        public static void set_EpochMiliSeconds(long _EpochMiliSeconds) {
            ClientHandler._EpochMiliSeconds = _EpochMiliSeconds;
        }

        Object lock = new Object();

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            // Implement client handling logic here
            BufferedReader in = null;
            String message = null;
            PrintWriter out = null;
            ObjectInputStream objectInputStream = null;
            ObjectOutputStream objectOutputStream = null;
            synchronized (lock) {
                try {
                    objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                    objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                    Object obj = objectInputStream.readObject();
                    if (obj instanceof String) {
                        message = (String) obj;
                        if (message.equals("99 HATA")) {
                            System.out.println(message);
                        }
                        if ((message.equals("ABONOL") || message.equals("ABONIPTAL") || message.equals("GIRIS") || message.equals("CIKIS"))) {
                            if (message.equals("ABONOL") && isAbon()) {
                                objectOutputStream.writeObject("50 HATA");
                            } else if (message.equals("ABONOL")) {
                                setAbon(true);
                                ClientHandler.set_EpochMiliSeconds(System.currentTimeMillis());
                                ServerHandler.Send(host, port, port1);
                                objectOutputStream.writeObject("55 TAMM");
                            }
                            if (message.equals("ABONIPTAL") && isAbon()) {
                                setAbon(false);
                                ClientHandler.set_EpochMiliSeconds(System.currentTimeMillis());
                                ServerHandler.Send(host, port, port1);
                                objectOutputStream.writeObject("55 TAMM");
                            } else if (message.equals("ABONIPTAL")) {
                                objectOutputStream.writeObject("50 HATA");
                            }
                            if (message.equals("GIRIS") && isGiris()) {
                                objectOutputStream.writeObject("50 HATA");
                            } else if (message.equals("GIRIS")) {
                                ClientHandler.set_EpochMiliSeconds(System.currentTimeMillis());
                                setGiris(true);
                                ServerHandler.Send(host, port, port1);
                                objectOutputStream.writeObject("55 TAMM");

                            }
                            if (message.equals("CIKIS") && isGiris()) {
                                ClientHandler.set_EpochMiliSeconds(System.currentTimeMillis());
                                setGiris(false);
                                ServerHandler.Send(host, port, port1);
                                objectOutputStream.writeObject("55 TAMM");
                            } else if (message.equals("CIKIS")) {
                                objectOutputStream.writeObject("50 HATA");
                            }
                            System.out.println("Received message on Server1(" + currentThread().getId() + ") from client: " + message);
                        }
                    } else if (obj instanceof Abone) {
                        Abone abone = (Abone) obj;
                        if (abone.getLastUpdatedEpochMiliSeconds() < get_EpochMiliSeconds()) {
                            System.out.println("99 HATA");
                            objectOutputStream.writeObject("99 HATA");
                        } else {
                            setAbon(abone.getAbone());
                            setGiris(abone.getGiris());
                            set_EpochMiliSeconds(abone.getLastUpdatedEpochMiliSeconds());
                        }
                    } else {
                        System.out.println("99 HATA");
                        objectOutputStream.writeObject("99 HATA");
                    }
                    // Send a response back to the client
                } catch (EOFException e) {
                    System.out.println("Veri akışı erken kesildi. Client veri göndermeden bağlantıyı kapattı.");
                } catch (SocketException e) {
                    System.out.println("Bağlantı hatası: Client bağlantısı yok veya bağlantı sıfırlandı.");
                } catch (ClassNotFoundException e) {
                    System.out.println("Gelen nesnenin sınıfı bulunamadı. Server tarafında tanım eksik olabilir.");
                } catch (IOException e) {
                    System.out.println("Genel IO hatası: " + e.getMessage());
                }
            }
        }
    }

    // Thread to ping other servers
    private static class PingThread extends Thread {
        private String host;
        private int port;

        public PingThread(String host, int port) {
            this.host = host;
            this.port = port;
        }

        public void run() {
            try {
                while (true) {
                    try (Socket socket  = new Socket(host, port)) {
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
}

