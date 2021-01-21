import com.sun.net.httpserver.HttpServer;

import java.io.IOException;

public class WebServer {

    public WebServer()  throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost",8001),0);



    }



}
