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
import android.widget.Spinner;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.data.model.Medicine;
import com.app.medicineauthourity.presentation.viewmodels.AddMedicineViewModel;
import com.app.medicineauthourity.presentation.viewmodels.RetrieveMedicinesViewModel;

import java.util.ArrayList;
import java.util.List;

public class ApprovalActivity extends AppCompatActivity {

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
        retrieveMedicinesViewModel.getMedicines().observe(this, medicines -> {
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
        selectedGenericMedicine.setAttrs(checkedAttrs);
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
            checkedAttrs.add(Medicine.ApproveAttr.Attr1.getContent());
        } else {
            checkedAttrs.remove(Medicine.ApproveAttr.Attr1.getContent());
        }
    }

    @OnCheckedChanged(R.id.attr2Checkbox)
    public void onAttr2Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(Medicine.ApproveAttr.Attr2.getContent());
        } else {
            checkedAttrs.remove(Medicine.ApproveAttr.Attr2.getContent());
        }
    }

    @OnCheckedChanged(R.id.attr3Checkbox)
    public void onAttr3Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(Medicine.ApproveAttr.Attr3.getContent());
        } else {
            checkedAttrs.remove(Medicine.ApproveAttr.Attr3.getContent());
        }
    }

    @OnCheckedChanged(R.id.attr4Checkbox)
    public void onAttr4Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(Medicine.ApproveAttr.Attr4.getContent());
        } else {
            checkedAttrs.remove(Medicine.ApproveAttr.Attr4.getContent());
        }
    }

    @OnCheckedChanged(R.id.attr5Checkbox)
    public void onAttr5Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(Medicine.ApproveAttr.Attr5.getContent());
        } else {
            checkedAttrs.remove(Medicine.ApproveAttr.Attr5.getContent());
        }
    }

    @OnCheckedChanged(R.id.attr6Checkbox)
    public void onAttr6Checked(boolean checked) {
        if (checked) {
            checkedAttrs.add(Medicine.ApproveAttr.Attr6.getContent());
        } else {
            checkedAttrs.remove(Medicine.ApproveAttr.Attr6.getContent());
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}