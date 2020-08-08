package com.android.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private Button btn_login;
    private TextView link_regist;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://192.168.33.216/android_register_login/login.php"; // Sesuaikan dengan url php (xampp/htdocs/namaFolder/file.php)
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        loading = findViewById(R.id.pb_loadingLogin);
        username = findViewById(R.id.edt_usernameLogin);
        password = findViewById(R.id.edt_passwordLogin);
        btn_login = findViewById(R.id.btn_loginLogin);
        link_regist = findViewById(R.id.btn_dontHaveAccount);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mUsername = username.getText().toString().trim();
                String mPass = password.getText().toString().trim();

                if (!mUsername.isEmpty() || !mPass.isEmpty()) {
                    Login(mUsername, mPass);
                } else {
                    username.setError("Please insert username");
                    password.setError("Please insert password");
                }
            }
        });

        link_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private void Login(final String username, final String password) {

        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            if (success.equals("1")) {

                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String username = object.getString("username").trim();

                                    sessionManager.createSession(username);

//                                    Toast.makeText(LoginActivity.this, "Succes Login. \n Your Name : " +username+"\nYour Email : " +email, Toast.LENGTH_SHORT).show();

                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                    intent.putExtra("username", username);
                                    startActivity(intent);

                                    loading.setVisibility(View.GONE);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, "Error " + e, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Error " + error, Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("username", username);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}