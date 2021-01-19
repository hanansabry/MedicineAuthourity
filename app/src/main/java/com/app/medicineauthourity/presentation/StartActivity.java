package com.app.medicineauthourity.presentation;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.presentation.author.LoginActivity;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnStart)
    public void onStartClicked() {
        startActivity(new Intent(this, BarcodeScanningActivity.class));
    }

    @OnClick(R.id.btnLogin)
    public void onLoginClicked() {
        startActivity(new Intent(this, LoginActivity.class));
    }
}