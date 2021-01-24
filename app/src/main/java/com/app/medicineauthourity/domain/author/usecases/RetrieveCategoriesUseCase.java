package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.CategoryRepository;
import com.app.medicineauthourity.data.model.Category;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class RetrieveCategoriesUseCase {

    private CategoryRepository categoryRepository;

    public RetrieveCategoriesUseCase(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public void execute(MutableLiveData<List<Category>> categories) {
        categoryRepository.retrieveCategories(categories);
    }
}
