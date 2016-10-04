package com.rana.kisannetwork.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rana.kisannetwork.R;
import com.rana.kisannetwork.datastructure.Contacts;

import java.util.ArrayList;

/**
 * Created by sandeeprana on 04/10/16.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */

public class AdapterFirstTab extends RecyclerView.Adapter<AdapterFirstTab.ViewHolder> {

    private final ArrayList<Contacts> contactsList;
    private final ListenerContactClicked listenerCallbackClicked;

    public AdapterFirstTab(ArrayList<Contacts> contactsArrayList, ListenerContactClicked listenerContactClicked) {
        this.contactsList = contactsArrayList;
        this.listenerCallbackClicked = listenerContactClicked;
    }

    @Override
    public AdapterFirstTab.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv_firsttab_contact, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(AdapterFirstTab.ViewHolder holder, int position) {
        Contacts contact = this.contactsList.get(position);
        String fullName = contact.getFirstName() + " " + contact.getLastName();
        String fullPHone = "+" + contact.getCountryCode() + contact.getPhone();

        holder.textViewFullName.setText(fullName);
        holder.textViewPhone.setText(fullPHone);
    }


    @Override
    public int getItemCount() {
        return this.contactsList.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView textViewFullName, textViewPhone;

        ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textViewFullName = (TextView) itemView.findViewById(R.id.first_item_fullname);
            textViewPhone = (TextView) itemView.findViewById(R.id.first_item_phone);

        }

        @Override
        public void onClick(View view) {
            if (listenerCallbackClicked != null) {
                listenerCallbackClicked.contactClicked(contactsList.get(this.getLayoutPosition()));
            }

        }

    }
}
