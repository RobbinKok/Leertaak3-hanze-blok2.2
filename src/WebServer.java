import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class WebServer {

    public WebServer() throws IOExc
        HttpServer server = HttpServer.create(new InetSocketAddress(8500), 0);
        HttpContext context = server.createContext("/");
        context.setHandler(WebServer::handleRequest);
        server.start();
    }



}
