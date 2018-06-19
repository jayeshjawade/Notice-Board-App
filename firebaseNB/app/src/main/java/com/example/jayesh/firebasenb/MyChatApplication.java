package com.example.jayesh.firebasenb;


import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Jayesh on 12/01/18.
 */
public class MyChatApplication extends android.app.Application{

    public void onCreate(){
        super.onCreate();
        FirebaseDatabase instance = FirebaseDatabase.getInstance();
        instance.setPersistenceEnabled(true);
        instance.getReference().keepSynced(true);
    }
}
