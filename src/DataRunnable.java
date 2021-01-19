import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;

public class DataRunnable implements Runnable {
    private Thread thread;
    private String name;
    private String data;
    private DB_connect db_connect;

    public DataRunnable(String name, String data, DB_connect db_connect) {
        this.name = name;
        this.data = data;
        this.db_connect = db_connect;
    }

    @Override
    public void run() {
        try {
            read();
        } catch (ParserConfigurationException | IOException | SAXException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void read() throws ParserConfigurationException, IOException, SAXException, SQLException {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();


        ByteArrayInputStream input = new ByteArrayInputStream(this.data.toString().getBytes("UTF-8"));

        Document doc = builder.parse(input);

        Element root = doc.getDocumentElement();

        NodeList nodeList = root.getElementsByTagName("MEASUREMENT");

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            Element element = (Element) node;

            Measurement measurement = new Measurement(element);
            measurement.create(db_connect);
        }

        thread.interrupt();
    }

    public void start() {
        System.out.println("Starting thread: " + this.name);
        if (this.thread == null) {
            thread = new Thread(this, this.name);
            thread.start();
        }
    }
}
