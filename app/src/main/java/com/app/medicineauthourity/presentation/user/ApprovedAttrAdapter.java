package com.app.medicineauthourity.presentation.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.medicineauthourity.R;

import java.util.List;
import java.util.zip.Inflater;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ApprovedAttrAdapter extends RecyclerView.Adapter<ApprovedAttrAdapter.AttrViewHolder> {

    private List<String> approvedAttrList;

    public ApprovedAttrAdapter(List<String> approvedAttrList) {
        this.approvedAttrList = approvedAttrList;
    }

    @NonNull
    @Override
    public AttrViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.attr_layout, null);
        return new AttrViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttrViewHolder holder, int position) {
        String attr = approvedAttrList.get(position);
        holder.attrTextView.setText(attr);
    }

    @Override
    public int getItemCount() {
        return approvedAttrList.size();
    }

    class AttrViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.attrTextView)
        TextView attrTextView;

        public AttrViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
