package stekcitevil.livetickets.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

public record EventPageDTO(
        Long id,
        String image,
        String name,
        LocalDate eventDate,
        String eventVenue,
        String venueName,
        @JsonFormat(pattern = "HH:mm")
        LocalTime startTime,
        @JsonFormat(pattern = "HH:mm")
        LocalTime endTime,
        Integer tickets,
        String description,
        BigDecimal price
) {}

