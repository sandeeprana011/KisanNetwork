package com.rana.kisannetwork.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rana.kisannetwork.R;
import com.rana.kisannetwork.activities.ContactsDetailActivity;
import com.rana.kisannetwork.adapters.AdapterFirstTab;
import com.rana.kisannetwork.adapters.ListenerContactClicked;
import com.rana.kisannetwork.datastructure.Contacts;
import com.rana.kisannetwork.datastructure.JKeys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by sandeeprana on 04/10/16.
 * License is only applicable to individuals and non-profits
 * and that any for-profit company must
 * purchase a different license, and create
 * a second commercial license of your
 * choosing for companies
 */
public class FirstTab extends Fragment implements ListenerContactClicked {
    public static final CharSequence TITLE = "Contacts";
    String JSON_DATA = "{\n" +
            "  \"contacts\": [\n" +
            "    {\n" +
            "      \"first_name\": \"Sandeep\",\n" +
            "      \"last_name\": \"Rana\",\n" +
            "      \"phone\": \"8439740563\",\n" +
            "      \"country_code\": \"91\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"first_name\": \"Vinay\",\n" +
            "      \"last_name\": \"Rana\",\n" +
            "      \"phone\": \"9015434206\",\n" +
            "      \"country_code\": \"91\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"first_name\": \"Aditya\",\n" +
            "      \"last_name\": \"Agarwalla\",\n" +
            "      \"phone\": \"9111011382\",\n" +
            "      \"country_code\": \"91\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.first_tab, container, false);
        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.rv_first_tab);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        AdapterFirstTab adapterFirstTab = new AdapterFirstTab(this.parseJsonAndReturnContacts(this.JSON_DATA), this);

        recyclerView.setAdapter(adapterFirstTab);

        return rootView;
    }


    ArrayList<Contacts> parseJsonAndReturnContacts(String jsonData) {
        ArrayList<Contacts> arrayList = new ArrayList<>();
        try {
            JSONObject root = new JSONObject(jsonData);
            if (isOk(root, JKeys.CONTACTS)) {
                JSONArray arrayContacts = root.getJSONArray(JKeys.CONTACTS);
                for (int i = 0; i < arrayContacts.length(); i++) {
                    String fName = null, lName = null, phone = null, cCode = null;
                    JSONObject objectCont = arrayContacts.getJSONObject(i);
                    if (isOk(objectCont, JKeys.FIRST_NAME))
                        fName = objectCont.getString(JKeys.FIRST_NAME);
                    if (isOk(objectCont, JKeys.LAST_NAME))
                        lName = objectCont.getString(JKeys.LAST_NAME);
                    if (isOk(objectCont, JKeys.PHONE)) phone = objectCont.getString(JKeys.PHONE);
                    if (isOk(objectCont, JKeys.COUNTRY_CODE))
                        cCode = objectCont.getString(JKeys.COUNTRY_CODE);
                    Contacts contact = new Contacts(fName, lName, phone, cCode);
                    arrayList.add(contact);

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    boolean isOk(JSONObject object, String jsonKey) {
        return object.has(jsonKey) && !object.isNull(jsonKey);
    }


    @Override
    public void contactClicked(Contacts contacts) {
        Intent intent = new Intent(getContext(), ContactsDetailActivity.class);
        intent.putExtra(JKeys.FIRST_NAME, contacts.getFirstName());
        intent.putExtra(JKeys.LAST_NAME, contacts.getLastName());
        intent.putExtra(JKeys.PHONE, contacts.getPhone());
        intent.putExtra(JKeys.COUNTRY_CODE, contacts.getCountryCode());
        startActivity(intent);
    }
}
