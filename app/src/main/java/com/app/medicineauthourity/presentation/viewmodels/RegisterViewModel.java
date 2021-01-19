package com.app.medicineauthourity.presentation.viewmodels;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.data.model.Author;
import com.app.medicineauthourity.domain.author.usecases.RegisterUseCase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private static final String EG_REGEX = "(^01\\d{9})";
    private RegisterUseCase registerUseCase;
    private MutableLiveData<Boolean> success = new MutableLiveData<>();
    private MutableLiveData<String> usernameError = new MutableLiveData<>();
    private MutableLiveData<String> mobileNumberError = new MutableLiveData<>();
    private MutableLiveData<String> mailError = new MutableLiveData<>();
    private MutableLiveData<String> passwordError = new MutableLiveData<>();
    private MutableLiveData<String> confirmPasswordError = new MutableLiveData<>();

    public RegisterViewModel() {
        registerUseCase = Injection.getRegisterUseCase();
    }

    public void register(String username, String mail, String mobile, String password, String confirmPassword) {
        if (validate(username, mail, mobile, password, confirmPassword)) {
            Author author = new Author(username, mail, mobile, password);
            registerUseCase.execute(author, success);
        }
    }

    private boolean validate(String username, String mail, String mobile, String password, String confirmPassword) {
        boolean isValid = true;
        if (username == null || username.isEmpty()) {
            usernameError.setValue("Required");
            isValid = false;
        }
        if (mobile == null || mobile.isEmpty()) {
            mobileNumberError.setValue("Required");
            isValid = false;
        }
        if (!matchesRegex(EG_REGEX, mobile)) {
            mobileNumberError.setValue("Incorrect mobile number");
            isValid = false;
        }
        if (mail == null || mail.isEmpty()) {
            mailError.setValue("Required");
            isValid = false;
        }
        if (!isEmailValid(mail)) {
            mailError.setValue("Incorrect email format");
            isValid = false;
        }
        if (password == null || password.isEmpty()) {
            passwordError.setValue("Required");
            isValid = false;
        } else if (password.length() < 8) {
            passwordError.setValue("Password must be 8 characters or more");
            isValid = false;
        } else if (confirmPassword == null || confirmPassword.isEmpty()) {
            confirmPasswordError.setValue("Required");
            isValid = false;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordError.setValue("doesn't match password");
            isValid = false;
        }
        return isValid;
    }

    public boolean matchesRegex(String regex, String textToCompare) {
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(textToCompare).matches();
    }

    public boolean isEmailValid(String email) {
        boolean isValid = false;

        // String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        String expression = "(?!^[.+&'_-]*@.*$)(^[_\\w\\d+&'-]+(\\.[_\\w\\d+&'-]*)*@[\\w\\d-]+(\\.[\\w\\d-]+)*\\.(([\\d]{1,3})|([\\w]{2,}))$)";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    public MutableLiveData<Boolean> isRegisterSucceeded() {
        return success;
    }

    public MutableLiveData<String> getUsernameError() {
        return usernameError;
    }

    public MutableLiveData<String> getMobileNumberError() {
        return mobileNumberError;
    }

    public MutableLiveData<String> getMailError() {
        return mailError;
    }

    public MutableLiveData<String> getPasswordError() {
        return passwordError;
    }

    public MutableLiveData<String> getConfirmPasswordError() {
        return confirmPasswordError;
    }
}
