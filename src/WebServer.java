import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import java.time.*;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.net.URI;
import java.util.ArrayList;

public class WebServer {

    private DB_connect db;
    private HttpServer server;

    public WebServer(DB_connect db) throws IOException {
        this.db = db;
        this.server = HttpServer.create(new InetSocketAddress(8500), 0);
    }

    private static void handleRequest(HttpExchange exchange) throws IOException {
        String response = "";
        try {
            response = handleResponse(exchange);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
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
        DB_connect db = new DB_connect();
        switch (request)
        {
            case "/station":
                return new JSONConverter(Station.get(db)).toString();
            case "/login":
                break;

            case "/measurements":
                Measurement.get(db, "");
                break;

            case "/measurements/top-ten-hum":
                break;

            default:

        }

        db.close();
        return request;
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
