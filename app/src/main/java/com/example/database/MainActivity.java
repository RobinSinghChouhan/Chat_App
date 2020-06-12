package com.example.database;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ListView recents;
    DatabaseReference databaseRecents,databaseChatUsers;
    FloatingActionButton newChat;
    List<User> recentUserList;
    public static List<ChatUser> chatUsers;
    AllContactAdapter adapter;
    ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        getSupportActionBar().setTitle("RECENTS");
        recents = findViewById(R.id.recents);
        newChat = findViewById(R.id.new_contact);
        recentUserList=new ArrayList<>();
        chatUsers=new ArrayList<>();
        loading=findViewById(R.id.loading);
        loadChatUsers();
        databaseRecents=FirebaseDatabase.getInstance().getReference("Recents");
        databaseRecents=databaseRecents.child(variables.currentuser);
        newChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, NewChat.class));
            }
        });
        adapter=new AllContactAdapter(this,recentUserList);
        recents.setAdapter(adapter);
    }


    public void loadChatUsers(){
        databaseChatUsers= FirebaseDatabase.getInstance().getReference("ChatUsers");
        databaseChatUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                chatUsers.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    ChatUser cuser=snapshot.getValue(ChatUser.class);
                    chatUsers.add(cuser);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    protected void onStart() {
        databaseRecents.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                recentUserList.clear();
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User recentUsers=snapshot.getValue(User.class);
                        recentUserList.add(recentUsers);

                }
                adapter.notifyDataSetChanged();
                loading.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        super.onStart();

    }
}
