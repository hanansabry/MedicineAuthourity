package com.app.medicineauthourity.presentation.viewmodels;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.data.model.Category;
import com.app.medicineauthourity.domain.author.usecases.RetrieveCategoriesUseCase;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetrieveCategoriesViewModel extends ViewModel {

    private RetrieveCategoriesUseCase retrieveCategoriesUseCase;
    private MutableLiveData<List<Category>> categories = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RetrieveCategoriesViewModel() {
        this.retrieveCategoriesUseCase = Injection.getRetrieveCategoriesUseCase();
    }

    public void retrieveCategories() {
        retrieveCategoriesUseCase.execute(categories);
    }

    public MutableLiveData<List<Category>> getCategories() {
        return categories;
    }

    public void setCategories(MutableLiveData<List<Category>> categories) {
        this.categories = categories;
    }
}
