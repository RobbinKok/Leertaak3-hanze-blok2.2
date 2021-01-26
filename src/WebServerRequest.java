import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.ArrayList;

public class WebServerRequest {

    private String method; // voor later gebruik
    private String url;

    private final String measurements = "SELECT * FROM `measurement`";
    private  final String windspeed = "SELECT DISTINCT(stations.name), AVG(measurement.wind_speed) FROM measurement JOIN stations ON stations.stn = measurement.station_stn WHERE stations.country LIKE '%CZECH REPUBLIC%' GROUP BY stations.name";
    private final String hum = "SELECT DISTINCT(stations.stn), stations.name, measurement.temperature, measurement.dew_point, 100 - (5 * (measurement.temperature - measurement.dew_point)) AS Humidity FROM measurement JOIN stations ON stations.stn = measurement.station_stn WHERE measurement.dew_point != 0 AND measurement.dew_point < measurement.temperature ORDER BY Humidity DESC LIMIT 10";
    private final String station_data = "SELECT DISTINCT stations.stn, stations.name, stations.longitude, stations.latitude, stations.elevation, measurement.station_stn, measurement.temperature, measurement.dew_point, measurement.wind_speed, measurement.wnddir FROM measurement JOIN stations ON stations.stn = measurement.station_stn WHERE stations.country=\"CZECH REPUBLIC\" OR stations.country=\"GERMANY\" OR stations.country=\"AUSTRIA\" OR stations.country=\"POLAND\" OR stations.country=\"SLOVAKIA\" GROUP BY stations.stn ";
    private final String stations = "SELECT * FROM `stations`";

    public WebServerRequest(String method, String url) {
        this.method = method;
        this.url = url;
    }

    public void handleRequest(HttpExchange httpExchange) throws IOException, SQLException {
        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
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
                    result = new JSONConverter(Measurement.getPage(db_connect, stations)).toString();
                    break;
                case "/measurements":
                    result = new JSONConverter(Measurement.getPage(db_connect, measurements)).toString();
                    break;
                case "/hum":
                    result = new JSONConverter(Measurement.getPage(db_connect, hum)).toString();
                    break;
                case "/station-data":
                    result = new JSONConverter(Measurement.getPage(db_connect, station_data)).toString();
                    break;
                case "/windspeed":
                    result = new JSONConverter(Measurement.getPage(db_connect, windspeed)).toString();
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
