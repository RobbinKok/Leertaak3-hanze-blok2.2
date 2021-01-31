import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        DB_connect db = new DB_connect();
        try {
            // todo: thread aanmaken voor Server en voor Webserver zodat het tegelijk kan runnen.
            WebServer webServer = new WebServer(db);
            webServer.addRequest("GET", "/");
            webServer.addRequest("GET", "/stations");
            webServer.addRequest("GET", "/measurements");
            webServer.addRequest("GET", "/hum");
            webServer.addRequest("GET", "/station-data");
            webServer.addRequest("GET", "/windspeed");
            webServer.addRequest("GET", "/week");
            webServer.addRequest("POST", "/login");
            webServer.addRequest("POST", "/download/humidity");
            webServer.addRequest("POST", "/download/windspeed");
            webServer.addRequest("POST", "/download/map");
            webServer.start();

            new Server(db);
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
