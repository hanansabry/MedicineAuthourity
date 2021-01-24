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

    public void executeByCategory(String categoryId) {
        medicineRepository.retrieveMedicinesByCategory(categoryId);
    }

    public void executeByProduction(String productionType, MutableLiveData<List<Medicine>> medicines) {
        medicineRepository.retrieveMedicinesByProduction(productionType, medicines);
    }
}
