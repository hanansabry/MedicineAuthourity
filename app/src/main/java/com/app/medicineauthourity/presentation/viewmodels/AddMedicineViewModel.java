package com.app.medicineauthourity.presentation.viewmodels;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.domain.author.usecases.AddMedicineUseCase;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddMedicineViewModel extends ViewModel {

    private AddMedicineUseCase addMedicineUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<Boolean> updateStatus = new MutableLiveData<>();
    private MutableLiveData<String> medNameError = new MutableLiveData<>();
    private MutableLiveData<String> globalError = new MutableLiveData<>();

    public AddMedicineViewModel() {
        addMedicineUseCase = Injection.getMedicineUseCase();
    }

    public void addMedicine(String name, String barQRCode, String categoryName, String composition, String usage, String physiological, String production) {
        if (valid(name, barQRCode, categoryName, composition, usage, physiological, production)) {
            Medicine newMedicine = new Medicine(name, barQRCode, categoryName, composition,
                    usage, physiological, production);
            addMedicineUseCase.execute(newMedicine, success);
        }
    }

    public void updateApprovedStatus(Medicine medicine) {
        addMedicineUseCase.updateApproveStatus(medicine, updateStatus);
    }

    private boolean valid(String name, String barQRCode, String categoryName, String composition, String usage, String physiological, String production) {
        boolean isValid = true;
        if (name == null || name.isEmpty()) {
            medNameError.setValue("Required");
            isValid = false;
        } else if (categoryName == null || categoryName.isEmpty()) {
            globalError.setValue("You must select disease category");
            isValid = false;
        } else if (composition == null || composition.isEmpty()) {
            globalError.setValue("You must select composition value");
            isValid = false;
        } else if (usage == null || usage.isEmpty()) {
            globalError.setValue("You must select usage value");
            isValid = false;
        } else if (physiological == null || physiological.isEmpty()) {
            globalError.setValue("You must select physiological value");
            isValid = false;
        } else if (production == null || production.isEmpty()) {
            globalError.setValue("You must select production value");
            isValid = false;
        }
        return isValid;
    }

    public MutableLiveData<Boolean> getSuccess() {
        return success;
    }

    public MutableLiveData<String> getMedNameError() {
        return medNameError;
    }

    public MutableLiveData<String> getGlobalError() {
        return globalError;
    }

    public MutableLiveData<Boolean> getUpdateStatus() {
        return updateStatus;
    }
}
