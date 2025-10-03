package stekcitevil.livetickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stekcitevil.livetickets.model.Ticket;
import stekcitevil.livetickets.model.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Integer countByEventIdAndStatus(Long eventId, TicketStatus status);
}
