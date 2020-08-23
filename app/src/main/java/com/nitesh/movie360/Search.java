package com.nitesh.movie360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Search extends AppCompatActivity {


    TextInputEditText searchtext;

    SearchAdapter searchAdapter;

    RecyclerView recyclerView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);


        searchtext = findViewById(R.id.search);
        recyclerView = findViewById(R.id.search_recycler);



        searchtext.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEARCH){

                    loadData(v.getText().toString());


                    return true;
                }

                return false;
            }
        });

    }




    public void loadData(String searchtext){

        FirebaseRecyclerOptions<Content> options =
                new FirebaseRecyclerOptions.Builder<Content>()
                .setQuery(FirebaseDatabase.getInstance()
                        .getReference().child("Movies")
                        .orderByChild("search")
                        .startAt(searchtext.toLowerCase())
                        .endAt(searchtext.toLowerCase() + "\uf8f8"), Content.class)
                        .build();


        searchAdapter = new SearchAdapter(options);



        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GridLayoutManager gridLayoutManager = new GridLayoutManager(Search.this,3, LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setAdapter(searchAdapter);


                searchAdapter.startListening();
                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }















}
