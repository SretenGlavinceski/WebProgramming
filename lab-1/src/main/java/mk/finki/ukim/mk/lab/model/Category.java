package mk.finki.ukim.mk.lab.model;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class Category {
    String typeCategory;
    double popularity;

    public Category(String typeCategory, double popularity) {
        this.typeCategory = typeCategory;
        this.popularity = popularity;
    }
}
