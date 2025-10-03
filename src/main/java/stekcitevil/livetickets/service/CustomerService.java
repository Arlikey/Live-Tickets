package stekcitevil.livetickets.service;

import org.springframework.stereotype.Service;
import stekcitevil.livetickets.dto.*;
import stekcitevil.livetickets.model.Customer;
import stekcitevil.livetickets.repository.CustomerRepository;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public PersonalAccountDTO getCustomerByUserEmail(String email) {
        Customer customer = customerRepository.findByUserEmail(email).orElseThrow();
        List<FullTicketDTO> tickets = customer.getTickets().stream()
                .map(ticket -> new FullTicketDTO(
                        ticket.getId(),
                        ticket.getNumber(),
                        ticket.getPrice(),
                        new EventDTO(
                                ticket.getEvent().getId(),
                                ticket.getEvent().getName(),
                                ticket.getEvent().getStartDateTime().toLocalDate(),
                                ticket.getEvent().getStartDateTime().toLocalTime(),
                                ticket.getEvent().getEventVenue() != null
                                        ? new VenueDTO(
                                        ticket.getEvent().getEventVenue().getId(),
                                        ticket.getEvent().getEventVenue().getTitle(),
                                        ticket.getEvent().getEventVenue().getAddress()
                                )
                                        : null
                        )
                ))
                .toList();

        return new PersonalAccountDTO(customer.getFullName(), customer.getPhoneNumber(), tickets);
    }

}

