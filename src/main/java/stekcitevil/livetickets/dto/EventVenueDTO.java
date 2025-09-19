package stekcitevil.livetickets.dto;

import java.time.Duration;
import java.time.LocalDate;

public record EventVenueDTO(
        String eventTitle, LocalDate eventDate,
        String organiser, Duration eventDurationTime
) {
}
