import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class WebServerRequest {

    private String method;
    private String url;

    public WebServerRequest(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public void handleRequest(HttpExchange httpExchange) throws IOException, SQLException {
        String response = this.result();
        httpExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String result() throws SQLException {
        String result = "";
        DB_connect db_connect = new DB_connect();
        if (this.method.equals("GET")) {
            switch (url) {
                case "/stations":
                    result =  new JSONConverter(Station.get(db_connect)).toString();
                    break;
                default:
                    result = "404";
                    break;
            }
        } else if (this.method.equals("POST")) {
            switch (url) {
                case "/login":
                    // todo: create login
                    result = "success";
                    break;
                default:
                    result = "404";
                    break;
            }
        } else {
            result = "404";
        }

        db_connect.close();
        return result;
    }
}
