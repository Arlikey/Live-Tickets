package stekcitevil.livetickets.dto;

import java.time.Duration;
import java.time.LocalDate;

public record EventCardDTO(Long id, String image, String name, LocalDate eventDate, String eventVenue,
                           Duration duration, Integer tickets, String description) {
}
