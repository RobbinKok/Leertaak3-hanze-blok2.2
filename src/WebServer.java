import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class WebServer {

    public WebServer() throws IOExc
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(WebServer::handleRequest);
        server.start();
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
     * @param content // dit wordt een enum
     */
    private void addRequest(HttpExchange httpExchange, String url, WebServerContent content) {

        if("GET".equals(httpExchange.getRequestMethod())) {
            url = handleGetRequest(httpExchange);
        } else if("POST".equals(httpExchange)) {
            url = handlePostRequest(httpExchange);
        }
        handleResponse(httpExchange, url);
    }
    private String handleGetRequest(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }

    private String handlePostRequest(HttpExchange httpExchange) {
        return httpExchange.getRequestURI().toString().split("\\?")[1].split("=")[1];
    }

    private void handleResponse(HttpExchange httpExchange, String request) throws IOException{
        DB_connect db = new DB_connect();
        switch (request)
        {  // Kan AP ook nog wat doen??? Hij ademt lucht kijk in dc

            case "station": // request == station

                db.query("SELECT * FROM ");
                break;
            case "login":

                break;
            default:

        }
    }

    private static void printRequestInfo(HttpExchange exchange) {
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
    }
}
