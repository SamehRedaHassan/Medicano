package com.iti.java.medicano.splash.view;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.iti.java.medicano.Constants;
import com.iti.java.medicano.R;
import com.iti.java.medicano.databinding.FragmentSplashBinding;
import com.iti.java.medicano.model.User;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepo;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;

// one to three activity
public class SplashFragment extends Fragment {
    private FragmentSplashBinding binding;
    private NavController navController;
    private UserRepo repo;
    private User user;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentSplashBinding.inflate(getLayoutInflater());
        navController = NavHostFragment.findNavController(this);

        new Handler().postDelayed(() -> {
            if(user == null) {
                if (navController.getCurrentDestination().getId() == R.id.splashFragment)
                    navController.navigate(R.id.action_splashFragment_to_fragmentLogin2);
            }
            else {
                if (navController.getCurrentDestination().getId() == R.id.splashFragment)
                    navController.navigate(R.id.action_splashFragment_to_mainFragment);
            }
        }, 1000); // wait for 5 seconds


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repo = UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE));
        user = repo.getPreferences();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
