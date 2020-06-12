package com.example.database;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<Chat> {
    private Activity context;
    private List<Chat> chat_msg;
    public ChatAdapter(@NonNull Activity context, List<Chat> chat_msg) {
        super(context, R.layout.chat_from,chat_msg);
        this.context=context;
        this.chat_msg=chat_msg;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View view;
        if(chat_msg.get(position).getFrom().equals(variables.currentuser)){
            view=inflater.inflate(R.layout.chat_from,null,true);
        }else{
            view=inflater.inflate(R.layout.chat_to,null,true);
        }
        TextView textView=view.findViewById(R.id.chat_text);
        textView.setText(chat_msg.get(position).getChat());
        return view;
    }
}
