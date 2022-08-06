package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "address_line_one")
    private String addressLine;
    @Column(length = 50)
    private String city;
    @Column(name = "postal_code")
    private String code;
    @ManyToOne
    @JoinColumn(name = "id_state")
    private State state;
    @Column(length = 60)
    private String country;

}
