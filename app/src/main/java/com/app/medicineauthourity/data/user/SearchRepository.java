package com.app.medicineauthourity.data.user;

public interface SearchRepository {

    void searchByMedicine(String medicineId);
    void searchByBarCode(String barcode);
}
