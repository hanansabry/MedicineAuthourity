package com.app.medicineauthourity.domain.author.usecases;

import com.app.medicineauthourity.data.author.MedicineRepository;
import com.app.medicineauthourity.data.model.Medicine;

public class AddMedicineUseCase {

    private MedicineRepository medicineRepository;
    private Medicine medicine;

    public AddMedicineUseCase(MedicineRepository medicineRepository, Medicine medicine) {
        this.medicineRepository = medicineRepository;
        this.medicine = medicine;
    }

    public void execute() {
        medicineRepository.addMedicine(medicine);
    }
}
