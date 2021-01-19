import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Worker implements Runnable {
    private Thread thread;
    private String name;
    private Socket connection;
    private boolean running = true;
    private DB_connect db;


    public Worker(String name, Socket connection, DB_connect db) {
        this.name = name;
        this.connection = connection;
        this.db = db;
    }

    @Override
    public void run() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String line = "";
        StringBuilder stringBuilder = new StringBuilder();

        while (this.running) {
            try {
                if ((line = reader.readLine()) == null) running = false;
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (line != null) {
                if (line.equals("<?xml version=\"1.0\"?>")) {
                    stringBuilder = new StringBuilder();
                }
                stringBuilder.append(line);


                if (line.equals("</WEATHERDATA>")) {
                    DataRunnable dataRunnable = new DataRunnable("biem", stringBuilder.toString(), db);
                    dataRunnable.start();
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
