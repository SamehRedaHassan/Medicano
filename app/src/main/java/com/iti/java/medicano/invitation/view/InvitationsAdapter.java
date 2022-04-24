package com.iti.java.medicano.invitation.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.iti.java.medicano.R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class InvitationsAdapter extends RecyclerView.Adapter<InvitationsAdapter.myInvitationViewHolder>{
    HashMap<String,Object> invitations;
    Context context;
    List<String> myInvitors;
    AcceptDenyCallback delegate;


    public InvitationsAdapter(Context context ,  HashMap<String,Object> invitations, AcceptDenyCallback delegate){
        this.invitations = invitations;
        this.context = context;
        this.delegate = delegate;
        myInvitors = new ArrayList<>();
        if (invitations != null && invitations.keySet() != null){
            myInvitors.addAll(invitations.keySet());
        }
    }

    @NonNull
    @Override
    public myInvitationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        InvitationsAdapter myAdapter = new InvitationsAdapter(context ,invitations, delegate);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.invitation_row, parent, false);
        InvitationsAdapter.myInvitationViewHolder viewHolder = myAdapter.new myInvitationViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull InvitationsAdapter.myInvitationViewHolder holder, int position) {
        holder.invitorName.setText(invitations.get(myInvitors.get(position)) + " wants to add you as his medfriend");
        holder.denyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               delegate.didPressDenyWithID(myInvitors.get(holder.getAdapterPosition()));
            }
        });
        holder.acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                delegate.didPressAcceptWithID(myInvitors.get(holder.getAdapterPosition()),invitations.get(myInvitors.get(holder.getAdapterPosition()) ).toString());

            }
        });
    }

    @Override
    public int getItemCount() {

        return myInvitors.size();
    }

    public class myInvitationViewHolder extends RecyclerView.ViewHolder {
        TextView invitorName;
        Button acceptBtn;
        Button denyBtn;
        View row;

        public myInvitationViewHolder(@NonNull View itemView) {
            super(itemView);
            row = itemView;
            invitorName = row.findViewById(R.id.inviter_txtView);
            acceptBtn = row.findViewById(R.id.btn_accept);
            denyBtn = row.findViewById(R.id.btn_deny);
        }
    }
}
