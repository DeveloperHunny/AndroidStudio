package com.example.chatapplication.extra_class;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chatapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatAdapter extends BaseAdapter {

    ArrayList<MessageItem> messageItems;
    LayoutInflater layoutInflater;
    User user;

    public ChatAdapter(ArrayList<MessageItem> messageItems, LayoutInflater layoutInflater, User user) {
        this.messageItems = messageItems;
        this.layoutInflater = layoutInflater;
        this.user = user;
    }

    @Override
    public int getCount() {
        return messageItems.size();
    }

    @Override
    public Object getItem(int position) {
        return messageItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MessageItem messageItem = messageItems.get(position);

        View itemView = null;

        if (messageItem.getNickname().equals(user.getNickname())) {
            itemView = layoutInflater.inflate(R.layout.my_messagebox, parent, false);
        } else {
            itemView = layoutInflater.inflate(R.layout.other_messagebox, parent, false);
        }

        CircleImageView circleImageView = itemView.findViewById(R.id.profileImg);
        TextView tvName = itemView.findViewById(R.id.tv_name);
        TextView tvMsg = itemView.findViewById(R.id.tv_msg);
        TextView tvTime = itemView.findViewById(R.id.tv_time);

        tvMsg.setText(messageItem.getMessage());
        tvName.setText(messageItem.getNickname());
        tvTime.setText(messageItem.getTime());

        Glide.with(parent.getContext()).load(messageItem.getProfileUri()).into(circleImageView);

        return itemView;


    }
}

