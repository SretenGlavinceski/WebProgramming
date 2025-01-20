package mk.ukim.finki.wp.kol2021.restaurant.web;

import mk.ukim.finki.wp.kol2021.restaurant.model.Menu;
import mk.ukim.finki.wp.kol2021.restaurant.model.MenuType;
import mk.ukim.finki.wp.kol2021.restaurant.service.MenuService;
import mk.ukim.finki.wp.kol2021.restaurant.service.impl.MenuItemServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class MenuController {

    private final MenuService menuService;
    private final MenuItemServiceImpl menuItemService;

    public MenuController(MenuService service, MenuItemServiceImpl menuItemService) {
        this.menuService = service;
        this.menuItemService = menuItemService;
    }


    @GetMapping({"/", "/menu"})
    public String showMenus(@RequestParam(required = false) String nameSearch,
                            @RequestParam(required = false) MenuType menuType,
                            Model model) {

        List<Menu> menus = null;
        if (nameSearch == null && menuType == null) {
            menus = menuService.listAll();
        } else {
            menus = this.menuService.listMenuItemsByRestaurantNameAndMenuType(nameSearch,  menuType);
        }
        model.addAttribute("menus", menus);
        model.addAttribute("menuTypes", MenuType.values());

        return "list";
    }

    @GetMapping("/menu/add")
    public String showAdd(Model model) {
        model.addAttribute("menuTypes", MenuType.values());
        model.addAttribute("items", menuItemService.listAll());
        model.addAttribute("pathURL", "/menu");

        return "form";
    }

    @GetMapping("/menu/{id}/edit")
    public String showEdit(@PathVariable Long id, Model model) {
        model.addAttribute("menu", menuService.findById(id));
        model.addAttribute("menuTypes", MenuType.values());
        model.addAttribute("items", menuItemService.listAll());
        model.addAttribute("pathURL", "/menu/" + id);

        return "form";
    }
//String name, MenuType menuType, List<Long> menuItemIds
    @PostMapping("/menu")
    public String create(@RequestParam String name,
                         @RequestParam MenuType menuType,
                         @RequestParam List<Long> menuItemsId) {
        menuService.create(name, menuType, menuItemsId);
        return "redirect:/menu";
    }
//Long id, String name, String description, MenuType menuType, List<Long> menuItemIds
    @PostMapping("/menu/{id}")
    public String update(@PathVariable Long id,
                         @RequestParam String name,
                         @RequestParam MenuType menuType,
                         @RequestParam List<Long> menuItemsId) {
        menuService.update(id, name, menuType, menuItemsId);
        return "redirect:/menu";
    }

    @PostMapping("/menu/{id}/delete")
    public String delete(@PathVariable Long id) {
        menuService.delete(id);
        return "redirect:/menu";
    }
}
