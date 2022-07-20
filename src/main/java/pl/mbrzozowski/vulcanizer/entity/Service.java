package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "service")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_business")
    private Business business;
    @Column(name = "name_one")
    private String nameOne;
    @Column(name = "name_two")
    private String nameTwo;
    @Column(name = "execution_time")
    private LocalTime executionTime;
    private double price;

    public Service(Business business,
                   String nameOne,
                   String nameTwo,
                   LocalTime executionTime,
                   double price) {
        this.business = business;
        this.nameOne = nameOne;
        this.nameTwo = nameTwo;
        this.executionTime = executionTime;
        this.price = price;
    }
}
