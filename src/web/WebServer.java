

package web;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class WebServer {

    private static final Logger LOGGER =
            Logger.getLogger(WebServer.class.getName());

    private static final int PORT = 8080;

    private static final String HTML_RESPONSE = """
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

    private WebServer() {
        throw new IllegalStateException("Utility class");
    }

    public static void start() throws WebServerException {

        try {
            HttpServer server = HttpServer.create(
                    new InetSocketAddress("0.0.0.0", PORT), 0
            );

            server.createContext("/", exchange -> {

                byte[] response = HTML_RESPONSE.getBytes(
                        StandardCharsets.UTF_8
                );

                exchange.getResponseHeaders().set(
                        "Content-Type",
                        "text/html; charset=UTF-8"
                );

                exchange.sendResponseHeaders(
                        200, response.length
                );

                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response);
                } catch (IOException e) {
                    LOGGER.log(
                            Level.WARNING,
                            "Failed to send HTTP response.",
                            e
                    );
                }
            });

            server.start();

            LOGGER.info(
                    "MusicWave started on port " + PORT
            );

        } catch (IOException e) {
            throw new WebServerException(
                    "Unable to start MusicWave web server.", e
            );
        }
    }

    public static class WebServerException extends Exception {

        private static final long serialVersionUID = 1L;

        public WebServerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
