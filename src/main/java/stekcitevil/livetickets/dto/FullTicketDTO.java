package stekcitevil.livetickets.dto;

import stekcitevil.livetickets.model.TicketStatus;

public record FullTicketDTO(
        Long id,
        String number,
        Double price,
        EventDTO event
) {
}