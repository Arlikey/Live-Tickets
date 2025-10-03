package stekcitevil.livetickets.dto;

import java.util.Set;

public record SignupRequest (String fullName, String phone, String email, Set<String> roles, String password){

}
