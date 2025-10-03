package stekcitevil.livetickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stekcitevil.livetickets.model.Venue;

import java.util.Optional;

public interface VenueRepository extends JpaRepository<Venue, Long> {
    public Optional<Venue> findByTitle(String title);
}
