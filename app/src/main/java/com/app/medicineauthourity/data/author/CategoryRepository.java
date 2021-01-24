package com.app.medicineauthourity.data.author;

import com.app.medicineauthourity.data.model.Category;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface CategoryRepository {

    void addCategory(Category category, MutableLiveData<Boolean> success);
    void retrieveCategories(MutableLiveData<List<Category>> categories);
}
