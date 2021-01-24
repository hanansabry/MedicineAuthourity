package com.app.medicineauthourity.data.author;

import com.app.medicineauthourity.data.model.Medicine;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface MedicineRepository {

    void addMedicine(Medicine medicine, MutableLiveData<Boolean> success);
    void retrieveMedicines();
    void retrieveMedicinesByCategory(String categoryId);
    void retrieveMedicinesByProduction(String productionType, MutableLiveData<List<Medicine>> medicines);
    void updateApproveStatus(Medicine medicine, MutableLiveData<Boolean> updateStatus);
}
