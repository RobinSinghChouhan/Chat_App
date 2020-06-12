package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetailedChatActivity extends AppCompatActivity {
    DatabaseReference databaseChats;
    DatabaseReference databaseRecentsFrom,databaseRecentsTo;
    ListView chatList;
    EditText chat_message;
    Button send;
    String recent_id;
    String recent_email;
    ProgressBar loading;
    ChatAdapter adapter;
    List<Chat> chat_msg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_chat);
        chatList=findViewById(R.id.chats_in_order);
        send=findViewById(R.id.add_chat_db);
        loading=findViewById(R.id.loading);
        chat_message=findViewById(R.id.chat_message);
        databaseChats= FirebaseDatabase.getInstance().getReference("CHATS");
        databaseRecentsFrom=FirebaseDatabase.getInstance().getReference("Recents");
        databaseRecentsTo=FirebaseDatabase.getInstance().getReference("Recents");
        chat_msg=new ArrayList<>();
        Intent intent=getIntent();
        String id=intent.getExtras().getString("table_name");
        recent_id=intent.getExtras().getString("chat_to_id");
        recent_email=intent.getExtras().getString("chat_to_email");
        getSupportActionBar().setTitle(""+recent_email);
        databaseChats=databaseChats.child(id);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addChat();
            }
        });
        adapter=new ChatAdapter(DetailedChatActivity.this,chat_msg);
        chatList.setAdapter(adapter);
    }

    private void addChat(){
        String msg=chat_message.getText().toString();
        if(msg.isEmpty()){

        }else {
            if(chat_msg.size()==0){
                databaseRecentsFrom=databaseRecentsFrom.child(variables.currentuser);
             //   String key=databaseRecents.push().getKey();
                databaseRecentsTo=databaseRecentsTo.child(recent_id);
                RecentUsers recentUsers=new RecentUsers(recent_id,recent_email);
                databaseRecentsFrom.child(recent_id).setValue(recentUsers);
                RecentUsers new_recentUser=new RecentUsers(variables.currentuser,variables.currentEmail);
                databaseRecentsTo.child(variables.currentuser).setValue(new_recentUser);
            }
            String id = databaseChats.push().getKey();
            Chat chat = new Chat(id, variables.currentuser, msg);
            databaseChats.child(id).setValue(chat);
            chatList.post(new Runnable() {
                @Override
                public void run() {
                    chatList.setSelection(adapter.getCount() - 1);
                }
            });
            chat_message.setText("");
        }
    }


    @Override
    protected void onStart() {
        super.onStart();
        databaseChats.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chat_msg.clear();
            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                Chat chat= snapshot.getValue(Chat.class);
                chat_msg.add(chat);
            }
            adapter.notifyDataSetChanged();
            loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}