package com.example.garclouddata;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Modify_About extends AppCompatActivity {
    TextView mEditTextTo;
    Button enter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_about);
        mEditTextTo = findViewById(R.id.edit_text_to);
        enter = findViewById(R.id.enter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("修改關於我們");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        FirebaseFirestore.getInstance()
                .collection("Management_System")
                .document("data")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot documentSnapshot = task.getResult();
                            String text = documentSnapshot.getString("about");
                            Log.d(TAG, "為 " + text);
                            mEditTextTo.setText(text);
                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
        enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String e = mEditTextTo.getText().toString();
                if (e.equals(null)) {
                    Toast.makeText(Modify_About.this, "請輸入內容", Toast.LENGTH_SHORT).show();
                } else {
                    final DocumentReference update_password = FirebaseFirestore
                            .getInstance()
                            .collection("Management_System")
                            .document("data");
                    String text = mEditTextTo.getText().toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("about", text);
                    update_password.update(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Modify_About.this, "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Modify_About.this, "修改失敗 ", Toast.LENGTH_SHORT).show();
                            mEditTextTo.setText(null);
                        }
                    });

                }

            }
        });




    }
}
