package com.example.garclouddata;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class Modify_connection extends AppCompatActivity {
    private EditText mEditTextSubject,mEditTextMessage;
    TextView mEditTextTo;
    Button enter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_connection);
        mEditTextTo = findViewById(R.id.edit_text_to);
        enter = findViewById(R.id.edit_enter);
        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);
        Button buttonSend = findViewById(R.id.btn_send);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("聯絡我們");
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
                            String mail = documentSnapshot.getString("mail");
                            Log.d(TAG, "為 " + mail);
                            mEditTextTo.setText(mail);
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
                if (!e.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(e).matches()) {
                    Toast.makeText(Modify_connection.this, "修改成功", Toast.LENGTH_SHORT).show();
                    final DocumentReference update_password = FirebaseFirestore
                            .getInstance()
                            .collection("Management_System")
                            .document("data");
                    String text = mEditTextTo.getText().toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("mail", text);
                    update_password.update(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(Modify_connection.this, "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Modify_connection.this, "重建密碼失敗 ", Toast.LENGTH_SHORT).show();
                            mEditTextTo.setText(null);
                        }
                    });

                } else {
                    Toast.makeText(Modify_connection.this, "輸入錯誤", Toast.LENGTH_SHORT).show();

                }

            }
        });
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();

            }
        });
    }
    private void sendMail(){
        String MList = mEditTextTo.getText().toString();
        String[] MLists = MList.split(",");

        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, MLists);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent, "寄信中.."));
    }
}
