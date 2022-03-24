
package com.example.chatapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;

public class TestActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private DatabaseReference databaseRef;

    private EditText editText_input;
    private Button sendBtn;
    private ListView listVIew;

    private ArrayList<SampleMessage> messageList;
    private MessageAdapter messageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        database = FirebaseDatabase.getInstance("https://chatapp-63293-default-rtdb.firebaseio.com/");
        databaseRef = database.getReference("ChatList").child("testChat");

        editText_input = (EditText) findViewById(R.id.editText_input);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        listVIew = (ListView) findViewById(R.id.chatListView);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(messageList,TestActivity.this);
        listVIew.setAdapter(messageAdapter);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnClick_sendBtn();
            }
        });

        ValueEventListener messageListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //하나씩 가져오기
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    SampleMessage messageItem = snapshot.getValue(SampleMessage.class);
                    messageList.add(messageItem);
                }
                messageAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TEST : " , "getting message is failed");
            }
        };

        databaseRef.addValueEventListener(messageListener);

    }

    public void OnClick_sendBtn(){
        String message = editText_input.getText().toString();

        databaseRef.push().setValue(new SampleMessage("TEST USER", message));

        editText_input.setText("");
    }

}