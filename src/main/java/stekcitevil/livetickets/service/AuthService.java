package stekcitevil.livetickets.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import stekcitevil.livetickets.dto.SignupRequest;
import stekcitevil.livetickets.model.Customer;
import stekcitevil.livetickets.model.ERole;
import stekcitevil.livetickets.model.Role;
import stekcitevil.livetickets.model.User;
import stekcitevil.livetickets.repository.CustomerRepository;
import stekcitevil.livetickets.repository.RoleRepository;
import stekcitevil.livetickets.repository.UserRepository;
import stekcitevil.livetickets.validation.ValidationException;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       CustomerRepository customerRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerRepository = customerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(SignupRequest signupRequest) {
        validateSignupRequest(signupRequest);

        if (userRepository.existsByEmail(signupRequest.email())) {
            throw new ValidationException("Email is already in use!");
        }

        User user = new User(signupRequest.email(),
                passwordEncoder.encode(signupRequest.password()));
        Customer customer = new Customer(signupRequest.fullName(), signupRequest.phone());
        customer.setUser(user);

        Set<String> reqRoles = signupRequest.roles();
        Set<Role> roles = new HashSet<>();

        if (reqRoles == null || reqRoles.isEmpty()) {
            roles.add(getRole(ERole.ROLE_USER));
        } else {
            reqRoles.forEach(r -> {
                switch (r.toLowerCase()) {
                    case "admin" -> roles.add(getRole(ERole.ROLE_ADMIN));
                    case "mod" -> roles.add(getRole(ERole.ROLE_MODERATOR));
                    default -> roles.add(getRole(ERole.ROLE_USER));
                }
            });
        }

        user.setRoles(roles);

        userRepository.save(user);
        customerRepository.save(customer);
    }

    private Role getRole(ERole roleName) {
        return roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
    }

    private void validateSignupRequest(SignupRequest request) {
        if (request.fullName() == null || request.fullName().trim().isEmpty()) {
            throw new ValidationException("Full name is required");
        }
        if (request.email() == null || !request.email().matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$")) {
            throw new ValidationException("Invalid email format");
        }
        if (request.phone() == null || !request.phone().matches("^\\+?\\d{7,15}$")) {
            throw new ValidationException("Invalid phone number");
        }
        if (request.password() == null || request.password().length() < 6) {
            throw new ValidationException("Password must be at least 6 characters");
        }
    }
}
