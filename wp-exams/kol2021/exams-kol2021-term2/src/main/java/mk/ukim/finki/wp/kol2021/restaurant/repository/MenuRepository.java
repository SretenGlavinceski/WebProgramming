package mk.ukim.finki.wp.kol2021.restaurant.repository;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface MenuRepository extends JpaRepository<Menu, Long> {
}
