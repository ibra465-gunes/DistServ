package core;

import model.Abone;
import util.HealthLogger;
import model.CommandType;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.locks.ReentrantLock;

public class ClientHandler extends Thread {
    private final HealthLogger log = new HealthLogger();
    private final Socket clientSocket;
    private final String host;
    private final int port;
    private final int port1;
    Abone locaLAbone;
    private static final ReentrantLock lock = new ReentrantLock(true);
    int serverId;
    public ClientHandler(Socket socket, String host, int port, int port1, Abone localAbone, int serverId) {
        this.clientSocket = socket;
        this.host = host;
        this.port = port;
        this.port1 = port1;
        this.locaLAbone = localAbone;
        this.serverId = serverId;
    }

    public void run() {
        // Implement client handling logic here
        String message;

        lock.lock();
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            Object obj = objectInputStream.readObject();
            if (obj instanceof String) {
                message = (String) obj;
                if (message.equals("99 HATA")) {
                    System.out.println(message);
                }
                CommandType command;
                command = CommandType.valueOf(message);
                if ((command == CommandType.ABONOL || command == CommandType.ABONIPTAL || command == CommandType.GIRIS || command == CommandType.CIKIS)) {
                    String response = CommandProcessor.process(command,locaLAbone,host,port,port1,serverId);
                    objectOutputStream.writeObject(response);
                    log.writer("INFO", "Server" + serverId, "Komut alındı: "+currentThread().threadId()+" " + message);
                }
            } else if (obj instanceof Abone abone) {
                if (abone.getLastUpdatedEpochMiliSeconds() < locaLAbone.getLastUpdatedEpochMiliSeconds()) {
                    System.out.println("99 HATA");
                    objectOutputStream.writeObject("99 HATA");
                } else {
                    locaLAbone.setAbone(abone.getAbone());
                    locaLAbone.setGiris(abone.getGiris());
                    locaLAbone.setLastUpdatedEpochMiliSeconds(abone.getLastUpdatedEpochMiliSeconds());
                }
            } else {
                System.out.println("99 HATA");
                objectOutputStream.writeObject("99 HATA");
            }
            // Send a response back to the client
        } catch (EOFException e) {
            String text = " Veri akışı erken kesildi. client.Client veri göndermeden bağlantıyı kapattı.";
            log.writer("INFO", "Server"+serverId, text);
        } catch (SocketException e) {
            String text = " Bağlantı hatası: client.Client bağlantısı yok veya bağlantı sıfırlandı.";
            log.writer("ERROR", "Server"+serverId, text);
        } catch (ClassNotFoundException e) {
            String text = " Gelen nesnenin sınıfı bulunamadı. Server tarafında tanım eksik olabilir.";
            log.writer("ERROR", "Server"+serverId, text);
        } catch (IOException e) {
            String text = " Genel IO hatası: " + e.getMessage();
            log.writer("WARN", "Server"+serverId, text);
        } finally {
            lock.unlock();
        }
    }
}

