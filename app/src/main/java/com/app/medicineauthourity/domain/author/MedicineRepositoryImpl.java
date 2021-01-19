package com.app.medicineauthourity.domain.author;

import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.data.model.Medicine;

import java.util.List;

public class MedicineRepositoryImpl implements MedicineRepository {
    @Override
    public void addMedicine(Medicine medicine) {

    }

    @Override
    public List<Medicine> retrieveMedicines() {
        return null;
    }

    @Override
    public List<Medicine> retrieveMedicinesByCategory(String categoryId) {
        return null;
    }

    @Override
    public List<Medicine> retrieveMedicinesByProduction(String productionType) {
        return null;
    }
}
