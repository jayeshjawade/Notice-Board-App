package com.example.jayesh.firebasenb;

import android.os.*;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

/**
 * Created by Jayesh on 13-Jan-18.
 */

class FirebaseRecyclerAdapter<T, T1> extends RecyclerView.Adapter {
    public FirebaseRecyclerAdapter(Class<T> messageClass, int item_row_chat, Class<T1> chatViewHolderClass, DatabaseReference chat) {
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
