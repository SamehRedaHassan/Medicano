package com.iti.java.medicano.mymedications.view;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.model.Medication;

import java.util.List;

public class MedicationsAdapter extends RecyclerView.Adapter<MedicationsAdapter.myViewHolder>{
    Context context;
    List<MedicationSection> medicationSectionList;

    public MedicationsAdapter(Context context , List<MedicationSection>  data){
        this.context = context;
        medicationSectionList = data;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        RecyclerView sectionRecyclerView;
        View row;
        public myViewHolder(@NonNull View itemView ) {
            super(itemView);
            row = itemView;
            sectionRecyclerView = row.findViewById(R.id.oneSectionRecyclerViewId);
        }
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MedicationsAdapter myAdapter = new MedicationsAdapter(context , medicationSectionList);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medication_row, parent, false);
        MedicationsAdapter.myViewHolder viewHolder = myAdapter.new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        MedicationSection medicationSection = medicationSectionList.get(position);
        List<Medication> items = medicationSection.getSectionItems();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        holder.sectionRecyclerView.setLayoutManager(linearLayoutManager);
        MyAdapter myAdapter = new MyAdapter(context , items);
        holder.sectionRecyclerView.setAdapter(myAdapter);

    }

    @Override
    public int getItemCount() {
        return medicationSectionList.size();
    }
}
