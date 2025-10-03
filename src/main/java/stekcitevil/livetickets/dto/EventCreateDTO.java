package stekcitevil.livetickets.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventCreateDTO(
        String name,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String description,
        List<String> images,
        Long venueId
) {}