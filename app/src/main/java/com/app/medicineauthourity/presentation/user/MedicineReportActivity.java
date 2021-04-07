package com.app.medicineauthourity.presentation.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveMedicinesViewModel;

import java.util.List;

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
    @BindView(R.id.approvalAttrsTextView)
    TextView approvalAttrsTextView;
    @BindView(R.id.attrsRecyclerView)
    RecyclerView attrsRecyclerView;
    @BindView(R.id.conflictRecyclerView)
    RecyclerView conflictRecyclerView;
    @BindView(R.id.contradictoryRecyclerView)
    RecyclerView contradictoryRecyclerView;
    @BindView(R.id.conflictTextView)
    TextView conflictTextView;
    @BindView(R.id.contradictoryTextView)
    TextView contradictoryTextView;
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

                setupAttrListRecyclerView(medicine.getAttrs());
                setupConflictMedicinesRecyclerView(medicine.getConflict_medicines());
                setupContradictoryDiseasesRecyclerView(medicine.getContradictory_diseases());
            } else {
                Toast.makeText(MedicineReportActivity.this, "Can't retrieve medicine", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupAttrListRecyclerView(List<String> attrs) {
        if (attrs != null && attrs.size() > 0) {
            approvalAttrsTextView.setVisibility(View.VISIBLE);
            attrsRecyclerView.setVisibility(View.VISIBLE);
            StringsListAdapter adapter = new StringsListAdapter(attrs);
            attrsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            attrsRecyclerView.setAdapter(adapter);
        } else {
            approvalAttrsTextView.setVisibility(View.GONE);
            attrsRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setupConflictMedicinesRecyclerView(List<String> conflictMedicinesList) {
        if (conflictMedicinesList != null && conflictMedicinesList.size() > 0) {
            conflictTextView.setVisibility(View.VISIBLE);
            conflictRecyclerView.setVisibility(View.VISIBLE);
            StringsListAdapter adapter = new StringsListAdapter(conflictMedicinesList);
            conflictRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            conflictRecyclerView.setAdapter(adapter);
        } else {
            conflictTextView.setVisibility(View.GONE);
            conflictRecyclerView.setVisibility(View.GONE);
        }
    }

    private void setupContradictoryDiseasesRecyclerView(List<String> contradictoryDiseasesList) {
        if (contradictoryDiseasesList != null && contradictoryDiseasesList.size() > 0) {
            contradictoryTextView.setVisibility(View.VISIBLE);
            contradictoryRecyclerView.setVisibility(View.VISIBLE);
            StringsListAdapter adapter = new StringsListAdapter(contradictoryDiseasesList);
            contradictoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            contradictoryRecyclerView.setAdapter(adapter);
        } else {
            contradictoryTextView.setVisibility(View.GONE);
            contradictoryRecyclerView.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}