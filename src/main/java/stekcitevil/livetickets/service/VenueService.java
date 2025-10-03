package stekcitevil.livetickets.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stekcitevil.livetickets.dto.VenueDTO;
import stekcitevil.livetickets.model.Venue;
import stekcitevil.livetickets.repository.VenueRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VenueService {

    private final VenueRepository venueRepository;

    public VenueService(VenueRepository venueRepository) {
        this.venueRepository = venueRepository;
    }

    public List<VenueDTO> getAllVenues() {
        return venueRepository.findAll().stream()
                .map(v -> new VenueDTO(v.getId(), v.getTitle(), v.getAddress()))
                .collect(Collectors.toList());
    }

    public VenueDTO getVenueById(Long id) {
        Venue v = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        return new VenueDTO(v.getId(), v.getTitle(), v.getAddress());
    }

    @Transactional
    public VenueDTO createVenue(VenueDTO dto) {
        Venue v = new Venue();
        v.setTitle(dto.title());
        v.setAddress(dto.address());
        Venue saved = venueRepository.save(v);
        return new VenueDTO(saved.getId(), saved.getTitle(), saved.getAddress());
    }

    @Transactional
    public VenueDTO updateVenue(Long id, VenueDTO dto) {
        Venue v = venueRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venue not found"));
        v.setTitle(dto.title());
        v.setAddress(dto.address());
        return new VenueDTO(v.getId(), v.getTitle(), v.getAddress());
    }

    @Transactional
    public void deleteVenue(Long id) {
        venueRepository.deleteById(id);
    }
}
