package com.app.medicineauthourity.presentation.author;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.data.model.Category;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.presentation.BarcodeScanningActivity;
import com.app.medicineauthourity.presentation.viewmodels.AddMedicineViewModel;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveCategoriesViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AddMedicineActivity extends AppCompatActivity {

    private static final int BARCODE_REQUEST_CODE = 101;

    @BindView(R.id.textInputMedName)
    TextInputLayout textInputMedName;
    @BindView(R.id.editTextMedName)
    TextInputEditText editTextMedName;

    @BindView(R.id.editTextBarCode)
    TextInputEditText editTextBarCode;

    @BindView(R.id.categoriesSpinner)
    Spinner categoriesSpinner;
    @BindView(R.id.compositionRadioGroup)
    RadioGroup compositionRadioGroup;
    @BindView(R.id.usageRadioGroup)
    RadioGroup usageRadioGroup;
    @BindView(R.id.physilogicalRadioGroup)
    RadioGroup physilogicalRadioGroup;
    @BindView(R.id.productionRadioGroup)
    RadioGroup productionRadioGroup;
    private AddMedicineViewModel addMedicineViewModel;
    private RetrieveCategoriesViewModel retrieveCategoriesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_medicine);
        ButterKnife.bind(this);

        addMedicineViewModel = ViewModelProviders.of(this).get(AddMedicineViewModel.class);
        retrieveCategoriesViewModel = ViewModelProviders.of(this).get(RetrieveCategoriesViewModel.class);

        addMedicineViewModel.getSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(AddMedicineActivity.this, "New medicine is added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddMedicineActivity.this, "Can't add new medicine, please try again", Toast.LENGTH_SHORT).show();
            }
        });
        addMedicineViewModel.getMedNameError().observe(this, error -> {
            textInputMedName.setError(error);
        });
        addMedicineViewModel.getGlobalError().observe(this, error -> {
            Toast.makeText(AddMedicineActivity.this, error, Toast.LENGTH_SHORT).show();
        });

        initCategoriesSpinner();
    }

    private void initCategoriesSpinner() {
        ArrayAdapter<String> categoriesAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        retrieveCategoriesViewModel.retrieveCategories();
        retrieveCategoriesViewModel.getCategories().observe(this, categories -> {
            if (categories == null || categories.isEmpty()) {
                Toast.makeText(this, "Empty categories list", Toast.LENGTH_SHORT).show();
            } else {
                ArrayList<String> categoriesNames = new ArrayList<>();
                categoriesNames.add("Select category");
                for (Category category : categories) {
                    categoriesNames.add(category.getName());
                }
                categoriesAdapter.addAll(categoriesNames);
                categoriesSpinner.setAdapter(categoriesAdapter);
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void onAddClicked() {
        if (categoriesSpinner.getSelectedItemPosition() == 0) {
            Toast.makeText(this, "You must select disease category", Toast.LENGTH_SHORT).show();
            return;
        }
        String medName = editTextMedName.getText().toString().trim();
        String barQRCode = editTextBarCode.getText().toString().trim();
        String catName = (String) categoriesSpinner.getSelectedItem() ;
        String composition = compositionRadioGroup.getCheckedRadioButtonId() == R.id.simpleRadioValue ?
                Medicine.Composition.Simple.name() : compositionRadioGroup.getCheckedRadioButtonId() == R.id.complexRadioValue ?
                Medicine.Composition.Complex.name() : null;

        String usage = usageRadioGroup.getCheckedRadioButtonId() == R.id.externalRadioValue ?
                Medicine.Usage.External.name() : usageRadioGroup.getCheckedRadioButtonId() == R.id.internalRadioValue ?
                Medicine.Usage.Internal.name() : null;

        String physiological = physilogicalRadioGroup.getCheckedRadioButtonId() == R.id.specialRadioValue ?
                Medicine.Physiological.Special.name() : physilogicalRadioGroup.getCheckedRadioButtonId() == R.id.generalRadioValue ?
                Medicine.Physiological.General.name() : physilogicalRadioGroup.getCheckedRadioButtonId() == R.id.prolongedRadioValue ?
                Medicine.Physiological.Prolonged.name() : null;

        String production = productionRadioGroup.getCheckedRadioButtonId() == R.id.originalRadioValue ?
                Medicine.Production.Original.name() : productionRadioGroup.getCheckedRadioButtonId() == R.id.licenseRadioValue ?
                Medicine.Production.License.name() : productionRadioGroup.getCheckedRadioButtonId() == R.id.genericRadioValue ?
                Medicine.Production.Generic.name() : null;
        addMedicineViewModel.addMedicine(medName, barQRCode, catName, composition, usage, physiological, production);
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }

    @OnClick(R.id.btnScan)
    public void onScanClicked() {
        startActivityForResult(new Intent(this, BarcodeScanningActivity.class), BARCODE_REQUEST_CODE);
    }

    @OnTextChanged(R.id.editTextMedName)
    public void removeMedicineNameError() {
        textInputMedName.setError(null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == BARCODE_REQUEST_CODE) {
                if (data != null) {
                    String barcode = data.getStringExtra(BarcodeScanningActivity.BARCODE);
                    editTextBarCode.setText(barcode);
                }
            }
        }
    }
}