package stekcitevil.livetickets.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stekcitevil.livetickets.dto.VenueDTO;
import stekcitevil.livetickets.service.VenueService;

import java.util.List;

@RestController
@RequestMapping("/api/venues")
public class VenueController {

    private final VenueService venueService;

    public VenueController(VenueService venueService) {
        this.venueService = venueService;
    }

    @GetMapping
    public List<VenueDTO> getAllVenues() {
        return venueService.getAllVenues();
    }

    @GetMapping("/{id}")
    public VenueDTO getVenue(@PathVariable Long id) {
        return venueService.getVenueById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public VenueDTO createVenue(@RequestBody VenueDTO dto) {
        return venueService.createVenue(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public VenueDTO updateVenue(@PathVariable Long id, @RequestBody VenueDTO dto) {
        return venueService.updateVenue(id, dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public void deleteVenue(@PathVariable Long id) {
        venueService.deleteVenue(id);
    }
}
