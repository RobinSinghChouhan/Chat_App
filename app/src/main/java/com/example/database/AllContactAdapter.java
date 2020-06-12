package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static com.example.database.MainActivity.chatUsers;

public class AllContactAdapter extends ArrayAdapter<User> {
    private Activity context;
    private List<User> allUser;
    DatabaseReference databaseChatUsers;
    String chat_name;
    public AllContactAdapter(@NonNull Activity context, List<User> allUser) {
        super(context, R.layout.recent_chat_layout,allUser);
        this.context=context;
        this.allUser=allUser;
    }

    @NonNull
    @Override
    public View getView( int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view;
        view=inflater.inflate(R.layout.recent_chat_layout,null,true);
        final TextView name=view.findViewById(R.id.recent_name);
        final User user=allUser.get(position);
        name.setText(user.getEmail());
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("CHAT_TO:",user.getId());
                chat_name=variables.currentuser+user.getId();
                databaseChatUsers=FirebaseDatabase.getInstance().getReference("ChatUsers");
                Log.i("SIZE_OF_CHATUSER:",chatUsers.size()+"     yes");
                boolean isChatUser=false;
                for(int i=0;i<chatUsers.size();i++){
                    if((variables.currentuser+user.getId()).equals(chatUsers.get(i).getChat_table_name()) ||
                            (user.getId()+variables.currentuser).equals(chatUsers.get(i).getChat_table_name())){
                        isChatUser=true;
                        chat_name=chatUsers.get(i).getChat_table_name();
                    }
                }
                if(!isChatUser){
                    chat_name=variables.currentuser+user.getId();
                    String id=databaseChatUsers.push().getKey();
                    ChatUser chatUser=new ChatUser(chat_name,id);
                    databaseChatUsers.child(id).setValue(chatUser);
                    isChatUser=false;
                }
                Intent intent=new Intent(context,DetailedChatActivity.class);
                intent.putExtra("table_name",chat_name);
                intent.putExtra("chat_to_id",user.getId());
                intent.putExtra("chat_to_email",user.getEmail());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
