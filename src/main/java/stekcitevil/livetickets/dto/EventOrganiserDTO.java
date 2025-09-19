package stekcitevil.livetickets.dto;

import java.time.LocalDate;

public record EventOrganiserDTO(
        String eventTitle, LocalDate eventDate,
        double revenue
) {
}
