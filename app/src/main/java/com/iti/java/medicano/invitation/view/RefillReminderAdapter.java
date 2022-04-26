package com.iti.java.medicano.invitation.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.model.Medication;
import com.iti.java.medicano.model.RefillReminder;

import java.util.ArrayList;
import java.util.List;

public class RefillReminderAdapter extends RecyclerView.Adapter<RefillReminderAdapter.RefillViewHolder> {

    List<Medication> medicationsToRefill;
    Context context;

    public RefillReminderAdapter(Context context,List<Medication> medicationsToRefill){
        this.context = context;
        this.medicationsToRefill = medicationsToRefill;
    }
    @NonNull
    @Override
    public RefillReminderAdapter.RefillViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.refill_reminder_item, parent, false);
        RefillViewHolder viewHolder = new RefillViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RefillReminderAdapter.RefillViewHolder holder, int position) {
        holder.txtRefill.setText(medicationsToRefill.get(position).getName());
        holder.txtRefill2.setText(medicationsToRefill.get(position).getRefillReminder().currentNumOfPills+"");
    }

    @Override
    public int getItemCount() {
        return medicationsToRefill.size();
    }
    public class RefillViewHolder extends RecyclerView.ViewHolder {
        TextView txtRefill;
        TextView txtRefill2;

        public RefillViewHolder(@NonNull View itemView) {
            super(itemView);
            txtRefill = itemView.findViewById(R.id.txtRefill);
            txtRefill2 = itemView.findViewById(R.id.txtRefill2);
        }
    }
}
