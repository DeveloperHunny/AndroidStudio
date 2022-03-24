package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.chatapplication.extra_class.ChatAdapter;
import com.example.chatapplication.extra_class.MessageItem;
import com.example.chatapplication.extra_class.User;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatActivity extends AppCompatActivity {

    User curUser;
    User otherUser;
    String chatName;

    EditText et;
    ListView listView;
    Button sendBtn;

    ArrayList<MessageItem> messageItems = new ArrayList<>();
    ChatAdapter chatAdapter;

    FirebaseDatabase database;
    DatabaseReference chatRef;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent = getIntent();
        curUser = intent.getParcelableExtra("curUser");
        otherUser = intent.getParcelableExtra("otherUser");
        chatName = intent.getStringExtra("Key");


        progressDialog = new ProgressDialog(this);

        progressDialog.show();

        et = (EditText)findViewById(R.id.et);
        listView = (ListView)findViewById(R.id.listview);
        sendBtn = (Button)findViewById(R.id.sendBtn);
        chatAdapter = new ChatAdapter(messageItems,getLayoutInflater(),curUser);
        listView.setAdapter(chatAdapter);


        //Firebase DB관리 객체와 'caht'노드 참조객체 얻어오기
        database = FirebaseDatabase.getInstance("https://chatapp-63293-default-rtdb.firebaseio.com/");
        chatRef= database.getReference("ChatList").child(chatName);

        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //새로 데이터 가져오기 전에 비우기
                messageItems.clear();
                //하나씩 가져오기
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    MessageItem messageItem = snapshot.getValue(MessageItem.class);
                    messageItems.add(messageItem);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        };

        chatRef.addValueEventListener(messageListener);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSend();
            }
        });

        progressDialog.dismiss();
    }

    public void clickSend(){
        String nickname = curUser.getNickname();
        String message = et.getText().toString();
        String profileUrl  = curUser.getProfileUri();

        //현재 시각 얻어오기
        Calendar calendar = Calendar.getInstance();
        String time = calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE);

        MessageItem messageItem = new MessageItem(nickname,message,time,profileUrl);

        chatRef.push().setValue(messageItem);

        et.setText("");

        //소프트키패드를 안보이도록..
        InputMethodManager imm=(InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);

    }
}
