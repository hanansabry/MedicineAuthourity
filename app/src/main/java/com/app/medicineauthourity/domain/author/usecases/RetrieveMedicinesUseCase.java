package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.data.model.Medicine;

import java.util.List;

public class RetrieveMedicinesUseCase {

    private MedicineRepository medicineRepository;

    public RetrieveMedicinesUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    public List<Medicine> execute() {
        return medicineRepository.retrieveMedicines();
    }

    public List<Medicine> executeByCategory(String categoryId) {
        return medicineRepository.retrieveMedicinesByCategory(categoryId);
    }

    public List<Medicine> executeByProduction(String productionType) {
        return medicineRepository.retrieveMedicinesByProduction(productionType);
    }
}
