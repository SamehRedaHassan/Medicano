package com.iti.java.medicano.homescreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.homescreen.model.Medication;

import java.util.List;

public class HomeNestedAdapter extends RecyclerView.Adapter<HomeNestedAdapter.ViewHolder> {

    private Context context;
    private List<Medication> medications;

    public HomeNestedAdapter(Context contx,List<Medication> meds){
        context = contx;
        medications = meds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.home_nested_recyclerview_item,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Medication med = medications.get(position);
        holder.imgMed.setImageResource(R.drawable.capsule);
        holder.txtMedName.setText(med.getMedName());
        holder.txtMedDesc.setText(med.getMedDesc());
    }

    @Override
    public int getItemCount() {
        return medications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout constraintLayout;
        private ImageView imgMed;
        private TextView txtMedName;
        private TextView txtMedDesc;

        public ViewHolder(View v){
            super(v);
            constraintLayout = v.findViewById(R.id.singleMedConstraint);
            imgMed = v.findViewById(R.id.imgMedication);
            txtMedName = v.findViewById(R.id.txtMedName);
            txtMedDesc = v.findViewById(R.id.txtMedDesc);
        }

    }
}
