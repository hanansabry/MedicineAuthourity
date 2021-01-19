package com.app.medicineauthourity.data.author;

import com.app.medicineauthourity.data.model.Category;

import java.util.List;

public interface CategoryRepository {

    void addCategory(Category category);
    List<Category> retrieveCategories();
}
