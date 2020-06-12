package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class NewChat extends AppCompatActivity {
    DatabaseReference databaseUser,databaseChatUsers;
    ArrayList<User> allUsers;
  //  public static ArrayList<ChatUser> chatUsers;
    ListView newChat;
    AllContactAdapter adapter;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_chat);
        getSupportActionBar().setTitle("Start a new Chat");
        newChat=findViewById(R.id.new_chat);
        allUsers=new ArrayList<>();
//        chatUsers=new ArrayList<>();
        databaseUser= FirebaseDatabase.getInstance().getReference("Users");
       // loadChatUsers();
        loading=findViewById(R.id.loading);
        adapter=new AllContactAdapter(NewChat.this,allUsers);
       // Log.i("DATAS:",allUsers.get(0).getEmail());
        newChat.setAdapter(adapter);
    }

//    public void loadChatUsers(){
//        databaseChatUsers= FirebaseDatabase.getInstance().getReference("ChatUsers");
//        databaseChatUsers.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                chatUsers.clear();
//                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
//                    ChatUser cuser=snapshot.getValue(ChatUser.class);
//                    chatUsers.add(cuser);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
//    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                allUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user=snapshot.getValue(User.class);
                    Log.i("DATA:",user.getEmail());
                    if(!user.getId().equals(variables.currentuser))
                    {allUsers.add(user);}
                }
                Log.i("SIZE:",String.valueOf(allUsers.size()));
                adapter.notifyDataSetChanged();
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}