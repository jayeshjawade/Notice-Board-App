package com.example.jayesh.firebasenb;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Jayesh on 17-Feb-18.
 */

public class About extends MainActivity {
    ImageView b1,b2,b3;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);
        b1 =(ImageView) findViewById(R.id.fb);
        b1.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                String url = "https://www.facebook.com/gcoeanews/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
        b2 =(ImageView) findViewById(R.id.insta);
        b2.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                String url = "https://www.instagram.com/gcoea_family/";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        b3 =(ImageView) findViewById(R.id.web);
        b3.setOnClickListener(new View.OnClickListener() {
            //Start new list activity
            public void onClick(View v) {
                String url = "http://gcoea.ac.in/home";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

    }
}
