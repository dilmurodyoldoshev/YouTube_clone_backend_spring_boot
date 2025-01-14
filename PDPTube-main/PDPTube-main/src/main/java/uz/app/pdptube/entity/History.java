package uz.app.pdptube.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "history_videos",
            joinColumns = @JoinColumn(name = "history_id"),
            inverseJoinColumns = @JoinColumn(name = "video_id")
    )
    private Set<Video> videos;
}
