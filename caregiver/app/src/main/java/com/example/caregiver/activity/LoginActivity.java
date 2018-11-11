package com.example.caregiver.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.caregiver.App;
import com.example.caregiver.R;
import com.example.caregiver.activity.MainActivity;

public class LoginActivity extends AppCompatActivity {

    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("데이터 로딩중...");
        dialog.show();
        ((App)getApplication()).load(this);
    }

    public void loadFinish() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        });
    }

    public void onClickLogin(View view) {
        EditText input_id = findViewById(R.id.input_id);
        EditText input_pw = findViewById(R.id.input_pw);

        String id = input_id.getText().toString();
        String pw = input_pw.getText().toString();

        if(TextUtils.equals(id, "test") && TextUtils.equals(pw, "test123")) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
    }
}
