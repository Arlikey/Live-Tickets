package stekcitevil.livetickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stekcitevil.livetickets.model.Event;

import java.time.LocalDate;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByEventDateAndAvailableTicketsCountGreaterThan(LocalDate eventDate, Integer availableTicketsCount);

    List<Event> findByOrganiser(String organiser);

    List<Event> findByEventVenue(String eventVenue);

    @Query("SELECT e FROM Event e WHERE e.availableTicketsCount <= e.ticketsCount * 0.1")
    List<Event> findAlmostSoldOutEvents();

    @Query("SELECT e FROM Event e WHERE e.availableTicketsCount = 0 ORDER BY e.ticketsCount * e.ticketPrice DESC")
    List<Event> findSoldOutEventsOrderByRevenueDesc();
}
