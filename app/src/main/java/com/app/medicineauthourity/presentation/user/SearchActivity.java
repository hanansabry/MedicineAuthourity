package com.app.medicineauthourity.presentation.user;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.data.model.Category;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.presentation.BarcodeScanningActivity;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveCategoriesViewModel;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveMedicinesViewModel;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    @BindView(R.id.categoriesSpinner)
    Spinner categoriesSpinner;
    @BindView(R.id.medicineSpinner)
    Spinner medicineSpinner;

    private RetrieveCategoriesViewModel retrieveCategoriesViewModel;
    private RetrieveMedicinesViewModel retrieveMedicinesViewModel;
    private List<Category> categories;
    private Category selectedCategory;
    private List<Medicine> medicines;
    private Medicine selectedMedicine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);

        retrieveCategoriesViewModel = ViewModelProviders.of(this).get(RetrieveCategoriesViewModel.class);
        retrieveMedicinesViewModel = ViewModelProviders.of(this).get(RetrieveMedicinesViewModel.class);

        initCategoriesSpinner();
    }

    private void initCategoriesSpinner() {
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        retrieveCategoriesViewModel.retrieveCategories();
        retrieveCategoriesViewModel.getCategories().observe(this, categories -> {
            if (categories == null || categories.isEmpty()) {
                Toast.makeText(this, "Empty categories list", Toast.LENGTH_SHORT).show();
            } else {
                this.categories = categories;
                ArrayList<String> categoriesNames = new ArrayList<>();
                categoriesNames.add("Select category");
                for (Category category : categories) {
                    categoriesNames.add(category.getName());
                }
                categoriesAdapter.clear();
                categoriesAdapter.addAll(categoriesNames);
                categoriesSpinner.setAdapter(categoriesAdapter);
            }
        });
        categoriesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedCategory = categories.get(position - 1);
                    initMedicinesSpinner(selectedCategory.getId());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initMedicinesSpinner(String catId) {
        ArrayAdapter<String> medicinesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        retrieveMedicinesViewModel.getMedicinesByCategory(catId);
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
                medicinesAdapter.clear();
                medicinesAdapter.addAll(medicinesNames);
                medicineSpinner.setAdapter(medicinesAdapter);
            }
        });

        medicineSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedMedicine = medicines.get(position - 1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btnSearch)
    public void onSearchClicked() {
        if (selectedCategory == null || selectedMedicine == null) {
            Toast.makeText(this, "You must select category and medicine", Toast.LENGTH_SHORT).show();
            return;
        }
        goToMedicineReportScreen(selectedMedicine);
    }

    @OnClick({R.id.scanButtonLayout, R.id.btnScan})
    public void onScanClicked() {
        startActivityForResult(new Intent(this, BarcodeScanningActivity.class), BarcodeScanningActivity.BARCODE_REQUEST_CODE);
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BarcodeScanningActivity.BARCODE_REQUEST_CODE) {
                if (data != null) {
                    String barcode = data.getStringExtra(BarcodeScanningActivity.BARCODE);
                    retrieveMedicinesViewModel.retrieveMedicineByCode(barcode);
                    retrieveMedicinesViewModel.getMedicine().observe(this, this::goToMedicineReportScreen);
                }
            }
        }
    }

    private void goToMedicineReportScreen(Medicine medicine) {
        Intent intent = new Intent(SearchActivity.this, MedicineReportActivity.class);
        intent.putExtra("med_id", medicine.getId());
        startActivity(intent);
        retrieveMedicinesViewModel.getMedicine().removeObservers(SearchActivity.this);
    }
}