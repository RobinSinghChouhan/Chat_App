package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class RecentsAdapter extends ArrayAdapter<RecentUsers> {
    Activity context;
    List<RecentUsers> recentUsersList;
    public RecentsAdapter(@NonNull Activity context, List<RecentUsers> recentUsersList) {
        super(context, R.layout.recent_chat_layout);
        this.context=context;
        this.recentUsersList=recentUsersList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view;
        view=inflater.inflate(R.layout.recent_chat_layout,null,true);
        TextView textView=view.findViewById(R.id.recent_name);
        RecentUsers recentUsers=recentUsersList.get(position);
        textView.setText(recentUsers.getEmail());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,DetailedChatActivity.class);
//                intent.putExtra("table_name",chat_name);
//                intent.putExtra("chat_to_id",user.getId());
//                intent.putExtra("chat_to_email",user.getEmail());
                context.startActivity(intent);
            }
        });
        return view;
    }
}
