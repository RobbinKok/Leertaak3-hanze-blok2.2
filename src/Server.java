import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
    // The port variable is the same number as the port used by the Generator
    public static final int PORT = 7789;

    Socket connection;

    ServerSocket serverSocket;
    DB_connect db;
    ThreadPoolExecutor executor;


    public Server(DB_connect db) throws IOException {
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(800);
        serverSocket = new ServerSocket(PORT);
        this.db = db;
        System.out.println("New server started");


        while (true) {
            connection = serverSocket.accept();
            System.out.println("New connection");
            Worker worker = new Worker("test", connection, db);
            executor.execute(worker);
            worker.start();
        }
    }
}

