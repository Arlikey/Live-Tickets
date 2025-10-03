package stekcitevil.livetickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import stekcitevil.livetickets.dto.EventCardDTO;
import stekcitevil.livetickets.dto.EventCreateDTO;
import stekcitevil.livetickets.dto.EventFullCreateDTO;
import stekcitevil.livetickets.dto.EventPageDTO;
import stekcitevil.livetickets.model.Event;
import stekcitevil.livetickets.service.EventService;


@RestController
@RequestMapping("/api/events")
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping("{id}")
    public EventPageDTO getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }


    @GetMapping
    public Page<EventCardDTO> getAllEvents(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "8") int size,
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "upcoming") String sort
    ) {
        return eventService.getAllEventCards(page, size, search, sort);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/simple")
    public Event createSimpleEvent(@RequestBody EventCreateDTO dto) {
        return eventService.createEvent(dto);
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/full")
    public Event createFullEvent(@RequestBody EventFullCreateDTO dto) {
        return eventService.createFullEvent(dto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEvent(eventId);
    }
}
