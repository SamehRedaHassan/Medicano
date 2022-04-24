package com.iti.java.medicano.homescreen.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.homescreen.model.MedicationHome;

import java.util.List;

public class HomeNestedAdapter extends RecyclerView.Adapter<HomeNestedAdapter.ViewHolder> {

    private Context context;
    private List<MedicationHome> medicationHomes;

    public HomeNestedAdapter(Context contx,List<MedicationHome> meds){
        context = contx;
        medicationHomes = meds;
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
        MedicationHome med = medicationHomes.get(position);
        holder.imgMed.setImageResource(med.getMedImg());
        holder.txtMedName.setText(med.getMedName());
        holder.txtMedDesc.setText(med.getMedDesc());
        //holder.constraintLayout.setBackground();

        Dialog detailsDialog = new Dialog(context);

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsDialog.setContentView(R.layout.medication_dialog_details);
                detailsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageView mediImeg = detailsDialog.findViewById(R.id.mediIconDetails);
                TextView mediName = detailsDialog.findViewById(R.id.mediNameDetails);
                TextView nextDate = detailsDialog.findViewById(R.id.nextDate);
                TextView doseAndSize = detailsDialog.findViewById(R.id.doseAndSize);

                mediImeg.setImageResource(med.getMedImg());
                mediName.setText(med.getMedName());
                nextDate.setText("Scheduled for "+med.getCurrentReminder());
                doseAndSize.setText(med.getMedDesc());

                detailsDialog.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return medicationHomes.size();
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
