package com.iti.java.medicano.homescreen.view;

import android.content.Context;
import android.os.Build;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iti.java.medicano.R;
import com.iti.java.medicano.homescreen.model.MedicationHome;
import com.iti.java.medicano.homescreen.model.MedicationList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder>{

    private final Date date;
    private Context context;
    private TreeMap<String,List<MedicationHome>> medicationsList;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private ArrayList<String> medicationArray;

    public HomeAdapter(Context context, Date date){
        this.context = context;
        medicationsList = new TreeMap<>();
        medicationArray = new ArrayList<>();
        this.date = date;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setMedicationArray(HashMap<String,List<MedicationHome>> medsList){

        medicationArray.clear();
        medicationsList =new TreeMap<>(medsList);
        medicationArray.addAll(medicationsList.keySet());
        List<Pair<Integer,String>> tempArray = new ArrayList<>();
        for (String i:medicationArray){
            String[] HM = i.split(":");
            int h = Integer.parseInt(HM[0]);
            int m = Integer.parseInt(HM[1]);
            int totalTime = h*60+m;
            Pair<Integer,String> pair = new Pair<>(totalTime,i);
            tempArray.add(pair);
        }
        Collections.sort(tempArray, new Comparator<Pair<Integer, String>>() {
            @Override
            public int compare(Pair<Integer, String> pair1, Pair<Integer, String> pair2) {
                return pair1.first.compareTo(pair2.first);
            }
        });
        medicationArray.clear();
        for (Pair<Integer, String> i : tempArray) {
            medicationArray.add(i.second);
        }
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
        holder.txtTime.setText(medicationArray.get(position));
        List<MedicationHome> medList = medicationsList.get(medicationArray.get(position));

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                holder.recyclerView.getContext(),
                LinearLayoutManager.VERTICAL,
                false);

        layoutManager.setInitialPrefetchItemCount(medList.size());

        HomeNestedAdapter nestedAdapter = new HomeNestedAdapter(this.context,medList,date);

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
