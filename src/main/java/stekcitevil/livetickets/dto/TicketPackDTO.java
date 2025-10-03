package stekcitevil.livetickets.dto;

import java.math.BigDecimal;

public record TicketPackDTO(
        BigDecimal cost,
        Integer count
) {}