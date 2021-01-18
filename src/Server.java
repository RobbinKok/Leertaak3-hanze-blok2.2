import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
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


            while (true){
                // Accepts all incomming connections
                connection = server.accept();
//                connection.

                // Displays a message saying that there is a new connection (sanity check)
                System.out.println("New connection");

//                System.out.println(readAll(connection));

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {

                }


                // Reads the data that was send by the connecti//BufferedReader reader = new BufferedReader(new InputStreamReader((connection.getInputStream())));



                connection.close();
                System.err.println("Connection closed");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static String readAll(Socket socket) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        StringBuffer sb = new StringBuffer();
        String line;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
            System.out.println(line);
        }
        return sb.toString();
    }

}
