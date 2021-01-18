import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerVersieTwee {

    public static final int PORT = 7789;

    Socket connection;

    ServerSocket serverSocket;

    public ServerVersieTwee() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("New server started");


        while (true) {
            connection = serverSocket.accept(); // TODO: make connection multi threaded for multiple connections
            System.out.println("New connection");

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            ReaderRunnable readerRunnable = new ReaderRunnable("reader", reader);
            readerRunnable.start();
//            while ((line = reader.readLine()) != null) {
//                System.out.println(line.equals("<?xml version=\"1.0\"?>"));
//            }

        }
    }
}


class ReaderRunnable implements Runnable {
    private Thread thread;
    private String name;
    private BufferedReader reader;


    public ReaderRunnable(String name, BufferedReader reader) {
        this.name = name;
        this.reader = reader;
        System.out.println("Creating thread: " + this.name);
    }


    @Override
    public void run() {
        String line = "";
        boolean running = true;
        while (running) {
            try {
                if ((line = reader.readLine()) == null) running = false;
            } catch (IOException e) {
                e.printStackTrace();
            }


            StringBuilder stringBuilder = new StringBuilder();

            if (line != null) {
                stringBuilder.append(line);
                if (line.equals("<?xml version=\"1.0\"?>")) {
                    //todo: start
                } else if (line.equals("</WEATHERDATA>")) {
                    // todo: close

                }
            }
        }
    }

    public void start() {
        System.out.println("Starting thread: " + this.name);
        if (this.thread == null) {
            thread = new Thread(this, this.name);
            thread.start();
        }
    }
}


