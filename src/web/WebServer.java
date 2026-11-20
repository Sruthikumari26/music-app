
package web;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WebServer {

    public static void start() throws Exception {

        HttpServer server = HttpServer.create(
            new InetSocketAddress("0.0.0.0", 8080), 0
        );

        server.createContext("/", exchange -> {

            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>MusicWave</title>
                </head>
                <body style="background:#17172b;color:white;
                    text-align:center;font-family:Arial">

                    <h1>Welcome to MusicWave</h1>
                    <h2>Application is running!</h2>

                </body>
                </html>
                """;

            byte[] response =
                html.getBytes(StandardCharsets.UTF_8);

            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8"
            );

            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        server.start();

        System.out.println("MusicWave started on port 8080");
    }
}
