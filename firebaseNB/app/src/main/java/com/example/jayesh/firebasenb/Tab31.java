package com.example.jayesh.firebasenb;

import android.*;
import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;  */
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Jayesh on 08-Jan-18.
 */

public class Tab31 extends AppCompatActivity implements View.OnClickListener {

        //this is the pic pdf code used in file chooser
        final static int PICK_PDF_CODE = 2342;

        //these are the views
        TextView textViewStatus;
        EditText editTextFilename;
    EditText editTextComment;
        ProgressBar progressBar;

        //the firebase objects for storage and database
        StorageReference mStorageReference;
        DatabaseReference mDatabaseReference;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.tab31);


            //getting firebase objects
            mStorageReference = FirebaseStorage.getInstance().getReference();
            mDatabaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

            //getting the views
            textViewStatus = (TextView) findViewById(R.id.textViewStatus);
            editTextFilename = (EditText) findViewById(R.id.editTextFileName);
            editTextComment = (EditText) findViewById(R.id.editTextComment);
            progressBar = (ProgressBar) findViewById(R.id.progressbar);

            //attaching listeners to views
            findViewById(R.id.buttonUploadFile).setOnClickListener(this);
            findViewById(R.id.textViewUploads).setOnClickListener(this);
        }

        //this function will get the pdf from the storage
        private void getPDF() {
            //for greater than lolipop versions we need the permissions asked on runtime
            //so if the permission is not available user will go to the screen to allow storage permission
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && ContextCompat.checkSelfPermission(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                        Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                return;
            }

            //creating an intent for file chooser
            Intent intent = new Intent();
            intent.setType("application/pdf");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_PDF_CODE);
        }


        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            boolean isEmptyField = false;
            final String name  = editTextFilename.getText().toString().trim();
            final String comment  = editTextComment.getText().toString().trim();
            if (TextUtils.isEmpty(name)){
                isEmptyField = true;
                editTextFilename.setError("required");
            }
            else
            {
            //when the user choses the file
            if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null && data.getData() != null) {
                //if a file is selected
                if (data.getData() != null) {
                    //uploading the file
                    uploadFile(data.getData());
                } else {
                    Toast.makeText(this, "No file chosen", Toast.LENGTH_SHORT).show();
                }
                }
            }
        }


        //this method is uploading the file
        //the code is same as the previous tutorial
        //so we are not explaining it
        private void uploadFile(Uri data) {
            progressBar.setVisibility(View.VISIBLE);
            StorageReference sRef = mStorageReference.child(Constants.STORAGE_PATH_UPLOADS + System.currentTimeMillis() + ".pdf");
            sRef.putFile(data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressBar.setVisibility(View.GONE);
                            textViewStatus.setText("File Uploaded Successfully");

                            String dateAndTime = DateFormat.format("yyyy-MM-dd_hh:mm:ss", new Date()).toString();
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                            String email = user.getEmail();
                            Upload upload = new Upload(editTextFilename.getText().toString(), taskSnapshot.getDownloadUrl().toString(), dateAndTime,email,editTextComment.getText().toString());
                            mDatabaseReference.child(mDatabaseReference.push().getKey()).setValue(upload);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @SuppressWarnings("VisibleForTests")
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                            textViewStatus.setText((int) progress + "% Uploading...");
                        }
                    });

        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.buttonUploadFile:
                    getPDF();
                    break;
                case R.id.textViewUploads:
                    startActivity(new Intent(this, ViewUploadsActivity.class));
                    break;
            }
        }
    }
