package com.app.medicineauthourity.presentation.author;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.medicineauthourity.R;

public class ApproveResultActivity extends AppCompatActivity {

    @BindView(R.id.approvedIcon)
    ImageView approvedIcon;
    @BindView(R.id.rejectedIcon)
    ImageView rejectedIcon;
    @BindView(R.id.approveResultTextView)
    TextView approveResultTextView;
    @BindView(R.id.medNameTextView)
    TextView medNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approve_result);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            String medicineName = intent.getStringExtra("med_name");
            boolean approved = intent.getBooleanExtra("approved", false);
            medNameTextView.setText(medicineName);
            if (approved) {
                approvedIcon.setVisibility(View.VISIBLE);
                rejectedIcon.setVisibility(View.GONE);
                approveResultTextView.setText("Approved by authority of health");
            } else {
                rejectedIcon.setVisibility(View.VISIBLE);
                approvedIcon.setVisibility(View.GONE);
                approveResultTextView.setText("Non Authorized");
            }
        }
    }

    @OnClick(R.id.btnBack)
    public void onBackClicked() {
        onBackPressed();
    }
}