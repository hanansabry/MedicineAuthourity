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
    private MutableLiveData<List<String>> conflictMedicinesLiveList = new MutableLiveData<>();
    private MutableLiveData<List<String>> contradictoryDiseasesLiveList = new MutableLiveData<>();
    private MutableLiveData<Boolean> addConflictSuccess = new MutableLiveData<>();
    private MutableLiveData<Boolean> addContradictorySuccess = new MutableLiveData<>();
    private MutableLiveData<Medicine> medicine = new MutableLiveData<>();
    private MutableLiveData<String> error = new MutableLiveData<>();

    public RetrieveMedicinesViewModel() {
        this.retrieveMedicinesUseCase = Injection.getRetrieveMedicineUseCase();
    }

    public void retrieveAllMedicines() {
        retrieveMedicinesUseCase.execute(medicines);
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

    public void getConflictMedicines(Medicine medicine) {
        retrieveMedicinesUseCase.executeRetrievingConflictMedicines(medicine, conflictMedicinesLiveList);
    }

    public void getContradictoryDiseases(Medicine medicine) {
        retrieveMedicinesUseCase.executeRetrievingContradictoryDiseases(medicine, contradictoryDiseasesLiveList);
    }

    public void addConflictMedicine(Medicine medicine, String value) {
        retrieveMedicinesUseCase.executeAddingConflictMedicine(medicine, value, addConflictSuccess);
    }

    public void addContradictoryDisease(Medicine medicine, String value) {
        retrieveMedicinesUseCase.executeAddingContradictoryDisease(medicine, value, addContradictorySuccess);
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

    public MutableLiveData<List<String>> getConflictMedicinesLiveList() {
        return conflictMedicinesLiveList;
    }

    public MutableLiveData<List<String>> getContradictoryDiseasesLiveList() {
        return contradictoryDiseasesLiveList;
    }

    public MutableLiveData<Boolean> getAddConflictSuccess() {
        return addConflictSuccess;
    }

    public MutableLiveData<Boolean> getAddContradictorySuccess() {
        return addContradictorySuccess;
    }
}
