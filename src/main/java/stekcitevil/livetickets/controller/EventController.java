package stekcitevil.livetickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import stekcitevil.livetickets.dto.EventAvailableDTO;
import stekcitevil.livetickets.dto.EventOrganiserDTO;
import stekcitevil.livetickets.dto.EventRevenueDescDTO;
import stekcitevil.livetickets.dto.EventVenueDTO;
import stekcitevil.livetickets.model.Event;
import stekcitevil.livetickets.service.EventService;

import java.time.LocalDate;
import java.util.List;

@RestController
public class EventController {
    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @PostMapping
    public Event createEvent(@RequestBody Event newEvent) {
        return eventService.createEvent(newEvent);
    }

    @PutMapping("/{eventId}")
    public Event updateEvent(@PathVariable("eventId") Long eventId, @RequestBody Event newEvent) {
        return eventService.updateEvent(eventId, newEvent);
    }

    @DeleteMapping("/{eventId}")
    public void deleteEvent(@PathVariable("eventId") Long eventId) {
        eventService.deleteEvent(eventId);
    }

    @GetMapping("/events/date-available")
    public List<EventAvailableDTO> getAvailableEvents(@RequestParam LocalDate eventDate) {
        return eventService.getEventsWithAvailableTickets(eventDate);
    }

    @GetMapping("/events/organiser")
    public List<EventOrganiserDTO> getOrganiserEvents(@RequestParam String organiser) {
        return eventService.getEventsByOrganiser(organiser);
    }

    @GetMapping("/events/venue")
    public List<EventVenueDTO> getVenueEvents(@RequestParam String eventVenue) {
        return eventService.getEventsByVenue(eventVenue);
    }

    @GetMapping("/events/almost-sold-out")
    public List<Event> getAlmostSoldOutEvents() {
        return eventService.getAlmostSoldOutEvents();
    }

    @GetMapping("/events/revenue-desc")
    public List<EventRevenueDescDTO> getEventRevenueDesc() {
        return eventService.getEventsByRevenueDesc();
    }
}
