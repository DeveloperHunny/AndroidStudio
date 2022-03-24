package com.example.chatapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MessageAdapter extends BaseAdapter {

    ArrayList<SampleMessage> messageList;
    LayoutInflater layoutInflater;

    public MessageAdapter(ArrayList<SampleMessage> messageList, Context mContext){
        this.messageList = messageList;
        this.layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return messageList.size();
    }

    @Override
    public Object getItem(int i) {
        return messageList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        SampleMessage sampleMessage = messageList.get(i);
        View inflateView = layoutInflater.inflate(R.layout.samplemessage_layout,viewGroup,false);

        TextView textView_name = (TextView) inflateView.findViewById(R.id.textView_name);
        TextView textView_message = (TextView) inflateView.findViewById(R.id.textView_message);

        textView_name.setText(sampleMessage.getName());
        textView_message.setText(sampleMessage.getMessage());

        return inflateView;
    }
}
