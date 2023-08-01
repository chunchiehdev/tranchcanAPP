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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;
import java.util.Map;

public class MainManage extends AppCompatActivity {
    FirebaseFirestore db_password;
    String password_id_password,password_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("登入");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        TextView password = (TextView) findViewById(R.id.password);
        Button loginbtnn = (Button) findViewById(R.id.loginbtn);

        loginbtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance()
                        .collection("Management_System")
                        .document("data")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if(task.isSuccessful()){
                                    DocumentSnapshot documentSnapshot = task.getResult();
                                    String pwd = documentSnapshot.getString("password");
                                    Log.d(TAG, "為 " + password);
                                    if(password.getText().toString().equals(pwd)){
                                        Toast.makeText(MainManage.this, "登入成功", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent();
                                        intent.setClass(MainManage.this,DeleteData.class);
                                        startActivity(intent);
                                    }else if(password.getText().toString().equals("")){
                                        Toast.makeText(MainManage.this, "請輸入密碼", Toast.LENGTH_SHORT).show();
                                    }else
                                        Toast.makeText(MainManage.this, "密碼錯誤", Toast.LENGTH_SHORT).show();
                                    password.setText(null);

                                }

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }
        });
    }
}
