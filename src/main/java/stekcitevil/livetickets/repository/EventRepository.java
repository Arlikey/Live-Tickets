package stekcitevil.livetickets.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import stekcitevil.livetickets.model.Event;
import stekcitevil.livetickets.model.Venue;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    Page<Event> findByNameContainingIgnoreCase(String name, Pageable pageable);

    Page<Event> findByStartDateTimeAfter(LocalDateTime now, Pageable pageable);

    Page<Event> findByStartDateTimeAfterAndNameContainingIgnoreCase(LocalDateTime now, String name, Pageable pageable);
    Boolean existsByEventVenueAndStartDateTimeBetween(Venue eventVenue, LocalDateTime startDateTime, LocalDateTime endDateTime);
}
