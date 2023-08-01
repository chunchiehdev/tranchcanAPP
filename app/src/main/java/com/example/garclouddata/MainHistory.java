package com.example.garclouddata;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.sql.Date;

public class MainHistory extends AppCompatActivity {
    private RecyclerView mFirestoreList;
    private FirebaseFirestore firebaseFirestore;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("歷史資料");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference firebaseFirestore = db.collection("Garbage_History");
        mFirestoreList = findViewById(R.id.firestore_list);

        Query query = firebaseFirestore.orderBy("date",Query.Direction.DESCENDING);
        FirestoreRecyclerOptions<MainModel> options = new FirestoreRecyclerOptions.Builder<MainModel>()
                .setQuery(query,MainModel.class)
                .build();
        adapter = new FirestoreRecyclerAdapter<MainModel, MainViewHolder>(options) {
            @NonNull
            @Override
            public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_single,parent,false);
                return new MainViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MainViewHolder holder, int position, @NonNull MainModel model) {
                holder.name.setText(model.getName());
                holder.date.setText(String.valueOf(model.getDate() + ""));
                Glide.with(holder.imageView.getContext())
                        .load(model.getImg())
                        .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                        .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                        .into(holder.imageView);
            }
        };
        mFirestoreList.setHasFixedSize(true);
        mFirestoreList.setLayoutManager(new LinearLayoutManager(this));
        mFirestoreList.setAdapter(adapter);
    }

    private class MainViewHolder extends RecyclerView.ViewHolder{
        private TextView name,date;
        private ImageView imageView;
        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.type);
            date = itemView.findViewById(R.id.date);
            imageView = (ImageView)itemView.findViewById(R.id.img1);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

}
