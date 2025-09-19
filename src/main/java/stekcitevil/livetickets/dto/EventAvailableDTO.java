package stekcitevil.livetickets.dto;

import java.time.LocalTime;

public record EventAvailableDTO(
        String eventTitle, LocalTime eventStartTime,
        int availableTicketsCount, String eventVenue
) {
}
