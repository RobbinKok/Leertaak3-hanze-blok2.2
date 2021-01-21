import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        try {
            // todo: thread aanmaken voor Server en voor Webserver zodat het tegelijk kan runnen.
//            new Server();
            new WebServer();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
