package com.app.medicineauthourity.presentation.author;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.app.medicineauthourity.Injection;
import com.app.medicineauthourity.R;
import com.app.medicineauthourity.presentation.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthorControlActivity extends AppCompatActivity {

    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_author_control);
        ButterKnife.bind(this);
        sharedPref = Injection.getSharedPreferences(this);
    }

    @OnClick(R.id.addCategoryView)
    public void onAddCategoryClicked() {
        startActivity(new Intent(this, AddCategoryActivity.class));
    }

    @OnClick(R.id.addMedicineView)
    public void onAddMedicineView() {
        startActivity(new Intent(this, AddMedicineActivity.class));
    }

    @OnClick(R.id.approvalView)
    public void onApprovalClicked() {
        startActivity(new Intent(this, ApprovalActivity.class));
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.btnLogout)
    public void onLogoutClicked() {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, StartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);
    }
}