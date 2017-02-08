package org.peenyaindustries.piaconnect.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

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
import org.peenyaindustries.piaconnect.storage.SessionManager;

import java.util.HashMap;
import java.util.Map;

import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_CODE;
import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_MESSAGE;
import static org.peenyaindustries.piaconnect.extras.URLEndPoints.URL_OTP;

public class RegisterActivity extends AppCompatActivity {

    private EditText registerName, registerEmail, registerMobile, registerPassword;
    private String name, email, mobile, password;
    private AlertDialog.Builder builder;
    private SessionManager session;

    //Email Validation
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onResume() {
        super.onResume();
        session = new SessionManager(RegisterActivity.this);
        if (session.isLoggedIn()) {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        builder = new AlertDialog.Builder(this);

        registerName = (EditText) findViewById(R.id.registerName);
        registerEmail = (EditText) findViewById(R.id.registerEmail);
        registerMobile = (EditText) findViewById(R.id.registerMobile);
        registerPassword = (EditText) findViewById(R.id.registerPassword);

        //Check For Intent Extras
        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            registerName.setText(extras.getString("name"));
            registerEmail.setText(extras.getString("email"));
            registerMobile.setText(extras.getString("mobile"));
        }

    }

    public void smsRequest(View view) {

        name = registerName.getText().toString().trim();
        email = registerEmail.getText().toString().trim();
        mobile = registerMobile.getText().toString().trim();
        password = registerPassword.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
            builder.setTitle("Something went wrong...");
            builder.setMessage("All the fields are mandatory.");
            displayAlert("fieldsEmpty");
        } else if (!isValidEmail(email)) {
            builder.setTitle("Something went wrong...");
            builder.setMessage("Enter a valid email address");
            displayAlert("emailInvalid");
        } else if (!isValidMobile(mobile)) {
            builder.setTitle("Something went wrong...");
            builder.setMessage("Enter a valid phone number");
            displayAlert("mobileInvalid");
        } else {

            final StringRequest request = new StringRequest(Request.Method.POST, URL_OTP, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("TAG", "onResponse: " + response);
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

                            builder.setMessage("Server Response....");
                            builder.setMessage(message);
                            displayAlert(code);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("mobile", mobile);
                    return params;
                }
            };
            VolleySingletonAuth.getInstance(this).addToRequestQueue(request);
        }
    }

    public void loginLink(View view) {

        startActivity(new Intent(this, LoginActivity.class));
        finish();

    }

    //Validate Mobile Number
    private boolean isValidMobile(String phone) {
        return android.util.Patterns.PHONE.matcher(phone).matches();
    }

    //Alert Dialog Display
    private void displayAlert(final String alert) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (alert) {
                    case "mobileInvalid":
                    case "emailInvalid":
                        registerPassword.setText("");
                        break;
                    case "smsSent":
                        Intent i = new Intent(RegisterActivity.this, OtpActivity.class);
                        i.putExtra("name", name);
                        i.putExtra("email", email);
                        i.putExtra("mobile", mobile);
                        i.putExtra("password", password);
                        Log.i("TAG", "onClick: " + name + "," + email + "," + mobile + "," + password);
                        startActivity(i);
                        finish();
                        break;
                    default:
                        registerName.setText("");
                        registerEmail.setText("");
                        registerMobile.setText("");
                        registerPassword.setText("");
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }


}
