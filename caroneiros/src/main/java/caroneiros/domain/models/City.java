package caroneiros.domain.models;

public enum City {

    PALMAS(1l, "Palmas"), PORTO_NACIONAL(2l, "Porto Nacional");

    public final Long id;
    public final String name;

    City(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
