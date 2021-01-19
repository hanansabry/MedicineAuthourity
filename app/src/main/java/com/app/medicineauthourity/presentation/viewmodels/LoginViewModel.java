package com.app.medicineauthourity.presentation.viewmodels;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.domain.author.usecases.LoginUseCase;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private LoginUseCase loginUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<String> usernameError = new MutableLiveData<>();
    private MutableLiveData<String> passwordError = new MutableLiveData<>();

    public LoginViewModel() {
        loginUseCase = Injection.getLoginUseCase();
    }

    public void login(String username, String password) {
        if (validate(username, password)) {
            loginUseCase.execute(username, password, success);
        }
    }

    private boolean validate(String username, String password) {
        boolean isValidate = true;
        if (username == null || username.isEmpty()) {
            usernameError.setValue("Required");
            isValidate = false;
        }
        if (password == null || password.isEmpty()) {
            passwordError.setValue("Required");
            isValidate = false;
        }
        return isValidate;
    }

    public MutableLiveData<Boolean> isLoginSucceeded() {
        return success;
    }

    public MutableLiveData<String> getUsernameError() {
        return usernameError;
    }

    public MutableLiveData<String> getPasswordError() {
        return passwordError;
    }
}
