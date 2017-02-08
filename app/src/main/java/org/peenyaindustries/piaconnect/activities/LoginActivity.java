package org.peenyaindustries.piaconnect.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_EMAIL;
import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_MESSAGE;
import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_MOBILE;
import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_NAME;
import static org.peenyaindustries.piaconnect.extras.Keys.RESPONSE_ROLE;
import static org.peenyaindustries.piaconnect.extras.URLEndPoints.URL_LOGIN;

public class LoginActivity extends AppCompatActivity {
    private EditText loginEmail, loginPassword;
    private String email, password;
    private AlertDialog.Builder builder;
    private SessionManager session;

    //Email Validation
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onResume() {
        super.onResume();
        session = new SessionManager(LoginActivity.this);
        if (session.isLoggedIn()) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        builder = new AlertDialog.Builder(LoginActivity.this);

        loginEmail = (EditText) findViewById(R.id.loginEmail);
        loginPassword = (EditText) findViewById(R.id.loginPassword);
    }

    public void registerLink(View view) {
        startActivity(new Intent(this, RegisterActivity.class));
        finish();
    }

    public void loginButton(View view) {

        email = loginEmail.getText().toString().trim();
        password = loginPassword.getText().toString();

        if (!isValidEmail(email)) {
            builder.setTitle("Something went wrong....");
            builder.setMessage("Enter a valid e-mail address.");
            displayAlert("emailInvalid");
        } else if (email.isEmpty() || password.isEmpty()) {
            builder.setTitle("Something went wrong....");
            builder.setMessage("All fields are mandatory.");
            displayAlert("fieldsEmpty");
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, URL_LOGIN, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //Pares JSON Response
                    if (!response.isEmpty()) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(0);

                            String code = Constants.NA;
                            String message = Constants.NA;
                            String responseName = Constants.NA;
                            String responseEmail = Constants.NA;
                            String responseMobile = Constants.NA;
                            String responseRole = Constants.NA;

                            if (jsonObject.has(RESPONSE_CODE)) {
                                code = jsonObject.getString(RESPONSE_CODE);
                            }

                            if (jsonObject.has(RESPONSE_MESSAGE)) {
                                message = jsonObject.getString(RESPONSE_MESSAGE);
                            }

                            if (jsonObject.has(RESPONSE_NAME)) {
                                responseName = jsonObject.getString(RESPONSE_NAME);
                            }

                            if (jsonObject.has(RESPONSE_EMAIL)) {
                                responseEmail = jsonObject.getString(RESPONSE_EMAIL);
                            }

                            if (jsonObject.has(RESPONSE_MOBILE)) {
                                responseMobile = jsonObject.getString(RESPONSE_MOBILE);
                            }

                            if (jsonObject.has(RESPONSE_ROLE)) {
                                responseRole = jsonObject.getString(RESPONSE_ROLE);
                            }

                            session.createLoginSession(responseName, responseEmail, responseMobile, responseRole);

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
                    params.put("email", email);
                    params.put("password", password);
                    return params;
                }
            };

            VolleySingletonAuth.getInstance(LoginActivity.this).addToRequestQueue(request);
        }
    }

    //Alert Dialog Display
    private void displayAlert(final String alert) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (alert) {
                    case "emailInvalid":
                        loginPassword.setText("");
                        break;
                    case "loginSuccess":
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                        break;
                    default:
                        loginEmail.setText("");
                        loginPassword.setText("");
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void forgotPassword(View view) {
        startActivity(new Intent(this, ForgotPasswordActivity.class));
    }
}
