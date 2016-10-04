package com.rana.kisannetwork.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rana.kisannetwork.R;
import com.rana.kisannetwork.database.Messages;

import java.util.Collections;
import java.util.List;

/**
 * Created by sandeeprana on 04/10/16.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

public class AdapterSecondTabRecyclerView extends RecyclerView.Adapter<AdapterSecondTabRecyclerView.ViewHolder> {


    private List<Messages> arrayListMessages;

    public AdapterSecondTabRecyclerView() {
        this.arrayListMessages = Messages.listAll(Messages.class);
        Collections.reverse(this.arrayListMessages);
    }

    @Override
    public AdapterSecondTabRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_secondtab_history, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterSecondTabRecyclerView.ViewHolder holder, int position) {

        Messages message = this.arrayListMessages.get(position);


        holder.textViewFullName.setText(message.getNameto());
        holder.textViewTime.setText(message.getTimenumber());
        holder.textViewOtp.setText(message.getOtp());
        holder.textViewStatus.setText(message.getSentstatus());
    }


    @Override
    public int getItemCount() {
        return this.arrayListMessages.size();
    }

    public void notifyDataSetChangedOver() {
        this.arrayListMessages = Messages.listAll(Messages.class);
        Collections.reverse(this.arrayListMessages);
        super.notifyDataSetChanged();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewFullName, textViewTime, textViewOtp, textViewStatus;

        ViewHolder(View itemView) {
            super(itemView);
            textViewFullName = (TextView) itemView.findViewById(R.id.t_name_sento_itemhistory);
            textViewTime = (TextView) itemView.findViewById(R.id.t_time_itemhistory);
            textViewOtp = (TextView) itemView.findViewById(R.id.t_otp_itemhistory);
            textViewStatus = (TextView) itemView.findViewById(R.id.t_status_itemhistory);

        }


    }
}
