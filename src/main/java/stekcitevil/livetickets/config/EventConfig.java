package stekcitevil.livetickets.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import stekcitevil.livetickets.model.Event;
import stekcitevil.livetickets.repository.EventRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class EventConfig {
    @Bean
    CommandLineRunner init(EventRepository eventRepository) {
        return args -> {
            var eventsList = List.of(
                    new Event(null, "Imagine Dragons Live", "Live Nation",
                            LocalDate.of(2025, 10, 12),
                            LocalTime.of(19, 30),
                            Duration.ofHours(2),
                            10000, 10000, 89.99,
                            "Madison Square Garden"),

                    new Event(null, "Java Spring Boot Conference", "Tech Events Inc",
                            LocalDate.of(2025, 11, 5),
                            LocalTime.of(9, 0),
                            Duration.ofHours(8),
                            500, 500, 199.99,
                            "Berlin Congress Center"),

                    new Event(null, "Stand-up Comedy Night", "FunnyPeople",
                            LocalDate.of(2025, 9, 25),
                            LocalTime.of(20, 0),
                            Duration.ofHours(1).plusMinutes(30),
                            200, 200, 25.0,
                            "Kyiv Comedy Club"),

                    new Event(null, "Football Match: Dynamo vs Shakhtar", "Ukrainian Premier League",
                            LocalDate.of(2025, 9, 30),
                            LocalTime.of(18, 0),
                            Duration.ofHours(2),
                            30000, 30000, 15.0,
                            "NSC Olimpiyskiy"),

                    new Event(null, "Classical Music Evening", "Kyiv Philharmonic",
                            LocalDate.of(2025, 12, 20),
                            LocalTime.of(18, 30),
                            Duration.ofHours(2),
                            800, 800, 45.0,
                            "Kyiv Opera House")
            );
            eventRepository.saveAll(eventsList);
        };
    }
}
