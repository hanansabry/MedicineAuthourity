package com.app.medicineauthourity.data.author;

import com.app.medicineauthourity.data.model.Medicine;

import java.util.List;

import androidx.lifecycle.MutableLiveData;

public interface MedicineRepository {

    void addMedicine(Medicine medicine, MutableLiveData<Boolean> success);
    void retrieveMedicines();
    void retrieveMedicinesById(String medicineId, MutableLiveData<Medicine> medicine);
    void retrieveMedicinesByCategory(String categoryId, MutableLiveData<List<Medicine>> medicines);
    void retrieveMedicinesByProduction(String productionType, MutableLiveData<List<Medicine>> medicines);
    void updateApproveStatus(Medicine medicine, MutableLiveData<Boolean> updateStatus);
    void retrieveMedicineByCode(String barcode, MutableLiveData<Medicine> medicine);
}
