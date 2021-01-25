package com.app.medicineauthourity.presentation.author;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.presentation.BarcodeScanningActivity;
import com.app.medicineauthourity.presentation.viewmodels.LoginViewModel;
import com.app.medicineauthourity.presentation.viewmodels.RegisterViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.textInputUsername)
    TextInputLayout textInputUsername;
    @BindView(R.id.editTextUsername)
    TextInputEditText editTextUsername;

    @BindView(R.id.textInputMobile)
    TextInputLayout textInputMobile;
    @BindView(R.id.editTextMobile)
    TextInputEditText editTextMobile;

    @BindView(R.id.textInputEmail)
    TextInputLayout textInputMail;
    @BindView(R.id.editTextEmail)
    TextInputEditText editTextMail;

    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;
    @BindView(R.id.editTextPassword)
    TextInputEditText editTextPassword;

    @BindView(R.id.textInputConfirm)
    TextInputLayout textInputConfirm;
    @BindView(R.id.editTextConfirm)
    TextInputEditText editTextConfirm;

    private RegisterViewModel registerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);

        registerViewModel = ViewModelProviders.of(this).get(RegisterViewModel.class);
        registerViewModel.getUsernameError().observe(this, error -> {
            textInputUsername.setError(error);
        });
        registerViewModel.getMailError().observe(this, error -> {
            textInputMail.setError(error);
        });
        registerViewModel.getMobileNumberError().observe(this, error -> {
            textInputMobile.setError(error);
        });
        registerViewModel.getPasswordError().observe(this, error -> {
            textInputPassword.setError(error);
        });
        registerViewModel.getConfirmPasswordError().observe(this, error -> {
            textInputConfirm.setError(error);
        });
        registerViewModel.isRegisterSucceeded().observe(this, isSuccess -> {
            if (isSuccess) {
                startActivity(new Intent(RegisterActivity.this, AuthorControlActivity.class));
            } else {
                Toast.makeText(RegisterActivity.this, "Can't create account, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnRegister)
    public void onRegisterClicked() {
        String username = Objects.requireNonNull(editTextUsername.getText()).toString().trim();
        String mobile = Objects.requireNonNull(editTextMobile.getText()).toString().trim();
        String mail = Objects.requireNonNull(editTextMail.getText()).toString().trim();
        String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();
        String confirm = Objects.requireNonNull(editTextConfirm.getText()).toString().trim();

        registerViewModel.register(username, mail, mobile, password, confirm);
    }

    @OnTextChanged(R.id.editTextUsername)
    public void removeUsernameError() {
        textInputUsername.setError(null);
    }

    @OnTextChanged(R.id.editTextMobile)
    public void removeMobileNumberError() {
        textInputMobile.setError(null);
    }

    @OnTextChanged(R.id.editTextEmail)
    public void removeMailError() {
        textInputMail.setError(null);
    }

    @OnTextChanged(R.id.editTextPassword)
    public void removePasswordError() {
        textInputPassword.setError(null);
    }

    @OnTextChanged(R.id.editTextConfirm)
    public void removePasswordConfirmError() {
        textInputConfirm.setError(null);
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }


}