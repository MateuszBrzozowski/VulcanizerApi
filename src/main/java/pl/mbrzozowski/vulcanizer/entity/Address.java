package pl.mbrzozowski.vulcanizer.entity;

import javax.persistence.*;

@Entity
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
}
