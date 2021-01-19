import java.io.*;
import java.net.*;


public class Server {
    // The port variable is the same number as the port used by the Generator
    public static final int PORT = 7789;

    Socket connection;

    ServerSocket serverSocket;
    DB_connect db;


    public Server() throws IOException {
        serverSocket = new ServerSocket(PORT);
        this.db = new DB_connect();
        System.out.println("New server started");


        while (true) {
            connection = serverSocket.accept();
            System.out.println("New connection");
            Worker worker = new Worker("test", connection, db);
            worker.start();
        }
    }
}

