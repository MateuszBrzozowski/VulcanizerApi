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
    private String addressLineTwo;
    @Column(name = "address_line_two")
    private String addressLineOne;
    private String city;
    @Column(name = "postal_code")
    private String code;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_state")
    private State state;
}
