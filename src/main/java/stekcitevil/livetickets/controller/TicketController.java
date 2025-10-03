package stekcitevil.livetickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import stekcitevil.livetickets.dto.MessageResponse;
import stekcitevil.livetickets.dto.TicketDTO;
import stekcitevil.livetickets.model.Customer;
import stekcitevil.livetickets.model.Ticket;
import stekcitevil.livetickets.model.User;
import stekcitevil.livetickets.repository.CustomerRepository;
import stekcitevil.livetickets.repository.UserRepository;
import stekcitevil.livetickets.service.TicketService;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;
    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Autowired
    public TicketController(TicketService ticketService, CustomerRepository customerRepository, UserRepository userRepository) {
        this.ticketService = ticketService;
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/buy/{eventId}")
    public ResponseEntity<?> buyTicket(@PathVariable Long eventId, Authentication authentication) {
        String userEmail = authentication.getName();

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));

        Customer customer = customerRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("Customer not found for user"));

        try {
            Ticket ticket = ticketService.buyTicket(eventId, customer);
            return ResponseEntity.ok(ticket);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
        }
    }

    @GetMapping
    public List<TicketDTO> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketDTO getTicketById(@PathVariable Long id) {
        return ticketService.getTicketById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public TicketDTO createTicket(@RequestBody TicketDTO ticketDTO) {
        return ticketService.createTicket(ticketDTO);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTicket(@PathVariable Long id) {
        ticketService.deleteTicket(id);
        return ResponseEntity.noContent().build();
    }
}
