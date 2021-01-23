import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class WebServerRequest {

    private String method; // voor later gebruik
    private String url;

    public WebServerRequest(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public void handleRequest(HttpExchange httpExchange) throws IOException, SQLException {
        String response = this.result(httpExchange);
        httpExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    private String result(HttpExchange httpExchange) throws SQLException {
        String result = "";
        DB_connect db_connect = new DB_connect();
        if (httpExchange.getRequestMethod().equals("GET")) {
            switch (url) {
                // in deze switch kunnen er nieuwe urls worden toegevoegd
                // vergeet niet eerst add request te doen op de webserver
                // belangrijk: HIER NIET QUERYS NEER GOOIEN
                case "/stations":
                    result = new JSONConverter(Station.get(db_connect)).toString();
                    break;
                case "/measurements":
                    result = new JSONConverter(Measurement.get(db_connect)).toString();
                    break;
                case "/hum":
                    result = new JSONConverter(Measurement.getHum(db_connect)).toString();
                    break;
                case "/station-data":
                    result = new JSONConverter(Measurement.getWeatherStationData(db_connect)).toString();
                    break;
                default:
                    result = "404";
                    break;
            }
        } else if (httpExchange.getRequestMethod().equals("POST")) {
            switch (url) {
                case "/login":
                    // todo: create login (richard)
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
