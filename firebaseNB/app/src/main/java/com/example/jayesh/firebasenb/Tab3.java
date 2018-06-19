package com.example.jayesh.firebasenb;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Jayesh on 08-Jan-18.
 */
import com.mindorks.paracamera.Camera;

public class Tab3 extends MainActivity {
    ImageView b1,b2,b3,b4;
    Camera camera;
    Bitmap bitmap;
    Integer exit=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab3);
        b1 =(ImageView) findViewById(R.id.imageView14);
        b2 =(ImageView) findViewById(R.id.imageView15);
        b4 =(ImageView) findViewById(R.id.imageView16);


        //set a onclick listener for when the button gets clicked
        b1.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),Tab30.class);
                startActivity(mainIntent);
            }
        });
        //set a onclick listener for when the button gets clicked
        b2.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                Intent mainIntent = new Intent(getApplicationContext(),Tab31.class);
                startActivity(mainIntent);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                //Intent mainIntent = new Intent(getApplicationContext(),Notice.class);
                //startActivity(mainIntent);
            takePhoto(v);
            }
        });


    }

    public void takePhoto(View view)
    {
        camera = new Camera.Builder()
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(1000)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(this);

        try
        {
            camera.takePicture();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == Camera.REQUEST_TAKE_PHOTO){
            bitmap = camera.getCameraBitmap();
            if(bitmap != null)
                {


            }
            else
                {
                Toast.makeText(this.getApplicationContext(),"Picture not taken!",Toast.LENGTH_SHORT).show();
            }
        }
    }
    protected void onDestroy() {
        super.onDestroy();
        camera.deleteImage();
    }

    @Override
    public void onBackPressed() {
            Intent mainIntent = new Intent(getApplicationContext(),Tab5Activity.class);
            startActivity(mainIntent);
    }
}