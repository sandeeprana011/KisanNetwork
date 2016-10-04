package com.rana.kisannetwork.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.rana.kisannetwork.R;
import com.rana.kisannetwork.datastructure.Contacts;
import com.rana.kisannetwork.datastructure.JKeys;

public class ContactsDetailActivity extends AppCompatActivity {


    TextView tvFName, tvLName, tvPhone, tvCCode;
    private Contacts contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts_detail);
        contact = this.receiveContactInfo(getIntent());
        this.initializeViews(contact);

    }

    private void initializeViews(Contacts contact) {
        tvFName = (TextView) findViewById(R.id.first_name_contactdetails);
        tvLName = (TextView) findViewById(R.id.last_name_contactdetails);
        tvPhone = (TextView) findViewById(R.id.phone_contactdetails);
        tvCCode = (TextView) findViewById(R.id.country_code_contactdetails);

        tvFName.setText(contact.getFirstName());
        tvLName.setText(contact.getLastName());
        tvPhone.setText(contact.getPhone());
        tvCCode.setText(contact.getCountryCode());

    }

    private Contacts receiveContactInfo(Intent intent) {
        String fName = intent.getStringExtra(JKeys.FIRST_NAME);
        String lName = intent.getStringExtra(JKeys.LAST_NAME);
        String phone = intent.getStringExtra(JKeys.PHONE);
        String ccode = intent.getStringExtra(JKeys.COUNTRY_CODE);
        return new Contacts(fName, lName, phone, ccode);
    }

    public void sendMessage(View view) {
        Intent intent = new Intent(this, ComposeActivity.class);
        intent.putExtra(JKeys.FIRST_NAME, contact.getFirstName());
        intent.putExtra(JKeys.LAST_NAME, contact.getLastName());
        intent.putExtra(JKeys.PHONE, contact.getPhone());
        intent.putExtra(JKeys.COUNTRY_CODE, contact.getCountryCode());
        startActivity(intent);


//        Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//        sendIntent.setData(Uri.parse("sms:+" + contact.getCountryCode() + contact.getPhone()));
//        sendIntent.putExtra("sms_body", "From Contact details activity");
//        startActivity(sendIntent);

    }
}
