package pl.mbrzozowski.vulcanizer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "employee_permission")
public class EmployeePermission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //TODO
    // : Do przemyslenia jak to polaczyc wydajne, rola zeby miala swoje permisjie i osobny pracownik zeby mogl
    // : na poczatek mozemy zrobic ze sama rola ma tylko permisje - potem mozna to roziwjac ze dla kazdego uzytkownika z osobna

    private Long idOfTyp;
    private String name;
    private boolean isGrant;
}
