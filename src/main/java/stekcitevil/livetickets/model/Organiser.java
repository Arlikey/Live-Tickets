package stekcitevil.livetickets.model;

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
public class Organiser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;

    public Organiser(String title) {
        this.title = title;
    }

    @ManyToMany(mappedBy = "organisers")
    private List<Event> events = new ArrayList<>();
}
