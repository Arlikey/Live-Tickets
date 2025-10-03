package stekcitevil.livetickets.model;

public enum ERole {
    ROLE_USER("user"),
    ROLE_MODERATOR("moderator"),
    ROLE_ADMIN("admin");
    private String role;
    ERole(String role) {
        this.role = role;
    }
    public String getRole() {
        return role;
    }
}
