package stekcitevil.livetickets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import stekcitevil.livetickets.config.jwt.JwtUtils;
import stekcitevil.livetickets.dto.JwtResponse;
import stekcitevil.livetickets.dto.LoginRequest;
import stekcitevil.livetickets.dto.SignupRequest;
import stekcitevil.livetickets.dto.MessageResponse;
import stekcitevil.livetickets.model.Customer;
import stekcitevil.livetickets.model.ERole;
import stekcitevil.livetickets.model.Role;
import stekcitevil.livetickets.model.User;
import stekcitevil.livetickets.repository.CustomerRepository;
import stekcitevil.livetickets.repository.RoleRepository;
import stekcitevil.livetickets.repository.UserRepository;
import stekcitevil.livetickets.service.AuthService;
import stekcitevil.livetickets.service.CustomUserDetails;
import stekcitevil.livetickets.validation.ValidationException;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AuthService authService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.email(),
                        loginRequest.password()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail(), roles)
        );
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {
        try {
            authService.registerUser(signupRequest);
            return ResponseEntity.ok(new MessageResponse("User created successfully!"));
        } catch (ValidationException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: " + ex.getMessage()));
        }
    }

}
