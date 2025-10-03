package stekcitevil.livetickets.dto;

import java.util.List;

public record JwtResponse(
        String token,
        String type,
        Long id,
        String email,
        List<String> roles
) {
    public JwtResponse(String token, Long id, String email, List<String> roles) {
        this(token, "Bearer", id, email, roles);
    }
}
