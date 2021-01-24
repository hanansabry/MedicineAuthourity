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

    public void execute() {
        medicineRepository.retrieveMedicines();
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
}
