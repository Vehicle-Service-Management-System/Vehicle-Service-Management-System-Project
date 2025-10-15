package model;

public class Mechanic {
    private String id;
    private String name;
    private boolean isAvailable;

    public Mechanic(String id, String name, boolean isAvailable) {
        this.id = id;
        this.name = name;
        this.isAvailable = isAvailable;
    }

    // --- Getters ---
    public String getId() { return id; }
    public String getName() { return name; }
    public boolean isAvailable() { return isAvailable; }

    // --- Setters ---
    public void setName(String name) { this.name = name; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return "Mechanic [ID=" + id + ", Name=" + name + ", Available=" + isAvailable + "]";
    }
}