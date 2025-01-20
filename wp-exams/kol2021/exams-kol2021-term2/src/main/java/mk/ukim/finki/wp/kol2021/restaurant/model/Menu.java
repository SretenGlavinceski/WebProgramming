package mk.ukim.finki.wp.kol2021.restaurant.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Entity
@Data
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;

    @Enumerated(EnumType.STRING)
    private MenuType menuType;

    @ManyToMany(fetch = FetchType.EAGER)
    private List<MenuItem> menuItems;

    public Menu(String restaurantName, MenuType menuType, List<MenuItem> menuItems) {
        this.restaurantName = restaurantName;
        this.menuType = menuType;
        this.menuItems = menuItems;
    }
}
