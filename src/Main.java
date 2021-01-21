import java.io.IOException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        DB_connect db = new DB_connect();
//        User.init(db);
        try {
            // todo: thread aanmaken voor Server en voor Webserver zodat het tegelijk kan runnen.
            //new Server();
            WebServer webServer = new WebServer(db);
            webServer.addRequest("GET", "/");
            webServer.addRequest("GET", "/station");
            webServer.addRequest("POST", "/login");
            webServer.start();

        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }
}
