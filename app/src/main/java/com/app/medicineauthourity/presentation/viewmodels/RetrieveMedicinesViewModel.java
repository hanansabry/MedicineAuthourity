package com.app.medicineauthourity.presentation.viewmodels;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.domain.author.usecases.RetrieveMedicinesUseCase;

import java.util.List;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RetrieveMedicinesViewModel extends ViewModel {

    private RetrieveMedicinesUseCase retrieveMedicinesUseCase;
    private MutableLiveData<List<Medicine>> genericMedicines = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RetrieveMedicinesViewModel() {
        this.retrieveMedicinesUseCase = Injection.getRetrieveMedicineUseCase();
    }

    public void retrieveGenericMedicines() {
        retrieveMedicinesUseCase.executeByProduction(Medicine.Production.Generic.name(), genericMedicines);
    }

    public MutableLiveData<List<Medicine>> getGenericMedicines() {
        return genericMedicines;
    }

    public MutableLiveData<String> getError() {
        return error;
    }
}
