package stekcitevil.livetickets.validation;

import org.springframework.stereotype.Component;
import stekcitevil.livetickets.model.Event;

import java.time.Duration;
import java.time.LocalDate;

@Component
public class EventValidator {

    public void validateForCreate(Event event) {
        if (event.getId() != null) {
            throw new IllegalArgumentException("Id must be empty for new events");
        }
        validateCommon(event);
    }

    public void validateForUpdate(Event event) {
        validateCommon(event);
    }

    private void validateCommon(Event event) {
        if (event.getEventDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Event date must be after current date");
        }
        if (event.getEventDurationTime().compareTo(Duration.ZERO) <= 0) {
            throw new IllegalArgumentException("Event duration time must be greater than zero");
        }
        if (event.getTicketsCount() <= 0) {
            throw new IllegalArgumentException("Tickets count must be greater than zero");
        }
        if (event.getAvailableTicketsCount() < 0 ||
                event.getAvailableTicketsCount() > event.getTicketsCount()) {
            throw new IllegalArgumentException("Available tickets count must be between 0 and total tickets count");
        }
        if (event.getTicketPrice() < 0) {
            throw new IllegalArgumentException("Ticket price must be equals or greater than zero");
        }
    }
}