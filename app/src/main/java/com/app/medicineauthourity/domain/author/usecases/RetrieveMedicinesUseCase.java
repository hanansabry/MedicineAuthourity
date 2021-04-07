package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.data.model.Medicine;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class RetrieveMedicinesUseCase {

    private MedicineRepository medicineRepository;

    public RetrieveMedicinesUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public void executeByCategory(String categoryId, MutableLiveData<List<Medicine>> medicines) {
        medicineRepository.retrieveMedicinesByCategory(categoryId, medicines);
    }

    public void executeByProduction(String productionType, MutableLiveData<List<Medicine>> medicines) {
        medicineRepository.retrieveMedicinesByProduction(productionType, medicines);
    }

    public void executeByMedicineId(String medicineId, MutableLiveData<Medicine> medicine) {
        medicineRepository.retrieveMedicinesById(medicineId, medicine);
    }

    public void executeByCode(String barcode, MutableLiveData<Medicine> medicine) {
        medicineRepository.retrieveMedicineByCode(barcode, medicine);
    }

    public void execute(MutableLiveData<List<Medicine>> medicines) {
        medicineRepository.retrieveAllMedicines(medicines);
    }

    public void executeRetrievingConflictMedicines(Medicine medicine, MutableLiveData<List<String>> conflictMedicinesLiveList) {
        medicineRepository.retrieveConflictMedicinesForMedicine(medicine, conflictMedicinesLiveList);
    }

    public void executeRetrievingContradictoryDiseases(Medicine medicine, MutableLiveData<List<String>> contradictoryDiseasesLiveList) {
        medicineRepository.retrieveContradictoryDiseasesForMedicine(medicine, contradictoryDiseasesLiveList);
    }

    public void executeAddingConflictMedicine(Medicine medicine, String value, MutableLiveData<Boolean> addConflictSuccess) {
        medicineRepository.addConflictMedicinesForMedicine(medicine, value, addConflictSuccess);
    }

    public void executeAddingContradictoryDisease(Medicine medicine, String value, MutableLiveData<Boolean> addContradictorySuccess) {
        medicineRepository.addContradictoryDiseasesForMedicine(medicine, value, addContradictorySuccess);
    }
}
