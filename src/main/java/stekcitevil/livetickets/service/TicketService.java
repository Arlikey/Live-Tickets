package stekcitevil.livetickets.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stekcitevil.livetickets.dto.TicketDTO;
import stekcitevil.livetickets.model.Customer;
import stekcitevil.livetickets.model.Event;
import stekcitevil.livetickets.model.Ticket;
import stekcitevil.livetickets.model.TicketStatus;
import stekcitevil.livetickets.repository.EventRepository;
import stekcitevil.livetickets.repository.TicketRepository;

import java.util.List;

@Service
public class TicketService {

    private final EventRepository eventRepository;
    private final TicketRepository ticketRepository;

    @Autowired
    public TicketService(EventRepository eventRepository, TicketRepository ticketRepository) {
        this.eventRepository = eventRepository;
        this.ticketRepository = ticketRepository;
    }

    @Transactional
    public Ticket buyTicket(Long eventId, Customer customer) {
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        Ticket ticket = event.getTickets().stream()
                .filter(t -> t.getStatus() == TicketStatus.FREE)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No tickets available"));

        ticket.setStatus(TicketStatus.SOLD);
        ticket.setCustomer(customer);
        ticketRepository.save(ticket);

        return ticket;
    }

    public List<TicketDTO> getAllTickets() {
        return ticketRepository.findAll().stream()
                .map(TicketDTO::fromEntity)
                .toList();
    }

    public TicketDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found"));
        return TicketDTO.fromEntity(ticket);
    }

    public TicketDTO createTicket(TicketDTO dto) {
        Ticket ticket = new Ticket();
        ticket.setNumber(dto.number());
        ticket.setPrice(dto.price());
        ticket.setStatus(dto.status());
        return TicketDTO.fromEntity(ticketRepository.save(ticket));
    }

    public void deleteTicket(Long id) {
        ticketRepository.deleteById(id);
    }
}
