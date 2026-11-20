
package main;

import web.WebServer;
import web.WebServer.WebServerException;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class Main {

    private static final Logger LOGGER =
            Logger.getLogger(Main.class.getName());

    private Main() {
        throw new IllegalStateException("Application entry point");
    }

    public static void main(String[] args) {

        try {
            WebServer.start();
        } catch (WebServerException e) {
            LOGGER.log(
                    Level.SEVERE,
                    "MusicWave application failed to start.",
                    e
            );
        }
    }
}
