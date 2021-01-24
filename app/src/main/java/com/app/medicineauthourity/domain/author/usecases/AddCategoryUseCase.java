package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.CategoryRepository;
import com.app.medicineauthourity.data.model.Category;

import androidx.lifecycle.MutableLiveData;

public class AddCategoryUseCase {

    private CategoryRepository categoryRepository;

    public AddCategoryUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(Category category, MutableLiveData<Boolean> success) {
        categoryRepository.addCategory(category, success);
    }
}
