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
    private Company business;
    private int stars;
    private String description;
    private boolean visibility = true;
    @Column(name = "created_time")
    private LocalDateTime createdTime = LocalDateTime.now();
    @Column(name = "author_name")
    private String authorName;
    @OneToOne(mappedBy = "opinion")
    private Visit visit;

    @Builder
    public Opinion(User user,
                   Company business,
                   int stars,
                   String description) {
        this.user = user;
        this.business = business;
        this.stars = stars;
        this.description = description;
        this.authorName = user.getFirstName() + " " + user.getLastName();
    }
}
