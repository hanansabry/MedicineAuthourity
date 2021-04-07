package com.app.medicineauthourity.presentation.author;

import android.animation.Animator;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.medicineauthourity.CustomDialog;
import com.app.medicineauthourity.R;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.presentation.user.StringsListAdapter;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveMedicinesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SelectMedicineActivity extends AppCompatActivity {

    @BindView(R.id.fabLayout1)
    LinearLayout fabLayout1;
    @BindView(R.id.fabLayout2)
    LinearLayout fabLayout2;
    @BindView(R.id.fab)
    FloatingActionButton mainFab;
    @BindView(R.id.conflictFab)
    FloatingActionButton fab1;
    @BindView(R.id.contradictoryFab)
    FloatingActionButton fab2;
    @BindView(R.id.fabBGLayout)
    View fabBGLayout;
    @BindView(R.id.medicinesSpinner)
    Spinner medicineSpinner;
    @BindView(R.id.conflictRecyclerView)
    RecyclerView conflictRecyclerView;
    @BindView(R.id.contradictoryRecyclerView)
    RecyclerView contradictoryRecyclerView;
    @BindView(R.id.conflictTextView)
    TextView conflictTextView;
    @BindView(R.id.contradictoryTextView)
    TextView contradictoryTextView;

    private RetrieveMedicinesViewModel retrieveMedicinesViewModel;
    private List<Medicine> medicines;
    private Medicine selectedMedicine;
    boolean isFABOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_medicine);
        ButterKnife.bind(this);

        retrieveMedicinesViewModel = ViewModelProviders.of(this).get(RetrieveMedicinesViewModel.class);

        initMedicinesSpinner();
        retrieveMedicinesViewModel.getConflictMedicinesLiveList()
                .observe(this, this::setupConflictMedicinesRecyclerView);
        retrieveMedicinesViewModel.getContradictoryDiseasesLiveList()
                .observe(this, this::setupContradictoryDiseasesRecyclerView);
        retrieveMedicinesViewModel.getAddConflictSuccess().observe(this, success -> {
            Toast.makeText(SelectMedicineActivity.this, "Conflict medicine is added successfully", Toast.LENGTH_SHORT).show();
        });
        retrieveMedicinesViewModel.getAddContradictorySuccess().observe(this, success -> {
            Toast.makeText(SelectMedicineActivity.this, "Contradictory disease is added successfully", Toast.LENGTH_SHORT).show();
        });
    }

    private void initMedicinesSpinner() {
        ArrayAdapter<String> medicinesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        retrieveMedicinesViewModel.retrieveAllMedicines();
        retrieveMedicinesViewModel.getMedicines().observe(this, medicines -> {
            if (medicines == null || medicines.isEmpty()) {
                Toast.makeText(this, "Empty medicines list", Toast.LENGTH_SHORT).show();
            } else {
                this.medicines = medicines;
                ArrayList<String> medicinesNames = new ArrayList<>();
                medicinesNames.add("Select medicine");
                for (Medicine medicine : medicines) {
                    medicinesNames.add(medicine.getName());
                }
                medicinesAdapter.addAll(medicinesNames);
                medicineSpinner.setAdapter(medicinesAdapter);
            }
        });

        medicineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedMedicine = medicines.get(position - 1);
                    retrieveMedicinesViewModel.getConflictMedicines(selectedMedicine);
                    retrieveMedicinesViewModel.getContradictoryDiseases(selectedMedicine);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    @OnClick(R.id.fab)
    public void onMainFabClicked() {
        if (!isFABOpen) {
            showFABMenu();
        } else {
            closeFABMenu();
        }
    }

    @OnClick(R.id.fabBGLayout)
    public void onFabBGLayoutClicked() {
        closeFABMenu();
    }

    @OnClick(R.id.conflictFab)
    public void onConflictFabClicked() {
        if (selectedMedicine == null){
            Toast.makeText(this, "You must select medicine to add conflict medicine", Toast.LENGTH_SHORT).show();
        } else {
            CustomDialog customDialog = new CustomDialog(this, CustomDialog.ValueType.CONFLICT, value -> {
                retrieveMedicinesViewModel.addConflictMedicine(selectedMedicine, value);
            });
            customDialog.show();
        }
    }

    @OnClick(R.id.contradictoryFab)
    public void onContradictoryFabClicked() {
        if (selectedMedicine == null) {
            Toast.makeText(this, "You must select medicine to add contradictory disease", Toast.LENGTH_SHORT).show();
        } else {
            CustomDialog customDialog = new CustomDialog(this, CustomDialog.ValueType.CONTRADICTORY, value -> {
                retrieveMedicinesViewModel.addContradictoryDisease(selectedMedicine, value);
            });
            customDialog.show();
        }
    }

    private void showFABMenu() {
        isFABOpen = true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);
        mainFab.animate().rotationBy(180);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFABMenu() {
        isFABOpen = false;
        fabBGLayout.setVisibility(View.GONE);
        mainFab.animate().rotation(0);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (!isFABOpen) {
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (isFABOpen) {
            closeFABMenu();
        } else {
            super.onBackPressed();
        }
    }

}