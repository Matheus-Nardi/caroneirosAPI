package caroneiros.domain.models;

import caroneiros.infra.exceptions.NotFoundException;

public enum City {

    PALMAS(1l, "Palmas"), PORTO_NACIONAL(2l, "Porto Nacional");

    public final Long id;
    public final String name;

    City(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static City findCity(String name) {
        for (City city : values()) {
            if (city.name.equals(name)) {
                return city;
            }

        }
        throw new NotFoundException("Cidade não encontrada");
    }
}
