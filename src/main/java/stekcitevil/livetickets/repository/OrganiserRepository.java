package stekcitevil.livetickets.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import stekcitevil.livetickets.model.Organiser;

public interface OrganiserRepository extends JpaRepository<Organiser, Long> {
}
