package org.peenyaindustries.piaconnect.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.peenyaindustries.piaconnect.R;
import org.peenyaindustries.piaconnect.extras.Constants;
import org.peenyaindustries.piaconnect.network.VolleySingletonAuth;

import java.util.HashMap;
import java.util.Map;

import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_CODE;
import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_MESSAGE;
import static org.peenyaindustries.piaconnect.extras.URLEndPoints.URL_REGISTER;

public class OtpActivity extends AppCompatActivity {

    EditText otpCode;
    Button otpButton;
    TextView displayMobile, backLink;

    //
    String otp, name, email, mobile, password;
    AlertDialog.Builder builder;

    @Override
    protected void onResume() {
        super.onResume();

        otpCode = (EditText) findViewById(R.id.otpCode);
        displayMobile = (TextView) findViewById(R.id.displayMobile);
        backLink = (TextView) findViewById(R.id.backLink);
        otpButton = (Button) findViewById(R.id.otpButton);

        //Collect values from Intent
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            name = extras.getString("name");
            email = extras.getString("email");
            mobile = extras.getString("mobile");
            password = extras.getString("password");
        } else {
            name = "";
            email = "";
            mobile = "";
            password = "";
        }

        builder = new AlertDialog.Builder(this);

        displayMobile.setText(mobile);

        otpButton.performClick();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);


    }

    //Validate the OTP
    public void otpValidate(View view) {
        //otp = otpCode.getText().toString().trim();
        otp = "000000";
        if (otp.isEmpty()) {
            builder.setTitle("Something went wrong...");
            builder.setMessage("OTP cannot be empty");
            displayAlert("fieldEmpty");
        }

        StringRequest request = new StringRequest(Request.Method.POST, URL_REGISTER, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //Parse JSON Response

                if (!response.isEmpty()) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        JSONObject jsonObject = jsonArray.getJSONObject(0);

                        String code = Constants.NA;
                        String message = Constants.NA;

                        if (jsonObject.has(RESPONSE_CODE)) {
                            code = jsonObject.getString(RESPONSE_CODE);
                        }

                        if (jsonObject.has(RESPONSE_MESSAGE)) {
                            message = jsonObject.getString(RESPONSE_MESSAGE);
                        }

                        //builder.setMessage("Server Response....");
                        //builder.setMessage(message);
                        //displayAlert(code);

                        startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                        finish();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("mobile", mobile);
                params.put("password", password);
                params.put("otp", otp);
                return params;
            }
        };
        VolleySingletonAuth.getInstance(this).addToRequestQueue(request);
    }

    //Alert Dialog Display
    private void displayAlert(final String alert) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (alert.equals("registerSuccess")) {
                    startActivity(new Intent(OtpActivity.this, LoginActivity.class));
                    finish();
                } else {
                    otpCode.setText("");
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    //To Register Activity
    public void editMobile(View view) {
        Intent i = new Intent(OtpActivity.this, RegisterActivity.class);
        i.putExtra("name", name);
        i.putExtra("email", password);
        i.putExtra("mobile", mobile);
        startActivity(i);
        finish();
    }
}
