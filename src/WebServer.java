import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import org.json.*;

import java.time.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.net.URI;

public class WebServer {

    public WebServer(DB_connect db) throws IOException {
        this.db = db;
        this.server = HttpServer.create(new InetSocketAddress(8500), 0);
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        String response = "Dikke webserver bouwen is niet FUUNN!!! Dikke lul";
        exchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    /**
     *
     * @param method
     * @param url
     * @throws IOException
     * @throws SQLException
     */
    public void addRequest(String method, String url) throws IOException, SQLException {
        HttpContext context = server.createContext(url);
        System.out.println("current url: " + url);
        context.setHandler(WebServer::handleRequest);

//        if("GET".equals(httpExchange.getRequestMethod())) {
//            url = handleGetRequest(httpExchange);
//        } else if("POST".equals(httpExchange)) {
//            url = handlePostRequest(httpExchange);
//        }
//        handleResponse(httpExchange, url);
    }

    public void start() {
        server.start();
    }

//    private String handleGetRequest(HttpExchange httpExchange) {
//        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
//    }
//
//    private String handlePostRequest(HttpExchange httpExchange) {
//        return "Moet nog passieren";
//    }

    public static String handleResponse(HttpExchange httpExchange) throws IOException, SQLException {
        URI requestURI = httpExchange.getRequestURI();
        String request = requestURI.getPath();
        return request;
//        switch (request)
//        {
//            case "station":
//                Station.get(this.db);
////                String response = Station.get(this.db).toString();
//                String response = "Stations shit";
//                httpExchange.sendResponseHeaders(200, response.getBytes().length);//response code and length
//                OutputStream os = httpExchange.getResponseBody();
//                os.write(response.getBytes());
//                os.close();
//                break;
//
//            case "login":
//
//                break;
//
//            case "measurements":
//                Measurement.get(this.db, "");
//                break;
//
//            case "measurements/top-ten-hum":
//                LocalDate date = LocalDate.now();
//                //Goede query voor het selecten van van temperaturen
//                //TODO: Dag limit doen
//                this.db.query("SELECT * FROM measurement ORDER BY temperature DESC LIMIT 10 WHERE date BETWEEN " + date + " AND " + "");
//                break;
//
//            default:
//
//        }
    }

    /*private static void printRequestInfo(HttpExchange exchange) {
        System.out.println("-- headers --");
        Headers requestHeaders = exchange.getRequestHeaders();
        requestHeaders.entrySet().forEach(System.out::println);

        System.out.println("-- principle --");
        HttpPrincipal principal = exchange.getPrincipal();
        System.out.println(principal);

        System.out.println("-- HTTP method --");
        String requestMethod = exchange.getRequestMethod();
        System.out.println(requestMethod);

        System.out.println("-- query --");
        URI requestURI = exchange.getRequestURI();
        String query = requestURI.getQuery();
        System.out.println(query);
    }*/
}
