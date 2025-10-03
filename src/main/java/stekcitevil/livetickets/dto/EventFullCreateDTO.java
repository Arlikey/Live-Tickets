package stekcitevil.livetickets.dto;

import java.time.LocalDateTime;
import java.util.List;

public record EventFullCreateDTO(
        String name,
        LocalDateTime startDateTime,
        LocalDateTime endDateTime,
        String description,
        List<String> images,
        List<TicketPackDTO> ticketPacks,
        VenueDTO place
) {}