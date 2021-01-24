package com.app.medicineauthourity.presentation.author;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.presentation.viewmodels.AddMedicineViewModel;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveMedicinesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ApprovalActivity extends AppCompatActivity {

    private static final String ATTR1 = "attr1";
    private static final String ATTR2 = "attr2";
    private static final String ATTR3 = "attr3";
    private static final String ATTR4 = "attr4";
    private static final String ATTR5 = "attr5";
    private static final String ATTR6 = "attr6";

    @BindView(R.id.genericSpinner)
    Spinner genericSpinner;

    private boolean approved;
    private ArrayList<String> checkedAttrs = new ArrayList<>();
    private Medicine selectedGenericMedicine;
    private List<Medicine> medicines;
    private Intent approvedIntent;
    private AddMedicineViewModel addMedicineViewModel;
    private RetrieveMedicinesViewModel retrieveMedicinesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        ButterKnife.bind(this);

        addMedicineViewModel = ViewModelProviders.of(this).get(AddMedicineViewModel.class);
        retrieveMedicinesViewModel = ViewModelProviders.of(this).get(RetrieveMedicinesViewModel.class);
        initGenericSpinner();
    }

    private void initGenericSpinner() {
        ArrayAdapter<String> genericAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item);
        retrieveMedicinesViewModel.retrieveGenericMedicines();
        retrieveMedicinesViewModel.getGenericMedicines().observe(this, medicines -> {
            if (medicines == null || medicines.isEmpty()) {
                Toast.makeText(this, "Empty generic list", Toast.LENGTH_SHORT).show();
            } else {
                this.medicines = medicines;
                ArrayList<String> medicinesNames = new ArrayList<>();
                medicinesNames.add("Select generic medicine");
                for (Medicine medicine : medicines) {
                    medicinesNames.add(medicine.getName());
                }
                genericAdapter.addAll(medicinesNames);
                genericSpinner.setAdapter(genericAdapter);
            }
        });

        genericSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    selectedGenericMedicine = medicines.get(position-1);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @OnClick(R.id.btnApprove)
    public void onApproveClicked() {
        if (selectedGenericMedicine != null) {
            approvedIntent = new Intent(this, ApproveResultActivity.class);
            approvedIntent.putExtra("med_name", selectedGenericMedicine.getName());
            if (checkedAttrs.size() == 6) {
                approvedIntent.putExtra("approved", true);
                selectedGenericMedicine.setApproved(true);
            } else {
                approvedIntent.putExtra("approved", false);
                selectedGenericMedicine.setApproved(false);
            }
            updateMedicineApprovedStatus();
        } else {
            Toast.makeText(this, "You must select generic medicine", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateMedicineApprovedStatus() {
        addMedicineViewModel.updateApprovedStatus(selectedGenericMedicine);
        ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Loading");
        pd.show();

        addMedicineViewModel.getUpdateStatus().observe(this, statusUpdated -> {
            pd.dismiss();
            if (statusUpdated) {
                startActivity(approvedIntent);
                addMedicineViewModel.getUpdateStatus().removeObservers(this);
            } else {
                Toast.makeText(ApprovalActivity.this, "Can't update medicine status, please try again later", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnCheckedChanged(R.id.attr1Checkbox)
    public void onAttr1Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(ATTR1);
        } else {
            checkedAttrs.remove(ATTR1);
        }
    }

    @OnCheckedChanged(R.id.attr2Checkbox)
    public void onAttr2Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(ATTR2);
        } else {
            checkedAttrs.remove(ATTR2);
        }
    }

    @OnCheckedChanged(R.id.attr3Checkbox)
    public void onAttr3Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(ATTR3);
        } else {
            checkedAttrs.remove(ATTR3);
        }
    }

    @OnCheckedChanged(R.id.attr4Checkbox)
    public void onAttr4Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(ATTR4);
        } else {
            checkedAttrs.remove(ATTR4);
        }
    }

    @OnCheckedChanged(R.id.attr5Checkbox)
    public void onAttr5Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(ATTR5);
        } else {
            checkedAttrs.remove(ATTR5);
        }
    }

    @OnCheckedChanged(R.id.attr6Checkbox)
    public void onAttr6Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(ATTR6);
        } else {
            checkedAttrs.remove(ATTR6);
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}