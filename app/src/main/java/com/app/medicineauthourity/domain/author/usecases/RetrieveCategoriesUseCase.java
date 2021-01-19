package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.CategoryRepository;
import com.app.medicineauthourity.data.model.Category;

import java.util.List;

public class RetrieveCategoriesUseCase {

    private CategoryRepository categoryRepository;

    public RetrieveCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> execute() {
        return categoryRepository.retrieveCategories();
    }
}
