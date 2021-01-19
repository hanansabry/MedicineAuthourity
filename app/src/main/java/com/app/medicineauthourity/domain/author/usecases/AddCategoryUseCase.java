package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.CategoryRepository;
import com.app.medicineauthourity.data.model.Category;

public class AddCategoryUseCase {

    private CategoryRepository categoryRepository;
    private Category category;

    public AddCategoryUseCase(CategoryRepository categoryRepository, Category category) {
        this.categoryRepository = categoryRepository;
    }

    public void execute() {
        categoryRepository.addCategory(category);
    }
}
