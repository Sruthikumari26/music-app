package web;
import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class WebServer {

    public static void main(String[] args) throws Exception {

        HttpServer server = HttpServer.create(
            new InetSocketAddress(8080), 0
        );

        server.createContext("/", exchange -> {

            String html = """
                <!DOCTYPE html>
                <html>
                <head>
                    <title>MusicWave</title>
                    <style>
                        body {
                            background: #151525;
                            color: white;
                            font-family: Arial;
                            text-align: center;
                            padding-top: 100px;
                        }
                        input, button {
                            padding: 12px;
                            margin: 10px;
                        }
                    </style>
                </head>
                <body>
                    <h1>Welcome to MusicWave</h1>
                    <h2>Login</h2>

                    <form action="/login" method="post">
                        <input name="username"
                               placeholder="Username" required>
                        <br>
                        <input name="password"
                               type="password"
                               placeholder="Password" required>
                        <br>
                        <button type="submit">Login</button>
                    </form>
                </body>
                </html>
                """;

            byte[] response = html.getBytes(
                StandardCharsets.UTF_8
            );

            exchange.getResponseHeaders().set(
                "Content-Type", "text/html; charset=UTF-8"
            );

            exchange.sendResponseHeaders(200, response.length);

            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response);
            }
        });

        server.start();

        System.out.println(
            "MusicWave running on port 8080"
        );
    }
}
