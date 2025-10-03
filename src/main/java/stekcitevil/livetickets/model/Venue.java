package stekcitevil.livetickets.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String address;

    public Venue(String title, String address) {
        this.title = title;
        this.address = address;
    }

    @OneToMany(mappedBy = "eventVenue")
    private List<Event> events = new ArrayList<>();
}
