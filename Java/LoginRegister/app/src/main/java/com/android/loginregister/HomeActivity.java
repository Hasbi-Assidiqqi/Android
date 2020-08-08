package com.android.loginregister;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {
    private TextView username;
    private Button btn_logout;
    private long backPressed;
    SessionManager sessionManager;

    @Override
    public void onBackPressed() {
        if (backPressed + 1000 > System.currentTimeMillis()) {
            super.onBackPressed();
            return;
        } else {
            Toast.makeText(getBaseContext(),"Press back again to exit!", Toast.LENGTH_SHORT).show();
        }

        backPressed = System.currentTimeMillis();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        username = findViewById(R.id.username);
        btn_logout = findViewById(R.id.btn_logout);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String mUsername = user.get(SessionManager.USERNAME);

        username.setText(mUsername);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.logout();
            }
        });
    }
}