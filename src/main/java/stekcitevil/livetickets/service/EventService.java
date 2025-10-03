package stekcitevil.livetickets.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stekcitevil.livetickets.dto.*;
import stekcitevil.livetickets.model.Event;
import stekcitevil.livetickets.model.Ticket;
import stekcitevil.livetickets.model.TicketStatus;
import stekcitevil.livetickets.model.Venue;
import stekcitevil.livetickets.repository.EventRepository;
import stekcitevil.livetickets.repository.TicketRepository;
import stekcitevil.livetickets.repository.VenueRepository;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;
    private final VenueRepository venueRepository;

    @Autowired
    public EventService(EventRepository eventRepository, TicketRepository ticketRepository, VenueRepository venueRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
        this.venueRepository = venueRepository;
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    public Page<EventCardDTO> getAllEventCards(int page, int size, String search, String sort) {
        Pageable pageable = "desc".equalsIgnoreCase(sort)
                ? PageRequest.of(page, size, Sort.by("startDateTime").descending())
                : PageRequest.of(page, size, Sort.by("startDateTime").ascending());

        LocalDateTime now = LocalDateTime.now();

        Page<Event> events;
        if (search != null && !search.isBlank()) {
            events = eventRepository.findByStartDateTimeAfterAndNameContainingIgnoreCase(now, search, pageable);
        } else {
            events = eventRepository.findByStartDateTimeAfter(now, pageable);
        }

        return events.map(e -> new EventCardDTO(
                e.getId(),
                e.getImages().isEmpty() ? null : e.getImages().get(0),
                e.getName(),
                e.getStartDateTime().toLocalDate(),
                e.getEventVenue().getAddress(),
                Duration.between(e.getStartDateTime(), e.getEndDateTime()),
                ticketRepository.countByEventIdAndStatus(e.getId(), TicketStatus.FREE),
                e.getDescription()
        ));

    }

    public EventPageDTO getEventById(Long eventId) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new EntityNotFoundException("Event not found with id: " + eventId));

        BigDecimal price = event.getTickets().isEmpty()
                ? BigDecimal.ZERO
                : BigDecimal.valueOf(event.getTickets().get(0).getPrice());

        return new EventPageDTO(
                event.getId(),
                event.getImages().isEmpty() ? null : event.getImages().get(0),
                event.getName(),
                event.getStartDateTime().toLocalDate(),
                event.getEventVenue().getAddress(),
                event.getEventVenue().getTitle(),
                event.getStartDateTime().toLocalTime(),
                event.getEndDateTime().toLocalTime(),
                ticketRepository.countByEventIdAndStatus(event.getId(), TicketStatus.FREE),
                event.getDescription(),
                price
        );
    }


    public Event createEvent(EventCreateDTO dto) {
        Venue venue = venueRepository.findById(dto.venueId())
                .orElseThrow(() -> new EntityNotFoundException("Venue not found with id: " + dto.venueId()));

        Event event = initEvent(venue, dto.startDateTime(), dto.endDateTime(), dto.name(), dto.description(), dto.images());
        return eventRepository.save(event);
    }

    @Transactional
    public Event createFullEvent(EventFullCreateDTO dto) {
        Venue venue = venueRepository.findByTitle(dto.place().title())
                .orElseGet(() -> {
                    Venue v = new Venue(dto.place().title(), dto.place().address());
                    return venueRepository.save(v);
                });

        Event savedEvent = eventRepository.save(initEvent(venue, dto.startDateTime(), dto.endDateTime(), dto.name(), dto.description(), dto.images()));

        if (dto.ticketPacks() != null) {
            for (TicketPackDTO pack : dto.ticketPacks()) {
                for (int i = 0; i < pack.count(); i++) {
                    var ticket = new Ticket();
                    ticket.setEvent(savedEvent);
                    ticket.setPrice(pack.cost().doubleValue());
                    ticket.setNumber("TICKET-" + System.currentTimeMillis() + "-" + i);
                    ticket.setStatus(TicketStatus.FREE);
                    ticketRepository.save(ticket);
                }
            }
        }

        return savedEvent;
    }

    private Event initEvent(Venue venue,
                            LocalDateTime start,
                            LocalDateTime end,
                            String name,
                            String description,
                            List<String> images) {
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        boolean exists = eventRepository.existsByEventVenueAndStartDateTimeBetween(
                venue,
                start.toLocalDate().atStartOfDay(),
                start.toLocalDate().atTime(23, 59, 59)
        );
        if (exists) {
            throw new IllegalArgumentException("Venue is already booked for this date");
        }

        Event event = new Event();
        event.setName(name);
        event.setStartDateTime(start);
        event.setEndDateTime(end);
        event.setDescription(description);
        event.setImages(images);
        event.setEventVenue(venue);

        return event;
    }


    public void deleteEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Event with id " + eventId + " does not exist");
        }
        eventRepository.deleteById(eventId);
    }
}
