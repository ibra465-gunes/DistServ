package core;

import model.Abone;
import util.HealthLogger;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

public class ServerHandler {
    private static HealthLogger log = new HealthLogger();
    public static void Send(String host, int port, int port1, Abone abone, int serverId) {
        try (Socket socket = new Socket(host, port);
             Socket socket1 = new Socket(host, port1);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(socket1.getOutputStream())
        ) {
            objectOutputStream.writeObject(abone);
            objectOutputStream.flush();
            objectOutputStream1.writeObject(abone);
            objectOutputStream1.flush();

        } catch (EOFException e) {
            String text = " Veri akışı erken kesildi. client.Client veri göndermeden bağlantıyı kapattı.";
            log.writer("INFO", "Server"+serverId, text);
        } catch (SocketException e) {
            String text = " Bağlantı hatası: client.Client bağlantısı yok veya bağlantı sıfırlandı.";
            log.writer("ERROR", "Server"+serverId, text);
        } catch (IOException e) {
            String text = " Genel IO hatası: " + e.getMessage();
            log.writer("WARN", "Server"+serverId, text);
        }
    }
}
