package manager;

import model.User;
import org.mindrot.jbcrypt.BCrypt;
import util.DatabaseConnector;
import util.IdGenerator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {

    /**
     * Creates a new user, hashes their password, and saves them to the database.
     * @param username The new user's username.
     * @param password The plain-text password to be hashed.
     * @param role The user's role (e.g., "employee").
     */
    public void addUser(String username, String password, String role) {
        String id = IdGenerator.generateShortId();
        // Hash the password with a randomly generated salt.
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        String sql = "INSERT INTO users (id, username, password_hash, role) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, id);
            pstmt.setString(2, username);
            pstmt.setString(3, hashedPassword);
            pstmt.setString(4, role);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.err.println("Error adding user to database: " + e.getMessage());
        }
    }

    /**
     * Validates a user's login attempt.
     * @param username The username provided.
     * @param password The plain-text password to check.
     * @return The User object if authentication is successful, otherwise null.
     */
    public User validateUser(String username, String password) {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = null;

        try (Connection conn = DatabaseConnector.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHash = rs.getString("password_hash");
                
                // Use BCrypt's checkpw method to securely compare the plain-text password
                // with the stored hash.
                if (BCrypt.checkpw(password, storedHash)) {
                    String id = rs.getString("id");
                    String role = rs.getString("role");
                    user = new User(id, username, storedHash, role);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error validating user: " + e.getMessage());
        }
        return user;
    }
}