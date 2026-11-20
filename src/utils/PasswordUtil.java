
package utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class PasswordUtil {

    private PasswordUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static String hash(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            byte[] hash = md.digest(
                    password.getBytes(StandardCharsets.UTF_8)
            );

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b & 0xff));
            }

            return sb.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new PasswordHashException(
                    "Unable to hash password.", e
            );
        }
    }

    public static class PasswordHashException
            extends IllegalStateException {

        private static final long serialVersionUID = 1L;

        public PasswordHashException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
