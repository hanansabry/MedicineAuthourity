package com.app.medicineauthourity;

import android.content.Context;
import android.content.SharedPreferences;

import com.app.medicineauthourity.data.author.AuthorRepository;
import com.app.medicineauthourity.data.author.CategoryRepository;
import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.domain.author.AuthorRepositoryImpl;
import com.app.medicineauthourity.domain.author.CategoryRepositoryImpl;
import com.app.medicineauthourity.domain.author.MedicineRepositoryImpl;
import com.app.medicineauthourity.domain.author.usecases.AddCategoryUseCase;
import com.app.medicineauthourity.domain.author.usecases.AddMedicineUseCase;
import com.app.medicineauthourity.domain.author.usecases.LoginUseCase;
import com.app.medicineauthourity.domain.author.usecases.RegisterUseCase;
import com.app.medicineauthourity.domain.author.usecases.RetrieveCategoriesUseCase;
import com.app.medicineauthourity.domain.author.usecases.RetrieveMedicinesUseCase;
import com.app.medicineauthourity.presentation.StartActivity;

import static android.content.Context.MODE_PRIVATE;

public class Injection {
    private static final String PREFS_NAME = "general";

    public static LoginUseCase getLoginUseCase() {
        return new LoginUseCase(getAuthorRepository());
    }

    private static AuthorRepository getAuthorRepository() {
        return new AuthorRepositoryImpl();
    }

    public static RegisterUseCase getRegisterUseCase() {
        return new RegisterUseCase(getAuthorRepository());
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
    }

    public static AddCategoryUseCase getAddCategoryUseCase() {
        return new AddCategoryUseCase(getCategoryRepository());
    }

    private static CategoryRepository getCategoryRepository() {
        return new CategoryRepositoryImpl();
    }

    public static AddMedicineUseCase getMedicineUseCase() {
        return new AddMedicineUseCase(getMedicineRepository());
    }

    private static MedicineRepository getMedicineRepository() {
        return new MedicineRepositoryImpl();
    }

    public static RetrieveCategoriesUseCase getRetrieveCategoriesUseCase() {
        return new RetrieveCategoriesUseCase(getCategoryRepository());
    }

    public static RetrieveMedicinesUseCase getRetrieveMedicineUseCase() {
        return new RetrieveMedicinesUseCase(getMedicineRepository());
    }
}
