package com.example.jayesh.firebasenb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jayesh on 24-Feb-18.
 */

public class Developer extends MainActivity {
    ImageView b1,b2,b3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developer);

        b1 =(ImageView) findViewById(R.id.ws);
        b2 =(ImageView) findViewById(R.id.insta);
        b3 =(ImageView) findViewById(R.id.fb);

        b1.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                String url = "https://api.whatsapp.com/send?phone=918975171588";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                String url = "https://www.instagram.com/jayeshjawade_4ever";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                String url = "http://www.facebook.com/jayesh.jawade.02";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}
