package com.app.medicineauthourity.data.author;

import com.app.medicineauthourity.data.model.Medicine;

import java.util.List;

public interface MedicineRepository {

    void addMedicine(Medicine medicine);
    List<Medicine> retrieveMedicines();
    List<Medicine> retrieveMedicinesByCategory(String categoryId);
    List<Medicine> retrieveMedicinesByProduction(String productionType);
}
