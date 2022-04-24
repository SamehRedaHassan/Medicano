package com.iti.java.medicano.invitation.view;

import static android.content.Context.MODE_PRIVATE;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.iti.java.medicano.Constants;
import com.iti.java.medicano.databinding.FragmentInvitationsBinding;
import com.iti.java.medicano.invitation.presenter.InvitationsPresenter;
import com.iti.java.medicano.invitation.presenter.InvitationsPresenterImpl;
import com.iti.java.medicano.model.databaselayer.DatabaseLayer;
import com.iti.java.medicano.model.userrepo.UserRepoImpl;
import java.util.HashMap;

public class InvitationsFragment  extends Fragment implements InvitationsView {

    private FragmentInvitationsBinding binding ;
    RecyclerView invitationsView ;
    InvitationsAdapter invitationsAdapter ;
    InvitationsPresenter presenter ;
    HashMap<String,Object> invitations = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentInvitationsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureUI();
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void configureUI(){
        invitationsView = binding.invitationsRecycler;
        invitationsView.setHasFixedSize(true);

    }

    private void initData() {
        presenter = new InvitationsPresenterImpl(this, UserRepoImpl.getInstance(DatabaseLayer.getDBInstance(getContext()).UserDAO(),getContext().getSharedPreferences(Constants.SHARED_PREFERENCES,MODE_PRIVATE)));
        presenter.getInvitations().removeObservers(getViewLifecycleOwner());
        presenter.getInvitations().observe(getViewLifecycleOwner(), new Observer<HashMap<String,Object>>() {
            @Override
            public void onChanged(HashMap<String, Object> invitors) {
                invitations.clear();
                invitations = invitors ;
                Log.i("TAG", "onChanged: " + invitations.size());
                //ABDOO
                invitationsAdapter = new InvitationsAdapter(getContext(),invitations);
                invitationsView.setAdapter(invitationsAdapter);
                RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
                invitationsView.setLayoutManager(manager);
                invitationsAdapter.notifyDataSetChanged();
            }
        });
    }
}
