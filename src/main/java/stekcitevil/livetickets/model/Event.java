package stekcitevil.livetickets.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import stekcitevil.livetickets.converter.DurationSecondsConverter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String eventTitle;
    private String organiser;
    private LocalDate eventDate;
    private LocalTime eventStartTime;
    @Convert(converter = DurationSecondsConverter.class)
    private Duration eventDurationTime;
    private Integer ticketsCount;
    private Integer availableTicketsCount;
    private Double ticketPrice;
    private String eventVenue;
}
