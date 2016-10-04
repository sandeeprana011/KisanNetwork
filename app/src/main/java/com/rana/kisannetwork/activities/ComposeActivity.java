package com.rana.kisannetwork.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.rana.kisannetwork.R;
import com.rana.kisannetwork.database.Messages;
import com.rana.kisannetwork.datastructure.Constants;
import com.rana.kisannetwork.datastructure.Contacts;
import com.rana.kisannetwork.datastructure.JKeys;
import com.rana.kisannetwork.network.StringRequestOverridden;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ComposeActivity extends AppCompatActivity {

    EditText editText;
    String otpSent;
    Contacts contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compose);


        contacts = this.receiveContactInfo(getIntent());

        editText = (EditText) findViewById(R.id.e_message_to_send);
        TextView noteTextView = (TextView) findViewById(R.id.note);
        noteTextView.setText("NOTE: all messages will be sent tonumber +91" + contacts.getPhone());
        otpSent = this.getSixDigitRandomNumber();

        String textToSend = "Hi. Your OTP is: " + otpSent;
        editText.setText(textToSend);
    }

    public void sendMessageUsingTwilio(final View view) {


        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDateandTime = sdf.format(date);
        final Messages messages = new Messages(currentDateandTime, Constants.MESSAGE_TO, Constants.FROM,
                this.editText.getText().toString(), otpSent, "Unknown", contacts.getFirstName() + " " + contacts.getLastName());

        ((Button) view).setText("Sending to " + contacts.getFirstName());
        view.setEnabled(false);

        StringRequestOverridden stringRequestOverridden = new StringRequestOverridden
                (
                        Constants.TWILIO_BASE,
                        Constants.ACCOUNT_SID,
                        Constants.AUTH_TOKEN,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject object = new JSONObject(response);
                                    if (object.has(JKeys.BODY) && !object.isNull(JKeys.BODY)) {
                                        Toast.makeText(ComposeActivity.this, object.getString(JKeys.BODY), Toast.LENGTH_LONG).show();
                                        messages.setSentstatus("Sent");
                                        ((Button) view).setText("Sent to " + contacts.getFirstName());
                                    } else {
                                        messages.setSentstatus("Body Not Received");
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                view.setEnabled(true);
                                Log.e("TWILIO", "" + response);
                                Messages.save(messages);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                messages.setSentstatus("Failed");
//                                Toast.makeText(ComposeActivity.this, "Message Not Sent!", Toast.LENGTH_LONG).show();
                                view.setEnabled(true);
                                ((Button) view).setText("Error! Retry");
                                Log.e("Erro", "" + error.getMessage());
                                Messages.save(messages);
                            }
                        }
                ) {
            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("To", "+" + contacts.getCountryCode() + contacts.getPhone());
                params.put("From", Constants.FROM);
                params.put("Body", editText.getText().toString());
                return params;
            }
        };


        Volley.newRequestQueue(this).add(stringRequestOverridden);

    }

    String getSixDigitRandomNumber() {
        Random random = new Random();
        int numberSix = 100000 + random.nextInt(900000);
        return String.valueOf(numberSix);
    }


    private Contacts receiveContactInfo(Intent intent) {
        String fName = intent.getStringExtra(JKeys.FIRST_NAME);
        String lName = intent.getStringExtra(JKeys.LAST_NAME);
        String phone = intent.getStringExtra(JKeys.PHONE);
        String ccode = intent.getStringExtra(JKeys.COUNTRY_CODE);
        return new Contacts(fName, lName, phone, ccode);
    }

}
