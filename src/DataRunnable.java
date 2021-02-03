import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DataRunnable implements Runnable {
    private Thread thread;
    private String name;
    private String data;
    private DB_connect db_connect;
    private int number = 0;

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
            String s = "SELECT temperature from measurement where station_stn = " + measurement.station_stn + " LIMIT 30";
            db_connect.query(s);
            ArrayList<Double> metingen =  new ArrayList<>();

            ResultSet rs = db_connect.getResult();
            while (rs.next()) {
                metingen.add(rs.getDouble("temperature"));
            }


            //System.out.println("penis " + metingen);

            if (measurement.temperature <= getAverage(metingen) - 3)
            {
                measurement.temperature = getAverage(metingen) - 1.5;
            }
            else if (measurement.temperature >= getAverage(metingen) + 3){
                measurement.temperature = getAverage(metingen) + 1.5;
            }

            //System.out.println(metingen);

            if (measurement.dew_point <= measurement.temperature) { // check if the dew point is valid
                measurement.create(db_connect);

            }
        }
        thread.interrupt();
    }

    private static double getAverage(ArrayList<Double> array) {
        int counter = 0;
        double sum = 0;
        for (double d : array) {
            sum = sum + d;
            counter++;
        }

        return sum / counter;
    }

        public void start() {
        System.out.println("Starting thread: " + this.name);
        if (this.thread == null) {
            thread = new Thread(this, this.name);
            thread.start();
        }
    }
}
