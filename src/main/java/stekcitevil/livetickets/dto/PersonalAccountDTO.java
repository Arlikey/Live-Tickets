package stekcitevil.livetickets.dto;

import stekcitevil.livetickets.model.Ticket;

import java.util.List;

public record PersonalAccountDTO(String fullName, String phone, List<FullTicketDTO> tickets) {
}
