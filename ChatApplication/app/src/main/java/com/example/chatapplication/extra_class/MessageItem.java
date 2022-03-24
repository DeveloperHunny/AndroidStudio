package com.example.chatapplication.extra_class;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.chatapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MessageItem {

    private String nickname;
    private String message;
    private String time;
    private String profileUri;

    public MessageItem(String name, String message, String time, String pofileUrl) {
        this.nickname = name;
        this.message = message;
        this.time = time;
        this.profileUri = pofileUrl;
    }



    //firebase DB에 객체로 값을 읽어올 때..
    //파라미터가 비어있는 생성자가 핑요함.
    public MessageItem() {
    }

    public String getProfileUri() {
        return profileUri;
    }

    public String getNickname() {
        return nickname;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public void setProfileUri(String profileUri) {
        this.profileUri = profileUri;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
