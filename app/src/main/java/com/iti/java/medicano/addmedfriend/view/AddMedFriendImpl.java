package com.iti.java.medicano.addmedfriend.view;

import static android.content.Context.MODE_PRIVATE;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.addmedfriend.presenter.AddMedFriendPresenter;
import com.iti.java.medicano.addmedfriend.presenter.AddMedFriendPresenterImpl;
import com.iti.java.medicano.databinding.FragmentAddDependentBinding;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;

public class AddMedFriendImpl  extends Fragment implements AddMedFriend {
    Button btnInviteMedFriend ;
    TextView txtViewMedFriendEmail;
    private FragmentAddDependentBinding binding;
    private AddMedFriendPresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentAddDependentBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configureUI(){
        presenter =  new AddMedFriendPresenterImpl(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE)));
        btnInviteMedFriend = binding.btnInvite;
        txtViewMedFriendEmail = binding.txtViewMedFriendEmail;
        btnInviteMedFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = txtViewMedFriendEmail.getText().toString().trim();
                if(email == ""){
                    Toast.makeText(getContext(), "Please Enter Valid Email", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    presenter.addMedFriendWithEmail(txtViewMedFriendEmail.getText().toString().trim());
                    Toast.makeText(getContext(), "Invitation Sent successfully", Toast.LENGTH_SHORT).show();
                    binding.txtViewMedFriendEmail.setText("");
                }
            }
        });
    }

}
