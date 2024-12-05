package mk.finki.ukim.mk.lab3.services.impl;

import mk.finki.ukim.mk.lab3.model.Category;
import mk.finki.ukim.mk.lab3.model.exception.NoCategoryByIdException;
import mk.finki.ukim.mk.lab3.repository.CategoryRepository;
import mk.finki.ukim.mk.lab3.services.CategoryServiceBluePrint;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService implements CategoryServiceBluePrint {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    public Category findCategoryById(Long categoryId) throws NoCategoryByIdException {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NoCategoryByIdException(categoryId));
    }
}
