package com.app.medicineauthourity.presentation.author;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.R;
import com.app.medicineauthourity.domain.author.usecases.LoginUseCase;
import com.app.medicineauthourity.presentation.BarcodeScanningActivity;
import com.app.medicineauthourity.presentation.viewmodels.LoginViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.textInputMail)
    TextInputLayout textInputMail;
    @BindView(R.id.editTextMail)
    TextInputEditText editTextMail;
    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;
    @BindView(R.id.editTextPassword)
    TextInputEditText editTextPassword;

    private LoginViewModel loginViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getUsernameError().observe(this, error -> {
            textInputMail.setError(error);
        });
        loginViewModel.getPasswordError().observe(this, error -> {
            textInputPassword.setError(error);
        });
        loginViewModel.isLoginSucceeded().observe(this, isSuccess -> {
            if (isSuccess) {
                startActivity(new Intent(LoginActivity.this, BarcodeScanningActivity.class));
            } else {
                Toast.makeText(LoginActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnTextChanged(R.id.editTextMail)
    public void removeEmailError() {
        textInputMail.setError(null);
    }

    @OnTextChanged(R.id.editTextPassword)
    public void removePasswordError() {
        textInputPassword.setError(null);
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        String email = Objects.requireNonNull(editTextMail.getText()).toString().trim();
        String password = Objects.requireNonNull(editTextPassword.getText()).toString().trim();
        loginViewModel.login(email, password);
    }

    @OnClick(R.id.btnSignUp)
    public void onSignUpClicked() {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}