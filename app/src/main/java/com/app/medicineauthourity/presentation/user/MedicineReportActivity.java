package com.app.medicineauthourity.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveMedicinesViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MedicineReportActivity extends AppCompatActivity {

    @BindView(R.id.medicineNameTextView)
    TextView medicineNameTextView;
    @BindView(R.id.categoryNameTextView)
    TextView categoryNameTextView;
    @BindView(R.id.compositionValueTextView)
    TextView compositionValueTextView;
    @BindView(R.id.usageValueTextView)
    TextView usageValueTextView;
    @BindView(R.id.physilogicalValueTextView)
    TextView physilogicalValueTextView;
    @BindView(R.id.productionValueTextView)
    TextView productionValueTextView;
    @BindView(R.id.statusTextView)
    TextView statusTextView;
    @BindView(R.id.attrsRecyclerView)
    RecyclerView attrsRecyclerView;
    private String medId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_report);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            medId = intent.getStringExtra("med_id");
            Toast.makeText(this, medId, Toast.LENGTH_SHORT).show();
        }

        RetrieveMedicinesViewModel retrieveMedicinesViewModel = ViewModelProviders.of(this).get(RetrieveMedicinesViewModel.class);
        retrieveMedicinesViewModel.retrieveMedicineById(medId);
        retrieveMedicinesViewModel.getMedicine().observe(this, medicine -> {
            if (medicine != null) {
                medicineNameTextView.setText(medicine.getName());
                categoryNameTextView.setText(medicine.getCategoryName());
                compositionValueTextView.setText(medicine.getComposition());
                usageValueTextView.setText(medicine.getUsage());
                physilogicalValueTextView.setText(medicine.getPhysiological());
                productionValueTextView.setText(medicine.getProduction());
                statusTextView.setText(medicine.isApproved() ? "Approved" : "Not Authorized");

                setupAttrListRecyclerView(medicine);
            } else {
                Toast.makeText(MedicineReportActivity.this, "Can't retrieve medicine", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAttrListRecyclerView(Medicine medicine) {
        StringsListAdapter adapter = new StringsListAdapter(medicine.getAttrs());
        attrsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        attrsRecyclerView.setAdapter(adapter);
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}