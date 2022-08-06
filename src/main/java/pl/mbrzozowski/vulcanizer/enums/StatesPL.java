package pl.mbrzozowski.vulcanizer.enums;

public enum StatesPL {
    DOLNOSLASKIE("Dolnośląskie"),
    KUJAWSKO_POMORSKIE("Kujawsko-pomorskie"),
    LUBELSKIE("Lubelskie"),
    LUBUSKIE("Lubuskie"),
    LODZKIE("Łódzkie"),
    MALOPOLSKIE("Małopolskie"),
    MAZOWIECKIE("Mazowieckie"),
    OPOLSKIE("Opolskie"),
    PODKARPACKIE("Podkarpackie"),
    PODLASKIE("Podlaskie"),
    POMORSKIE("Pomorskie"),
    SLASKIE("Śląskie"),
    SWIETOKRZYSKIE("Świętokrzyskie"),
    WARMINSKO_MAZURSKIE("Warmińsko-mazurskie"),
    WIELKOPOLSKIE("Wielkopolskie"),
    ZACHODNIO_POMORSKIE("Zachodniopomorskie");

    private final String name;

    StatesPL(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
