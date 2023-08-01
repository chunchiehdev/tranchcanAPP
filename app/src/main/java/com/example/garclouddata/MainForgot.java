package com.example.garclouddata;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainForgot extends AppCompatActivity {

    String password_id,password_id_password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("重新設定密碼");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        Button CreatePwd = (Button) findViewById(R.id.CreatePwd);
        TextView EnterPwd = (TextView) findViewById(R.id.EnterPwd);
        TextView ConfirmPwd = (TextView) findViewById(R.id.ConfirmPwd);

        CreatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EnterPwd.getText().toString().equals("") && ConfirmPwd.getText().toString().equals("")) {
                    Toast.makeText(MainForgot.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
                } else if (EnterPwd.getText().toString().equals(ConfirmPwd.getText().toString())) {
                    FirebaseFirestore.getInstance()
                            .collection("Management_System")
                            .document("data")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if(task.isSuccessful()) {
                                        DocumentSnapshot documentSnapshot = task.getResult();
                                        String pwd = documentSnapshot.getString("password");
                                        if(pwd.equals(EnterPwd.getText().toString())){
                                            Toast.makeText(MainForgot.this, "密碼未變更", Toast.LENGTH_SHORT).show();
                                        }else {
                                            final DocumentReference update_password = FirebaseFirestore
                                                    .getInstance()
                                                    .collection("Management_System").document("data");
                                            String EnterPwd1 = EnterPwd.getText().toString();
                                            Map<String, Object> map = new HashMap<>();
                                            map.put("password", EnterPwd1);
                                            update_password.update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(MainForgot.this, "重建密碼成功, 請輸入新密碼", Toast.LENGTH_SHORT).show();
                                                    Intent intent = new Intent();
                                                    intent.setClass(MainForgot.this, MainManage.class);
                                                    startActivity(intent);
                                                }
                                            }).addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(MainForgot.this, "重建密碼失敗 ", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                        }
                                    }

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });

                }else{
                    Toast.makeText(MainForgot.this, "重建密碼失敗,兩者不一致", Toast.LENGTH_SHORT).show();
                    EnterPwd.setText(null);
                    ConfirmPwd.setText(null);
                }
            }
        });
    }
}
