package com.example.jayesh.firebasenb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog mProgressDialog;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView reset;

    private FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Login");
        mProgressDialog.setMessage("Please wait..");

        edtEmail = (EditText) findViewById(R.id.edt_email);
        edtPassword = (EditText) findViewById(R.id.edt_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        reset=(TextView) findViewById(R.id.reset);
        mFirebaseAuth = FirebaseAuth.getInstance();
        btnLogin.setOnClickListener(this);

        reset.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),Resetpassword.class);
                startActivity(mainIntent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_login){

            boolean isEmptyField = false;

            final String email  = edtEmail.getText().toString().trim();
            String password  = edtPassword.getText().toString().trim();
            if (TextUtils.isEmpty(email)){
                isEmptyField = true;
                edtEmail.setError("required");
            }

            if (TextUtils.isEmpty(password)){
                isEmptyField = true;
                edtPassword.setError("required");
            }

            if (!isEmptyField){
                mProgressDialog.show();
                mFirebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    mProgressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                  //  AppPreference mAppPreference = new AppPreference(LoginActivity.this);
                                 //   mAppPreference.setEmail(email);
                                    Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                    playNotificationSound();
                                    ClickMe();
                                    startActivity(new Intent(LoginActivity.this,Tab5Activity.class));
                                    finish();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Login error", Toast.LENGTH_SHORT).show();
                                    mProgressDialog.dismiss();
                                }
                            }
                        });
            }
        }
    }
    private void ClickMe()
    {
        Intent resultIntent = new Intent(this,MainActivity.class);
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent piResult = PendingIntent.getActivity(this,(int) Calendar.getInstance().getTimeInMillis(),resultIntent,0);

        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
        inboxStyle.setBigContentTitle("NOTICE BOARD Notification");
        inboxStyle.addLine("Login Successful !!!.");
        inboxStyle.setSummaryText("+ More");

        NotificationCompat.Builder mBuilder =
                (NotificationCompat.Builder) new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.it)
                        .setContentTitle("Inbox style notification")
                        .setContentText("One new notification")
                        .setStyle(inboxStyle)
                        .addAction(R.mipmap.ic_launcher,"show activity",piResult);
        NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0,mBuilder.build());
    }

    public void playNotificationSound() {
        try {
            Uri alarmSound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                    + "://" + this.getPackageName() + "/raw/notification");
            Ringtone r = RingtoneManager.getRingtone(this, alarmSound);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
