package com.app.medicineauthourity.presentation;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.R;
import com.app.medicineauthourity.data.author.AuthorRepository;
import com.app.medicineauthourity.presentation.author.AuthorControlActivity;
import com.app.medicineauthourity.presentation.author.LoginActivity;

public class StartActivity extends AppCompatActivity {

    private static final String PREFS_NAME = "general";
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
        sharedPref = Injection.getSharedPreferences(this);
    }

    @OnClick(R.id.btnStart)
    public void onStartClicked() {
        startActivity(new Intent(this, BarcodeScanningActivity.class));
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        boolean isLogged = sharedPref.getBoolean(LoginActivity.LOGGED_PREF, false);
        if (!isLogged) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startActivity(new Intent(this, AuthorControlActivity.class));
        }
    }
}