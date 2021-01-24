package com.app.medicineauthourity.presentation.viewmodels;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.data.model.Category;
import com.app.medicineauthourity.domain.author.usecases.AddCategoryUseCase;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddCategoryViewModel extends ViewModel {

    private AddCategoryUseCase addCategoryUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<String> categoryNameError = new MutableLiveData<>();
    private MutableLiveData<String> descError = new MutableLiveData<>();

    public AddCategoryViewModel() {
        addCategoryUseCase = Injection.getAddCategoryUseCase();
    }

    public void addCategory(String categoryName, String desc) {
        if (validate(categoryName, desc)) {
            Category category = new Category(categoryName, desc);
            addCategoryUseCase.execute(category, success);
        }
    }

    private boolean validate(String categoryName, String desc) {
        boolean isValid = true;
        if (categoryName == null || categoryName.isEmpty()) {
            isValid = false;
            categoryNameError.setValue("Required");
        }
        if (desc == null || desc.isEmpty()) {
            isValid = false;
            descError.setValue("Required");
        }
        return isValid;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<String> getCategoryNameError() {
        return categoryNameError;
    }

    public MutableLiveData<String> getDescError() {
        return descError;
    }

}
