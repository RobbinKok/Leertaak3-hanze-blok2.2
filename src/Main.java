import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        DB_connect db = new DB_connect();
//        User.init(db);
        try {
            // todo: thread aanmaken voor Server en voor Webserver zodat het tegelijk kan runnen.
//            new Server();
            WebServer webServer = new WebServer();
            // Soort, post of het een get
            // url, wat komt er achter je localhost
            // content, wat moet er worden opgehaald, return eerst maar bullshit
            webServer.addRequest("GET", "/stations", WebServerContent.STATION_ALL);
            webServer.addRequest("POST", "/login", WebServerContent.USER_LOGIN);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
