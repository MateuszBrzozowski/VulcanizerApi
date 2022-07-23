package pl.mbrzozowski.vulcanizer.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity(name = "opinion")
public class Opinion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_business")
    private Business business;
    private int stars;
    private String description;
    private boolean visibility;
    @Column(name = "created_time")
    private LocalDateTime createdTime = LocalDateTime.now();
    @Column(name = "author_name")
    private String authorName;

    @Builder
    public Opinion(User user,
                   Business business,
                   int stars,
                   String description,
                   boolean visibility,
                   LocalDateTime createdTime,
                   String authorName) {
        this.user = user;
        this.business = business;
        this.stars = stars;
        this.description = description;
        this.visibility = visibility;
        this.createdTime = createdTime;
        this.authorName = authorName;
    }
}
