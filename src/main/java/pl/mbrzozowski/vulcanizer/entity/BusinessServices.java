package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "service")
public class BusinessServices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "id_business")
    private Company business;
    @Column(name = "name_one")
    private String nameOne;
    @Column(name = "name_two")
    private String nameTwo;
    @Column(name = "execution_time")
    private LocalTime executionTime;
    private double price;

    public BusinessServices(Company business,
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
