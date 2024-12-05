package mk.finki.ukim.mk.test_controller.model;

import lombok.Data;

@Data
public class Location {

    private static long ID_GENERATOR = 1;
    private Long id;
    private String name;
    private String address;
    private String capacity;
    private String description;

    public Location(String name, String address, String capacity, String description) {
        this.id = ID_GENERATOR++;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.description = description;
    }
}
