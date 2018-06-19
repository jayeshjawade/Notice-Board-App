package com.example.jayesh.firebasenb;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jayesh on 13-Jan-18.
 */
public class Tab32 extends MainActivity implements View.OnClickListener {

    private Button btnSend;
    private EditText edtMessage;
    private RecyclerView rvMessage;

    private AppPreference mAppPreference;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    public FirebaseRecyclerAdapter<Message, ChatViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab32);

        btnSend = (Button) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(this);

        edtMessage = (EditText) findViewById(R.id.edt_message);
        rvMessage = (RecyclerView) findViewById(R.id.rv_chat);
        rvMessage.setHasFixedSize(true);
        rvMessage.setLayoutManager(new LinearLayoutManager(this));

        mAppPreference = new AppPreference(this);
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        FirebaseRecyclerAdapter<Message, ChatViewHolder> adapter;

        adapter = new FirebaseRecyclerAdapter<Message, ChatViewHolder>(
                Message.class,
                R.layout.item_row_chat,
                ChatViewHolder.class,
                mDatabaseReference.child("chat")
        )
        {
        //   @Override
           public void populateViewHolder(ChatViewHolder viewHolder, Message model, int position)
            {
          //     viewHolder.tvMessage.setText(model.message);
            //   viewHolder.tvEmail.setText(model.sender);
            }
        };
        rvMessage.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_send){
            String message = edtMessage.getText().toString().trim();
            if (!TextUtils.isEmpty(message)){
                Map<String, Object> param = new HashMap<>();
                param.put("sender", mAppPreference.getEmail());
                param.put("message", message);

                mDatabaseReference.child("chat")
                        .push()
                        .setValue(param)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                edtMessage.setText("");
                                if(task.isSuccessful()){
                                    Log.d("SendMessage", "Success");
                                }else{
                                    Log.d("SendMessage", "failed ");
                                }
                            }
                        });
            }
        }
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView tvEmail, tvMessage;

        public ChatViewHolder(View itemView) {
            super(itemView);

            tvEmail = (TextView) itemView.findViewById(R.id.tv_sender);
            tvMessage = (TextView) itemView.findViewById(R.id.tv_message);
        }
    }
}
