package com.app.medicineauthourity.domain.author;

import com.app.medicineauthourity.data.author.CategoryRepository;
import com.app.medicineauthourity.data.model.Category;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class CategoryRepositoryImpl implements CategoryRepository {
    @Override
    public void addCategory(Category category, MutableLiveData<Boolean> success) {
        if (category.getName().equals("cat1")) {
            success.setValue(true);
        } else {
            success.setValue(false);
        }
    }

    @Override
    public void retrieveCategories(MutableLiveData<List<Category>> categories) {
        List<Category> categoriesList = new ArrayList<>();
        categoriesList.add(new Category("Category 2", "desc1"));
        categoriesList.add(new Category("Category 2", "desc2"));
        categoriesList.add(new Category("Category 3", "desc3"));
        categoriesList.add(new Category("Category 4", "desc4"));
        categories.setValue(categoriesList);
    }
}
