
package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DatabaseHelper {

    private static final String URL = "jdbc:sqlite:musicapp.db";

    private static final Logger LOGGER =
            Logger.getLogger(DatabaseHelper.class.getName());

    private DatabaseHelper() {
        throw new IllegalStateException("Utility class");
    }

    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void initialize() {

        String usersTable = """
                CREATE TABLE IF NOT EXISTS users (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    username TEXT UNIQUE NOT NULL,
                    email TEXT UNIQUE NOT NULL,
                    password TEXT NOT NULL
                );
                """;

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(usersTable);
            LOGGER.info("Database ready.");

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,
                    "Failed to initialize database.", e);
        }
    }

    public static boolean registerUser(
            String username,
            String email,
            String password) {

        String sql = """
                INSERT INTO users(username, email, password)
                VALUES (?, ?, ?)
                """;

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, email);
            ps.setString(3, password);

            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,
                    "User registration failed.", e);
            return false;
        }
    }

    public static boolean validateUser(
            String username,
            String password) {

        String sql = """
                SELECT 1 FROM users
                WHERE username = ? AND password = ?
                """;

        try (Connection conn = connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (var result = ps.executeQuery()) {
                return result.next();
            }

        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,
                    "User validation failed.", e);
            return false;
        }
    }
}

