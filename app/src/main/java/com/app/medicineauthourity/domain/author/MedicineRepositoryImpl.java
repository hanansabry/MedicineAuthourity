package com.app.medicineauthourity.domain.author;

import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.data.model.Medicine;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.MutableLiveData;

public class MedicineRepositoryImpl implements MedicineRepository {
    @Override
    public void addMedicine(Medicine medicine, MutableLiveData<Boolean> success) {
        if (medicine.getName().equals("med1")) {
            success.setValue(true);
        } else {
            success.setValue(false);
        }
    }

    @Override
    public void updateApproveStatus(Medicine medicine, MutableLiveData<Boolean> updateStatus) {
        updateStatus.setValue(true);
    }

    @Override
    public void retrieveMedicines() {
    }

    @Override
    public void retrieveMedicinesById(String medicineId, MutableLiveData<Medicine> medicine) {

        Medicine m1 = new Medicine("Med1", null, "cat1", null, null, null, Medicine.Production.Generic.name());
        m1.setId("5111");

        List<String> attrs = new ArrayList<>();
        attrs.add(Medicine.ApproveAttr.Attr1.getContent());
        attrs.add(Medicine.ApproveAttr.Attr3.getContent());
        attrs.add(Medicine.ApproveAttr.Attr4.getContent());
        m1.setAttrs(attrs);
        medicine.setValue(m1);
    }

    @Override
    public void retrieveMedicineByCode(String barcode, MutableLiveData<Medicine> medicine) {
        Medicine m1 = new Medicine("Med by barcode", null, "cat1", null, null, null, Medicine.Production.Generic.name());
        m1.setId("barcode id");
        List<String> attrs = new ArrayList<>();
        attrs.add(Medicine.ApproveAttr.Attr1.getContent());
        attrs.add(Medicine.ApproveAttr.Attr2.getContent());
        attrs.add(Medicine.ApproveAttr.Attr3.getContent());
        attrs.add(Medicine.ApproveAttr.Attr4.getContent());
        attrs.add(Medicine.ApproveAttr.Attr5.getContent());
        attrs.add(Medicine.ApproveAttr.Attr6.getContent());
        m1.setAttrs(attrs);
        medicine.setValue(m1);
    }

    @Override
    public void retrieveMedicinesByCategory(String categoryId, MutableLiveData<List<Medicine>> medicines) {
        List<Medicine> genericMedicines = new ArrayList<>();
        Medicine m1 = new Medicine("Med1", null, "cat1", null, null, null, Medicine.Production.Generic.name());
        Medicine m2 = new Medicine("Med2", null, "cat2", null, null, null, Medicine.Production.Generic.name());
        Medicine m3 = new Medicine("Med3", null, "cat2", null, null, null, Medicine.Production.Generic.name());
        genericMedicines.add(m1);
        genericMedicines.add(m2);
        genericMedicines.add(m3);

        medicines.setValue(genericMedicines);
    }

    @Override
    public void retrieveMedicinesByProduction(String productionType, MutableLiveData<List<Medicine>> medicines) {
        List<Medicine> genericMedicines = new ArrayList<>();
        Medicine m1 = new Medicine("Generice Med1", null, "cat1", null, null, null, Medicine.Production.Generic.name());
        Medicine m2 = new Medicine("Generice Med2", null, "cat2", null, null, null, Medicine.Production.Generic.name());
        Medicine m3 = new Medicine("Generice Med3", null, "cat2", null, null, null, Medicine.Production.Generic.name());
        genericMedicines.add(m1);
        genericMedicines.add(m2);
        genericMedicines.add(m3);

        medicines.setValue(genericMedicines);
    }
}
