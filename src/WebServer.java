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
    private HttpServer server;

    public WebServer(DB_connect db) throws IOException {
        this.server = HttpServer.create(new InetSocketAddress(8500), 0);
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
        WebServerRequest webServerRequest = new WebServerRequest(method, url);
        context.setHandler(httpExchange -> {
            try {
                webServerRequest.handleRequest(httpExchange);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }


    public void start() {
        server.start();
    }

}
