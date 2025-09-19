package stekcitevil.livetickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stekcitevil.livetickets.dto.EventAvailableDTO;
import stekcitevil.livetickets.dto.EventOrganiserDTO;
import stekcitevil.livetickets.dto.EventRevenueDescDTO;
import stekcitevil.livetickets.dto.EventVenueDTO;
import stekcitevil.livetickets.model.Event;
import stekcitevil.livetickets.repository.EventRepository;
import stekcitevil.livetickets.validation.EventValidator;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final EventValidator eventValidator;

    @Autowired
    public EventService(EventRepository eventRepository, EventValidator eventValidator) {
        this.eventRepository = eventRepository;
        this.eventValidator = eventValidator;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Event createEvent(Event newEvent) {
        eventValidator.validateForCreate(newEvent);
        return eventRepository.save(newEvent);
    }

    public void deleteEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Event with id " + eventId + " does not exist");
        }
        eventRepository.deleteById(eventId);
    }

    @Transactional
    public Event updateEvent(Long eventId, Event newEvent) {
        var event = eventRepository.findById(eventId).orElseThrow(() -> new IllegalArgumentException("Event with id " + eventId + " does not exist"));

        eventValidator.validateForUpdate(newEvent);

        event.setEventTitle(newEvent.getEventTitle());
        event.setOrganiser(newEvent.getOrganiser());
        event.setEventDate(newEvent.getEventDate());
        event.setEventStartTime(newEvent.getEventStartTime());
        event.setEventDurationTime(newEvent.getEventDurationTime());
        event.setTicketsCount(newEvent.getTicketsCount());
        event.setAvailableTicketsCount(newEvent.getAvailableTicketsCount());
        event.setTicketPrice(newEvent.getTicketPrice());
        event.setEventVenue(newEvent.getEventVenue());

        return event;
    }

    public List<EventAvailableDTO> getEventsWithAvailableTickets(LocalDate date) {
        return eventRepository
                .findByEventDateAndAvailableTicketsCountGreaterThan(date, 0)
                .stream()
                .map(e -> new EventAvailableDTO(e.getEventTitle(), e.getEventStartTime(), e.getAvailableTicketsCount(), e.getEventVenue()))
                .collect(Collectors.toList());
    }

    public List<EventOrganiserDTO> getEventsByOrganiser(String organiser) {
        return eventRepository
                .findByOrganiser(organiser)
                .stream()
                .map(e -> new EventOrganiserDTO(e.getEventTitle(), e.getEventDate(), e.getTicketsCount() * e.getTicketPrice()))
                .collect(Collectors.toList());
    }

    public List<EventVenueDTO> getEventsByVenue(String venue) {
        return eventRepository
                .findByEventVenue(venue)
                .stream()
                .map(e -> new EventVenueDTO(e.getEventTitle(), e.getEventDate(), e.getOrganiser(), e.getEventDurationTime()))
                .collect(Collectors.toList());
    }

    public List<Event> getAlmostSoldOutEvents() {
        return eventRepository
                .findAlmostSoldOutEvents();
    }

    public List<EventRevenueDescDTO> getEventsByRevenueDesc(){
        return eventRepository
                .findSoldOutEventsOrderByRevenueDesc()
                .stream()
                .map(e -> new EventRevenueDescDTO(e.getEventTitle(), e.getOrganiser()))
                .collect(Collectors.toList());
    }
}
