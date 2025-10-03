package stekcitevil.livetickets.dto;


import stekcitevil.livetickets.model.Ticket;
import stekcitevil.livetickets.model.TicketStatus;

public record TicketDTO(
        Long id,
        String number,
        Double price,
        TicketStatus status
) {
    public static TicketDTO fromEntity(Ticket ticket) {
        return new TicketDTO(
                ticket.getId(),
                ticket.getNumber(),
                ticket.getPrice(),
                ticket.getStatus()
        );
    }
}
