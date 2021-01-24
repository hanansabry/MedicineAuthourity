package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.data.model.Medicine;

import androidx.lifecycle.MutableLiveData;

public class AddMedicineUseCase {

    private MedicineRepository medicineRepository;
    private Medicine medicine;

    public AddMedicineUseCase(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
        this.medicine = medicine;
    }

    public void execute(Medicine medicine, MutableLiveData<Boolean> success) {
        medicineRepository.addMedicine(medicine, success);
    }

    public void updateApproveStatus(Medicine medicine, MutableLiveData<Boolean> updateStatus) {
        medicineRepository.updateApproveStatus(medicine, updateStatus);
    }
}
