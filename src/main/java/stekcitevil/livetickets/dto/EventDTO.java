package stekcitevil.livetickets.dto;


import java.time.LocalDate;
import java.time.LocalTime;

public record EventDTO(
        Long id,
        String name,
        LocalDate eventDate,
        LocalTime eventStartTime,
        VenueDTO venue
) {
}