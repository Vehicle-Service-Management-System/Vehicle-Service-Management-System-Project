package model;

public class User {
    private String id;
    private String username;
    private String hashedPassword; // IMPORTANT: Never store plain text passwords
    private String role;           // e.g., "manager", "employee"

    public User(String id, String username, String hashedPassword, String role) {
        this.id = id;
        this.username = username;
        this.hashedPassword = hashedPassword;
        this.role = role;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public String getRole() {
        return role;
    }

    // --- Setters ---
    public void setUsername(String username) {
        this.username = username;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public void setRole(String role) {
        this.role = role;
    }
}