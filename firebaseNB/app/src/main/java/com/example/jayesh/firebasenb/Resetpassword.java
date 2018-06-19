package com.example.jayesh.firebasenb;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Button;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Jayesh on 26-Feb-18.
 */

public class Resetpassword extends AppCompatActivity {
    private EditText inputEmail;

    private Button btnReset, btnBack;

    private FirebaseAuth auth;
    private ProgressDialog mProgressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.resetpassword);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Login");
        mProgressDialog.setMessage("Please wait..");


        inputEmail = (EditText) findViewById(R.id.email);

        btnReset = (Button) findViewById(R.id.btn_reset_password);

        btnBack = (Button) findViewById(R.id.btn_back);

        auth = FirebaseAuth.getInstance();

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }

                mProgressDialog.show();

                auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Resetpassword.this, "We have sent you email to reset your password!!!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(Resetpassword.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
                                }
                                mProgressDialog.dismiss();
                            }
                        });
            }
        });
    }

}