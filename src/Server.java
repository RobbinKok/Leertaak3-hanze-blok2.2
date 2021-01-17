import java.io.*;
import java.net.*;

public class Server {
    // The port variable is the same number as the port used by the Generator
    public static final int PORT = 7789;

    public static void main(String[] args) {
        Socket connection;
        String s;

        try {
            // Start a new server on the specified port
            ServerSocket server = new ServerSocket(PORT);
            // Displays a message saying the server has started (sanity check)
            System.out.println("New server started");

            boolean nextline = true;
            String input;
            while (true){
                // Accepts all incomming connections
                connection = server.accept();
                // Displays a message saying that there is a new connection (sanity check)
                System.out.println("New connection");
                // Reads the data that was send by the connection
                BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));

                while (nextline){
                    input = reader.readLine();
                    if(!input.equals(null)){
                        //checks if the input is not equal to the string
                        if(!input.equals("<?xml version=\"1.0\"?>")){
                            input = reader.readLine();
                            System.out.println(input);
                        }


                    }
                }
                nextline = false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
