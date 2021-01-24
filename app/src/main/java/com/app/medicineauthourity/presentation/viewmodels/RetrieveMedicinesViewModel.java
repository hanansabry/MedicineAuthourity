package com.app.medicineauthourity.presentation.viewmodels;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.domain.author.usecases.RetrieveMedicinesUseCase;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetrieveMedicinesViewModel extends ViewModel {

    private RetrieveMedicinesUseCase retrieveMedicinesUseCase;
    private MutableLiveData<List<Medicine>> medicines = new MutableLiveData<>();
    private MutableLiveData<Medicine> medicine = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RetrieveMedicinesViewModel() {
        this.retrieveMedicinesUseCase = Injection.getRetrieveMedicineUseCase();
    }

    public void retrieveGenericMedicines() {
        retrieveMedicinesUseCase.executeByProduction(Medicine.Production.Generic.name(), medicines);
    }

    public void retrieveMedicineById(String medicineId) {
        retrieveMedicinesUseCase.executeByMedicineId(medicineId, medicine);
    }

    public void retrieveMedicineByCode(String barcode) {
        retrieveMedicinesUseCase.executeByCode(barcode, medicine);
    }

    public void getMedicinesByCategory(String catId) {
        retrieveMedicinesUseCase.executeByCategory(catId, medicines);
    }

    public MutableLiveData<List<Medicine>> getMedicines() {
        return medicines;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public MutableLiveData<Medicine> getMedicine() {
        return medicine;
    }
}
