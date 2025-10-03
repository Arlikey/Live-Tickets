//package stekcitevil.livetickets.config;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import stekcitevil.livetickets.model.*;
//import stekcitevil.livetickets.repository.*;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//@Configuration
//public class DataConfig {
//
//    @Bean
//    CommandLineRunner initData(
//            EventRepository eventRepository,
//            VenueRepository venueRepository,
//            OrganiserRepository organiserRepository,
//            TicketRepository ticketRepository,
//            CustomerRepository customerRepository
//    ) {
//        return args -> {
//            // ----- Venues -----
//            var msg = new Venue("Madison Square Garden", "New York, USA");
//            var bcc = new Venue("Berlin Congress Center", "Berlin, Germany");
//            var kcc = new Venue("Kyiv Comedy Club", "Kyiv, Ukraine");
//            var nso = new Venue("NSC Olimpiyskiy", "Kyiv, Ukraine");
//            var opera = new Venue("Kyiv Opera House", "Kyiv, Ukraine");
//            venueRepository.saveAll(List.of(msg, bcc, kcc, nso, opera));
//
//            // ----- Organisers -----
//            var liveNation = new Organiser("Live Nation");
//            var techEvents = new Organiser("Tech Events Inc");
//            var funnyPeople = new Organiser("FunnyPeople");
//            var upl = new Organiser("Ukrainian Premier League");
//            var philharmonic = new Organiser("Kyiv Philharmonic");
//            organiserRepository.saveAll(List.of(liveNation, techEvents, funnyPeople, upl, philharmonic));
//
//            // ----- Events -----
//            var event1 = new Event();
//            event1.setName("Imagine Dragons Live");
//            event1.setImages(List.of("nainoa-shizuru-NcdG9mK3PBY-unsplash.jpg"));
//            event1.setStartDateTime(LocalDateTime.of(2025, 10, 12, 19, 30));
//            event1.setEndDateTime(LocalDateTime.of(2025, 10, 12, 21, 30));
//            event1.setEventVenue(msg);
//            event1.getOrganisers().add(liveNation);
//
//            var event2 = new Event();
//            event2.setName("Java Spring Boot Conference");
//            event2.setImages(List.of("alexandre-debieve-DOu3JJ3eLQc-unsplash.jpg"));
//            event2.setStartDateTime(LocalDateTime.of(2025, 11, 5, 9, 0));
//            event2.setEndDateTime(LocalDateTime.of(2025, 11, 5, 17, 0));
//            event2.setEventVenue(bcc);
//            event2.getOrganisers().add(techEvents);
//
//            var event3 = new Event();
//            event3.setName("Stand-up Comedy Night");
//            event3.setImages(List.of("teemu-paananen-bzdhc5b3Bxs-unsplash.jpg"));
//            event3.setStartDateTime(LocalDateTime.of(2025, 9, 25, 20, 0));
//            event3.setEndDateTime(LocalDateTime.of(2025, 9, 25, 21, 30));
//            event3.setEventVenue(kcc);
//            event3.getOrganisers().add(funnyPeople);
//
//            var event4 = new Event();
//            event4.setName("Football Match: Dynamo vs Shakhtar");
//            event4.setImages(List.of("ferdinand-stohr-woYNcuQ2BLg-unsplash.jpg"));
//            event4.setStartDateTime(LocalDateTime.of(2025, 9, 30, 18, 0));
//            event4.setEndDateTime(LocalDateTime.of(2025, 9, 30, 20, 0));
//            event4.setEventVenue(nso);
//            event4.getOrganisers().add(upl);
//
//            var event5 = new Event();
//            event5.setName("Classical Music Evening");
//            event5.setImages(List.of("chuttersnap-aEnH4hJ_Mrs-unsplash.jpg"));
//            event5.setStartDateTime(LocalDateTime.of(2025, 12, 20, 18, 30));
//            event5.setEndDateTime(LocalDateTime.of(2025, 12, 20, 20, 30));
//            event5.setEventVenue(opera);
//            event5.getOrganisers().add(philharmonic);
//            event5.setDescription("Classical Music evening in Restaurant in Philarmonic sdassdklalpskfdklwakmfokwsfow okdfwmok emfokw moekfm wme oiwme ifmwoemofkw mowmeokfmw kmefokwem fkowmeokfm wke");
//
//            eventRepository.saveAll(List.of(event1, event2, event3, event4, event5));
//
//            // ----- Tickets (пример: сгенерим 100 билетов на первое событие) -----
//            for (int i = 1; i <= 100; i++) {
//                Ticket ticket = new Ticket();
//                ticket.setNumber("TICKET-" + i);
//                ticket.setPrice(89.99);
//                ticket.setEvent(event1);
//                ticket.setStatus(TicketStatus.FREE);
//                ticketRepository.save(ticket);
//            }
//
//            // ----- Customers (пример: 1 купивший билет) -----
//            Customer customer = new Customer();
//            customer.setFullName("John Doe");
//            customer.setPhoneNumber("+123456789");
//            customerRepository.save(customer);
//
//            // назначим билет клиенту
//            Ticket soldTicket = ticketRepository.findById(1L).orElseThrow();
//            soldTicket.setCustomer(customer);
//            soldTicket.setStatus(TicketStatus.SOLD);
//            ticketRepository.save(soldTicket);
//        };
//    }
//}
