package com.iti.java.medicano;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.iti.java.medicano.databinding.FragmentMyMedicationsBinding;

import java.util.ArrayList;
import java.util.List;

public class MyMedicationsFragment extends Fragment {

    private FragmentMyMedicationsBinding binding;

    List<Section> sections;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyMedicationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //Your RecyclerView
        MainRecyclerAdapter mainRecyclerAdapter = new MainRecyclerAdapter(getContext(), sections);
        binding.rvActiveMedications.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvActiveMedications.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        binding.rvActiveMedications.setAdapter(mainRecyclerAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    private void initData() {
        String sectionOneName = "Active Meds";
        List<MyData> sectionOneItems = new ArrayList<>();
        sectionOneItems.add(new MyData("AAAAAAA", 22, "AAAABBBAAAA", R.drawable.ic_launcher_foreground));
        sectionOneItems.add(new MyData("BBBBBBB", 22, "AAAABBBAAAA", R.drawable.ic_launcher_foreground));
        sectionOneItems.add(new MyData("CCCCCC", 22, "AAAABBBAAAA", R.drawable.ic_launcher_foreground));
        sectionOneItems.add(new MyData("DDDDDD", 22, "AAAABBBAAAA", R.drawable.ic_launcher_foreground));
        sectionOneItems.add(new MyData("EEEEEE", 22, "AAAABBBAAAA", R.drawable.ic_launcher_foreground));

        String sectionTwoName = "Inactive Meds";
        List<MyData> sectionTwoItems = new ArrayList<>();
        sectionTwoItems.add(new MyData("FFFFF", 22, "AAAABBBAAAA", R.drawable.ic_launcher_foreground));
        sectionTwoItems.add(new MyData("GGGGGG", 22, "AAAABBBAAAA", R.drawable.ic_launcher_foreground));


        sections = new ArrayList<>();
        sections.add(new Section(sectionOneName, sectionOneItems));
        sections.add(new Section(sectionTwoName, sectionTwoItems));

        String TAG = "TAG";
        Log.i(TAG, "initData: " + sections);

    }
}