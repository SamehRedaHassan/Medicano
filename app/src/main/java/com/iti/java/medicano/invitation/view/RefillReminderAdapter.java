package com.iti.java.medicano.invitation.view;

import static com.iti.java.medicano.Constants.POSITION;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.RefillReminderItemBinding;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.RefillReminder;

import java.util.ArrayList;
import java.util.List;

public class RefillReminderAdapter extends RecyclerView.Adapter<RefillReminderAdapter.RefillViewHolder> {

    private final NavController navController;
    List<Medication> medicationsToRefill;

    public RefillReminderAdapter(NavController navController, List<Medication> medicationsToRefill) {
        this.medicationsToRefill = medicationsToRefill;
        this.navController = navController;
    }

    @NonNull
    @Override
    public RefillReminderAdapter.RefillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RefillReminderItemBinding refillReminderItemBinding = RefillReminderItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new RefillViewHolder(refillReminderItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull RefillReminderAdapter.RefillViewHolder holder, int position) {
        Medication medication = medicationsToRefill.get(position);
        holder.binding.txtRefill.setText(medication.getName());
        holder.binding.txtRefill2.setText(medication.getRefillReminder().currentNumOfPills + "");
        holder.binding.getRoot().setOnClickListener((v) -> {
            Bundle bundle = new Bundle();
            bundle.putInt(POSITION,position);
            navController.navigate(R.id.action_mainFragment_to_refillDialogFragment,bundle);
        });
    }

    @Override
    public int getItemCount() {
        return medicationsToRefill.size();
    }

    public class RefillViewHolder extends RecyclerView.ViewHolder {
        private final RefillReminderItemBinding binding;

        public RefillViewHolder(@NonNull RefillReminderItemBinding itemView) {
            super(itemView.getRoot());
            binding = itemView;
        }
    }
}
