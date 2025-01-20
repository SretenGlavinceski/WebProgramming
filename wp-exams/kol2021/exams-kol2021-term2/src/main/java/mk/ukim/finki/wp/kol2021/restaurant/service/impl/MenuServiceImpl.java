package mk.ukim.finki.wp.kol2021.restaurant.service.impl;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.model.exceptions.InvalidMenuIdException;
import mk.ukim.finki.wp.kol2021.restaurant.repository.MenuRepository;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final MenuItemServiceImpl menuItemService;

    public MenuServiceImpl(MenuRepository menuRepository, MenuItemServiceImpl menuItemService) {
        this.menuRepository = menuRepository;
        this.menuItemService = menuItemService;
    }

    @Override
    public List<Menu> listAll() {
        return menuRepository.findAll();
    }

    @Override
    public Menu findById(Long id) {
        return menuRepository.findById(id).orElseThrow(InvalidMenuIdException::new);
    }

    @Override
    public Menu create(String restaurantName, MenuType menuType, List<Long> menuItems) {
        Menu menu = new Menu(
                restaurantName,
                menuType,
                menuItems.stream().map(menuItemService::findById).collect(Collectors.toList())
        );

        menuRepository.save(menu);
        return menu;
    }
    @Override
    public Menu update(Long id, String restaurantName, MenuType menuType, List<Long> menuItems) {
        Menu menu = findById(id);
        menu.setRestaurantName(restaurantName);
        menu.setMenuType(menuType);
        menu.setMenuItems(menuItems.stream().map(menuItemService::findById).collect(Collectors.toList()));

        menuRepository.save(menu);
        return menu;
    }

    @Override
    public Menu delete(Long id) {
        Menu menu = findById(id);
        menuRepository.delete(menu);
        return menu;
    }

    @Override
    public List<Menu> listMenuItemsByRestaurantNameAndMenuType(String restaurantName, MenuType menuType) {
        List<Menu> menus = listAll();

        if (restaurantName != null)
            menus = menus.stream()
                    .filter(menu -> menu.getRestaurantName().contains(restaurantName))
                    .collect(Collectors.toList());

        if (menuType != null)
            menus = menus.stream()
                    .filter(menu -> menu.getMenuType().equals(menuType))
                    .collect(Collectors.toList());

        return menus;
    }
}
