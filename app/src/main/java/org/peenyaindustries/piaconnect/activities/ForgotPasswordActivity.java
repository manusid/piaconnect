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
import org.peenyaindustries.piaconnect.utilities.L;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText registeredEmail;
    private AlertDialog.Builder builder;

    //Email Validation
    public static boolean isValidEmail(CharSequence target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        builder = new AlertDialog.Builder(this);

        registeredEmail = (EditText) findViewById(R.id.registeredEmail);
    }

    public void sendPassword(View view) {
        final String email = registeredEmail.getText().toString().trim();

        if (email.isEmpty()) {
            builder.setTitle("Something went wrong....");
            builder.setMessage("Email Id cannot be Empty.");
            displayAlert("error");
        } else if (!isValidEmail(email)) {
            builder.setTitle("Something went wrong....");
            builder.setMessage("Enter a valid e-mail address.");
            displayAlert("error");
        } else {
            StringRequest request = new StringRequest(Request.Method.POST, "http://www.peenyaindustries.org/pia_connect/forgot_password.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    L.Log("Error" + response);
                    if (response != null && response.length() > 0) {

                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jObj = jsonArray.getJSONObject(0);

                            String message = Constants.NA;
                            String code = Constants.NA;

                            if (jObj.has("message")) {
                                message = jObj.getString("message");
                            }

                            if (jObj.has("code")) {
                                code = jObj.getString("code");
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

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> params = new HashMap<>();
                    params.put("email", email);
                    return params;
                }
            };
            VolleySingletonAuth.getInstance(this).addToRequestQueue(request);
        }
    }

    //Alert Dialog Display
    private void displayAlert(final String alert) {
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (alert) {
                    case "error":
                        registeredEmail.setText("");
                        break;
                    default:
                        startActivity(new Intent(ForgotPasswordActivity.this, LoginActivity.class));
                        finish();
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
