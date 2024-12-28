package mk.finki.ukim.mk.lab3.services;

import mk.finki.ukim.mk.lab3.model.Category;
import mk.finki.ukim.mk.lab3.model.exception.NoCategoryByIdException;

import java.util.List;

public interface CategoryServiceBluePrint {
    List<Category> findAllCategories();
    Category findCategoryById(Long categoryId) throws NoCategoryByIdException;
}
