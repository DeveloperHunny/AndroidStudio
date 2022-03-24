package com.example.chatapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chatapplication.extra_class.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class FirstLoginActivity extends AppCompatActivity {

    User user;
    Uri imgUri;
    CircleImageView user_profile;
    Button complete_btn;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_login);

        user_profile = (CircleImageView)findViewById(R.id.user_profile);
        complete_btn = (Button)findViewById(R.id.complete_btn);
        editText = (EditText)findViewById(R.id.et_nickname);

        user = new User();

        Intent intent = getIntent();
        user = (User)intent.getParcelableExtra("User");


        complete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData_changeActivity();
            }
        });

    }


    public void ClickCirecleImageView(View view){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        intent.setType("image/*"); //앨범열기

        startActivityForResult(intent,10);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("test", "here1");
        switch (requestCode){
            case 10:
                if(resultCode==RESULT_OK){
                    imgUri = data.getData();
                    //Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imgUri);

                    Glide.with(this).load(imgUri).into(user_profile);
                    //glide는 추가 퍼미션이 필요하고 이를 위해 동적 퍼미션이 필요해서 귀찮으므로 Picasso 이용
                }
                else{
                    Log.e("test", "here2");
                }
                break;
        }
    }

    public void saveData_changeActivity(){
        user.setNickname(editText.getText().toString()); //nickname 설정

        if(imgUri==null){ //이미지를 선택하지 않았을 경우

            //Firebase Storage에 있는 defaulticon 가져옴
            FirebaseStorage firebaseStorage =FirebaseStorage.getInstance();
            final StorageReference imgRef = firebaseStorage.getReferenceFromUrl("gs://chatapp-63293.appspot.com/profileImage/default_icon.png");

            //Firebase Storage에서 파일 다운로드 URL 얻어오기
            imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    user.setProfileUri(uri.toString()); //profileUri 저장

                    //firebasefirestore user 정보 변경
                    SetUserInfo(user);
                }
            });
        }
        else{
            final String fileName = user.getEmail() + "_profile" + ".png";

            //Firebase Storage에 저장
            FirebaseStorage firebaseStorage =FirebaseStorage.getInstance();
            final StorageReference imgRef = firebaseStorage.getReference("profileImage/" + fileName);

            //파일 업로드
            UploadTask uploadTask = imgRef.putFile(imgUri);
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //이미지가 성공적으로 업로드가 됨.
                    //Firebase Storage에서 파일 다운로드 URL 얻어오기
                    imgRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            user.setProfileUri(uri.toString()); //profileUri 저장
                            Toast.makeText(FirstLoginActivity.this, "프로필 저장 완료", Toast.LENGTH_SHORT).show();

                            //firebasefirestore user 정보 변경
                            SetUserInfo(user);


                        }
                    });

                }
            });

        }




    }

    private void SetUserInfo(User user){
        //firebasefirestore user 정보 변경
        final FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();

        firebaseFirestore.collection("users").document(user.getEmail()).update("profileUri",user.getProfileUri());
        firebaseFirestore.collection("users").document(user.getEmail()).update("nickname",user.getNickname());

        //저장이 완료되었으니 ChatActivity로 전환
        Intent intent=new Intent(FirstLoginActivity.this, MainActivity.class);
        intent.putExtra("User",user);
        startActivity(intent);
        finish();
    }



}
