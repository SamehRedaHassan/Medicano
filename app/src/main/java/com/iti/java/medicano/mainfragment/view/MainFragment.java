package com.iti.java.medicano.mainfragment.view;

import static android.content.Context.MODE_PRIVATE;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.work.WorkManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.R;
import com.iti.java.medicano.addmedication.repo.medication.MedicationRepoImpl;
import com.iti.java.medicano.databinding.FragmentMainBinding;
import com.iti.java.medicano.mainfragment.presenter.MainFragmentPresenter;
import com.iti.java.medicano.mainfragment.presenter.MainFragmentPresenterImpl;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import com.iti.java.medicano.utils.OnNotifyDataChanged;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainFragment extends Fragment implements MainFragmentView {
    private FragmentMainBinding binding;
    private NavController navController;
    private MainFragmentPresenter presenter;
    private List<User> users;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMainBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = ((NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_main)).getNavController();
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController);
        initData();
        //TODO if first Time Fire PeriodicWorker for the first time;;;
        //TODO if first Time Fire PeriodicWorker for the first time;;;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void initData() {

        presenter = new MainFragmentPresenterImpl(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(), getContext().getSharedPreferences(Constants.SHARED_PREFERENCES, MODE_PRIVATE)), MedicationRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).MedicationDAO(),
                FirebaseDatabase.getInstance(),
                FirebaseAuth.getInstance().getUid(),
                WorkManager.getInstance(getContext().getApplicationContext())));
        binding.currentUserName.setOnItemClickListener((adapterView, view, i, l) -> {
            presenter.switchUser(users.get(i));
            Fragment fragmentById = getChildFragmentManager().findFragmentById(R.id.fragmentHome);
            Fragment primaryNavigationFragment = fragmentById.getChildFragmentManager().getPrimaryNavigationFragment();
            if (primaryNavigationFragment instanceof OnNotifyDataChanged) {
                ((OnNotifyDataChanged) primaryNavigationFragment).notifyDataChanged();
            }
        });
        presenter.syncUsers().observe(getViewLifecycleOwner(), users -> {
            if (users.size() > 0) {
                MainFragment.this.users = users;
                List<String> names = users.stream().map(x -> x.getFullName()).collect(Collectors.toList());
                ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), R.layout.item_drop_down, names);
                ((AutoCompleteTextView) binding.textInputSchoolName.getEditText()).setAdapter(adapter);
            }
        });
    }
}
