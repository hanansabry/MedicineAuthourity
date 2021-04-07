package com.app.medicineauthourity;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomDialog extends Dialog {

    private DialogListener dialogListener;
    private ValueType valueType;
    private Context context;
    @BindView(R.id.valueEditText)
    EditText valueEditText;
    @BindView(R.id.dialog_title)
    TextView dialog_title;

    public CustomDialog(@NonNull Context context, ValueType valueType, DialogListener dialogListener) {
        super(context);
        this.context = context;
        this.valueType = valueType;
        this.dialogListener = dialogListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        View view = View.inflate(getContext(), R.layout.add_value_dialog, null);
        setContentView(view);
        ButterKnife.bind(this, view);

        if (valueType == ValueType.CONFLICT) {
            dialog_title.setText("Add new conflict medicine");
        } else {
            dialog_title.setText("Add new contradictory disease");
        }
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);

    }

    @OnClick(R.id.btn_yes)
    public void onAddClicked() {
        String value = valueEditText.getText().toString();
        if (value.isEmpty()) {
            Toast.makeText(context, "You must add value", Toast.LENGTH_SHORT).show();
        } else {
            dialogListener.onAddClicked(value);
            dismiss();
        }
    }

    @OnClick(R.id.btn_no)
    public void onCancelClicked() {
        dismiss();
    }

    public interface DialogListener {
        void onAddClicked(String value);
    }

    public enum ValueType {
        CONFLICT, CONTRADICTORY
    }
}
