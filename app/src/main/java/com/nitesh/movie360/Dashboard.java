package com.nitesh.movie360;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.interfaces.ItemClickListener;
import com.denzcoskun.imageslider.models.SlideModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {



    List<SlideModel> slideModels;


    List<String> slidertitle;
    List<String> sliderdescription;
    List<String> sliderposter;
    List<String> slideryear;
    List<String> sliderquality;
    List<String> slidercategory;



    SearchAdapter moviesadapter;
    SearchAdapter seriesadapter;

    RecyclerView movierecycler;
    RecyclerView seriesrecycler;


    InterstitialAd interstitialAd;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        final ImageSlider imageSlider = findViewById(R.id.image_slider);

        movierecycler = findViewById(R.id.movie_recycler);
        seriesrecycler = findViewById(R.id.series_recycler);

        slideModels = new ArrayList<>();

        slidertitle = new ArrayList<>();
        sliderdescription = new ArrayList<>();
        slideryear = new ArrayList<>();
        sliderquality = new ArrayList<>();
        sliderposter = new ArrayList<>();
        slidercategory = new ArrayList<>();

        interstitialAd = new InterstitialAd(Dashboard.this);

        interstitialAd.setAdUnitId("ca-app-pub-6725465197219280/6361069346");

        interstitialAd.loadAd(new AdRequest.Builder().build());





        Query sliderquery = FirebaseDatabase.getInstance().getReference().child("Movies").limitToLast(4);



        sliderquery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {

                for (DataSnapshot data : dataSnapshot.getChildren()){


                    slideModels.add(new SlideModel(data.child("pic").getValue().toString(), data.child("title").getValue().toString()));

                    slidertitle.add(data.child("title").getValue().toString());
                    sliderdescription.add(data.child("description").getValue().toString());
                    sliderquality.add(data.child("quality").getValue().toString());
                    slideryear.add(data.child("year").getValue().toString());
                    slidercategory.add(data.child("category").getValue().toString());
                    sliderposter.add(data.child("pic").getValue().toString());




                }

                imageSlider.setImageList(slideModels,true);
                imageSlider.setVisibility(View.VISIBLE);






                imageSlider.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onItemSelected(int i) {

                        final Intent detail = new Intent(Dashboard.this,ContentDetail.class);


                        detail.putExtra("detailposter",sliderposter.get(i));
                        detail.putExtra("detailtitle",slidertitle.get(i));
                        detail.putExtra("detailyear",slideryear.get(i));
                        detail.putExtra("detailquality",sliderquality.get(i));
                        detail.putExtra("detaildescription",sliderdescription.get(i));
                        detail.putExtra("detailcategory",slidercategory.get(i));



                        interstitialAd.setAdListener(new AdListener(){

                            @Override
                            public void onAdClosed() {
                                super.onAdClosed();
                                startActivity(detail);
                                interstitialAd.loadAd(new AdRequest.Builder().build());

                            }
                        });



                        if (interstitialAd.isLoaded()){

                            interstitialAd.show();
                        }

                        else{
                            startActivity(detail);
                        }





                    }
                });





            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        imageSlider.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemSelected(int i) {

            }
        });







        FirebaseRecyclerOptions<Content> movieoptions =
                new FirebaseRecyclerOptions.Builder<Content>()
                        .setQuery(FirebaseDatabase.getInstance()
                                .getReference().child("Movies")
                                .orderByChild("category").equalTo("movie"), Content.class)
                        .build();


        moviesadapter = new SearchAdapter(movieoptions);



        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                LinearLayoutManager movieLayoutManager = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL,true);

                movieLayoutManager.setStackFromEnd(true);

                movierecycler.setLayoutManager(movieLayoutManager);


                movierecycler.setAdapter(moviesadapter);



                moviesadapter.startListening();
                moviesadapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        FirebaseRecyclerOptions<Content> seriesoptions =
                new FirebaseRecyclerOptions.Builder<Content>()
                        .setQuery(FirebaseDatabase.getInstance()
                                .getReference().child("Movies")
                                .orderByChild("category").equalTo("series"), Content.class)
                        .build();


        seriesadapter = new SearchAdapter(seriesoptions);




        FirebaseDatabase.getInstance().getReference().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                LinearLayoutManager seriesLayoutManager = new LinearLayoutManager(Dashboard.this, LinearLayoutManager.HORIZONTAL,true);


                seriesLayoutManager.setStackFromEnd(true);


                seriesrecycler.setLayoutManager(seriesLayoutManager);


                seriesrecycler.setAdapter(seriesadapter);







                seriesadapter.startListening();
                seriesadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dashboard_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.searchmenu:
                startActivity(new Intent(Dashboard.this,Search.class));
                break;


            case R.id.requestmenu:

                startActivity(new Intent(Dashboard.this,RequestContent.class));
                break;

            case R.id.sharemenu:


                FirebaseDatabase.getInstance().getReference().child("ver").child("updatelink").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String url = dataSnapshot.getValue().toString();

                        Intent sharingIntent = new Intent();

                        sharingIntent.setType("text/plain");
                        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, "*Watch Latest Movies and Shows for Free only on _Movie360_ App *\n\n------------------------------\n\n*Download Now* - \n\n" + url);
                        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
                        startActivity(Intent.createChooser(sharingIntent, "Share using"));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });




                break;

        }

        return true;
    }












}
