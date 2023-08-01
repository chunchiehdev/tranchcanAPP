package com.example.garclouddata;

import static android.content.ContentValues.TAG;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.api.LogDescriptor;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainChart extends AppCompatActivity {
    BarChart barChart;
    int plastic,glass,metal,other,total;

    FirebaseFirestore db;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart);
        barChart = findViewById(R.id.BarChart);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("各區域統計資料");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Count1();
    }
    public void Count1(){
        FirebaseFirestore.getInstance()
                .collection("Garbage_Chart")
                .get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()){
                    List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                    for(DocumentSnapshot snapshot : myListOfDocuments ){
                        total++;
                        if (snapshot.getString("name").equals("其它類")){
                            other++;
                        }else if (snapshot.getString("name").equals("塑膠類")){
                            plastic++;
                            Log.d(TAG, "為: "+ plastic+ snapshot.getString("name"));
                        }else if (snapshot.getString("name").equals("鐵鋁類")){
                            metal++;
                        }else if (snapshot.getString("name").equals("玻璃類")){
                            glass++;
                        }

                    }
                    ArrayList<BarEntry> data = new ArrayList<>();
                    data.add(new BarEntry(0,total));
                    data.add(new BarEntry(2,plastic));
                    data.add(new BarEntry(4,glass));
                    data.add(new BarEntry(6,metal));
                    data.add(new BarEntry(8,other));
                    BarDataSet barDataSet = new BarDataSet(data,"Total、Plastic、Glass、Metal、Other");
                    barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
                    barDataSet.setValueTextColor(Color.BLACK);
                    BarData barData = new BarData(barDataSet);
                    barDataSet.setValueTextSize(16f);
                    barChart.setFitBars(true);
                    barChart.setData(barData);
                    barChart.invalidate();
                }
            }
        });
    }
}