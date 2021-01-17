import java.net.*;
import java.io.*;

public class Client {
    private static DataInputStream input = null;
    private static DataOutputStream out = null;
    private Socket connection;
    public static final int PORT = 7789;

    public static void main(String[] args) {
        try {
            Socket client = new Socket("127.0.0.1", PORT);
            System.out.println("Connected");
            input = new DataInputStream(System.in);
            out = new DataOutputStream(client.getOutputStream());

           out.writeUTF("This is a test");
           System.out.println("Message sent");
           input.close();
           out.close();
           client.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
