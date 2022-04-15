package com.iti.java.medicano.homescreen.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.homescreen.model.MedicationList;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    private Context context;
    private List<MedicationList> medicationsList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();

    public HomeAdapter(Context context,List<MedicationList> medsList){
        this.context = context;
        this.medicationsList = medsList;
    }

    @NonNull
    @Override
    public HomeAdapter.HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.home_recyclerview_item,parent,false);
        HomeViewHolder vh = new HomeViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.HomeViewHolder holder, int position) {
        MedicationList medList = medicationsList.get(position);
        holder.txtTime.setText(medList.getTime());

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerView.getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        layoutManager.setInitialPrefetchItemCount(medList.getMedications().size());

        HomeNestedAdapter nestedAdapter = new HomeNestedAdapter(this.context,medList.getMedications());

        holder.recyclerView.setLayoutManager(layoutManager);
        holder.recyclerView.setAdapter(nestedAdapter);
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return medicationsList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTime;
        private RecyclerView recyclerView;

        public HomeViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTime = itemView.findViewById(R.id.txtTime);
            recyclerView = itemView.findViewById(R.id.homeNestedRecycler);
        }
    }
}
