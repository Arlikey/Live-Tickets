package stekcitevil.livetickets.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import stekcitevil.livetickets.dto.PersonalAccountDTO;
import stekcitevil.livetickets.model.Customer;
import stekcitevil.livetickets.model.User;
import stekcitevil.livetickets.repository.CustomerRepository;
import stekcitevil.livetickets.repository.UserRepository;
import stekcitevil.livetickets.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/me")
    public ResponseEntity<PersonalAccountDTO> getCurrentCustomer(@AuthenticationPrincipal UserDetails userDetails) {
        PersonalAccountDTO personalAccountDTO = customerService.getCustomerByUserEmail(userDetails.getUsername());
        return ResponseEntity.ok(personalAccountDTO);
    }
}

