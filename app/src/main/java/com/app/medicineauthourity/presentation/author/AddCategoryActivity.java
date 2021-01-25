package com.app.medicineauthourity.presentation.author;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

import android.os.Bundle;
import android.widget.Toast;

import com.app.medicineauthourity.R;
import com.app.medicineauthourity.presentation.viewmodels.AddCategoryViewModel;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class AddCategoryActivity extends AppCompatActivity {

    @BindView(R.id.textInputCatName)
    TextInputLayout textInputCatName;
    @BindView(R.id.editTextCatName)
    TextInputEditText editTextCatName;

    @BindView(R.id.textInputDesc)
    TextInputLayout textInputDesc;
    @BindView(R.id.editTextDesc)
    TextInputEditText editTextDesc;

    private AddCategoryViewModel addCategoryViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        ButterKnife.bind(this);

        addCategoryViewModel = ViewModelProviders.of(this).get(AddCategoryViewModel.class);
        addCategoryViewModel.getCategoryNameError().observe(this, error -> {
            textInputCatName.setError(error);
        });
        addCategoryViewModel.getDescError().observe(this, error -> {
            textInputDesc.setError(error);
        });
        addCategoryViewModel.getSuccess().observe(this, success -> {
            if (success) {
                Toast.makeText(AddCategoryActivity.this, "New category is added successfully", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(AddCategoryActivity.this, "Can't add new category, please try again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.btnAdd)
    public void onAddClicked() {
        String categoryName = Objects.requireNonNull(editTextCatName.getText()).toString().trim();
        String desc = Objects.requireNonNull(editTextDesc.getText()).toString().trim();
        addCategoryViewModel.addCategory(categoryName, desc);

    }

    @OnTextChanged(R.id.editTextCatName)
    public void removeCategoryNameError() {
        textInputCatName.setError(null);
    }

    @OnTextChanged(R.id.editTextDesc)
    public void removeDescError() {
        textInputDesc.setError(null);
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}