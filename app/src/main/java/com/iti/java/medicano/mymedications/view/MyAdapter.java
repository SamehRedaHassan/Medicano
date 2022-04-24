package com.iti.java.medicano.mymedications.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.homescreen.model.MedicationHome;
import com.iti.java.medicano.model.Medication;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {
    List<Medication> data;
    Context context;

    public MyAdapter(Context context , List<Medication> data){
        this.data = data;
        this.context = context;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView medicationName;
        TextView medication_dose;
        TextView medication_instructions;
        ImageView medication_shape_image_view;
        View row;
        public myViewHolder(@NonNull View itemView ) {
            super(itemView);
            row = itemView;
            medicationName = row.findViewById(R.id.text_view_medication_name);
            medication_dose = row.findViewById(R.id.text_view_medication_dose);
            medication_instructions = row.findViewById(R.id.text_view_medication_instructions);
            medication_shape_image_view = row.findViewById(R.id.image_view_medication_shape);
        }
    }


    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MyAdapter myAdapter = new MyAdapter(context ,data);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.medication_section_row, parent, false);
        myViewHolder viewHolder = myAdapter.new myViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        Medication med = data.get(position);
        holder.medicationName.setText(med.getName());
        holder.medication_shape_image_view.setImageResource(med.getIcon());
        holder.medication_dose.setText(""+med.getStrengthValue()+ " | " + med.getStrengthType()  );
        holder.medication_instructions.setText(med.getInstruction());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
