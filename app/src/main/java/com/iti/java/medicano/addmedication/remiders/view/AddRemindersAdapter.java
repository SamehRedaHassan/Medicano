package com.iti.java.medicano.addmedication.remiders.view;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.iti.java.medicano.databinding.ItemNewReminderBinding;
import com.iti.java.medicano.model.Reminder;

import java.util.List;

public class AddRemindersAdapter extends RecyclerView.Adapter<AddRemindersAdapter.MyViewHolder> {


    private final List<Reminder> reminders;

    public AddRemindersAdapter(List<Reminder> reminders) {
        this.reminders = reminders;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNewReminderBinding binding = ItemNewReminderBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new MyViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Reminder currentReminder = reminders.get(position);
        holder.binding.textViewReminderTime.setText(currentReminder.hours+":"+currentReminder.minutes);
        holder.binding.btnDelete.setOnClickListener((v)->{
            reminders.remove(position);
            notifyItemChanged(position);
        });
    }

    @Override
    public int getItemCount() {
        return reminders.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private final ItemNewReminderBinding binding;
        public MyViewHolder(@NonNull ItemNewReminderBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
