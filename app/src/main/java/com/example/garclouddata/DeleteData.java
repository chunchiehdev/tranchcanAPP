package com.example.garclouddata;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentId;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeleteData extends AppCompatActivity {
    Button clean_garbage,reset_chart,Modify_contact,Modify_About_us;
    FirebaseFirestore db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manage);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("後台管理系統");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        clean_garbage =findViewById(R.id.clean_garbage);
        reset_chart =findViewById(R.id.Reset);
        Modify_contact =findViewById(R.id.Modify_contact_information);
        Modify_About_us = findViewById(R.id.Modify_About_us);

        clean_garbage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance()
                        .collection("Garbage_Chart").get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()){

                                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                                    for(DocumentSnapshot snapshot : myListOfDocuments ){
                                        FirebaseFirestore.getInstance().collection("Garbage_Chart").document(snapshot.getId()).delete();
                                        Toast.makeText(DeleteData.this, "圖表重置成功", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(DeleteData.this, "圖表已經重置", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
        reset_chart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeleteData.this,MainForgot.class);
                startActivity(intent);
            }
        });
        Modify_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeleteData.this,Modify_connection.class);
                startActivity(intent);
            }
        });
        Modify_About_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(DeleteData.this,Modify_About.class);
                startActivity(intent);

            }
        });



    }
}
