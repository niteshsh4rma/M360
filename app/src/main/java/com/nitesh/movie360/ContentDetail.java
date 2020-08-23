package com.nitesh.movie360;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
public class ContentDetail extends AppCompatActivity {


    ImageView detailthumbnail;
    ImageView detailposter;
    TextView detailtitle;
    ImageView playbutton;
    TextView detaildescription;
    TextView detailyear;
    TextView detailquality;
    ImageView detailepisodeListBlockSide;
    TextView detailepisodeList;

    Spinner episodespinner;
    RelativeLayout episodeLyout;

    private String detailvideo;

    private AdView mAdView;
    private InterstitialAd interstitialAd;



    private boolean check = true;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_detail);
        final Intent inormal = getIntent();
        detailthumbnail = findViewById(R.id.detailthumbnail);
        detailposter = findViewById(R.id.detailposter);
        detailtitle = findViewById(R.id.detailtitle);
        detailyear = findViewById(R.id.detailyear);
        detailquality = findViewById(R.id.detailquality);
        detaildescription = findViewById(R.id.detaildescription);
        detailepisodeListBlockSide = findViewById(R.id.detailepisodeListBlockSide);
        detailepisodeList = findViewById(R.id.detailepisodeList);
        playbutton = findViewById(R.id.play_icon);
        episodeLyout = findViewById(R.id.episodelist);
        episodespinner = findViewById(R.id.episodespinner2);
        mAdView = findViewById(R.id.adView);







        final ArrayList<String> episodetitle = new ArrayList<>();
        final ArrayList<String> episodelink = new ArrayList<>();

        episodetitle.add("Choose Episode");



        Picasso.get().load(inormal.getExtras().getString("detailposter")).into(detailthumbnail);
        Picasso.get().load(inormal.getExtras().getString("detailposter")).into(detailposter);
        detailtitle.setText(inormal.getExtras().getString("detailtitle"));
        detailyear.setText(inormal.getExtras().getString("detailyear"));
        detailquality.setText(inormal.getExtras().getString("detailquality"));
        detaildescription.setText(inormal.getExtras().getString("detaildescription"));




        if (inormal.getExtras().getString("detailcategory").equals("movie")){
            detailepisodeList.setVisibility(View.GONE);
            detailepisodeListBlockSide.setVisibility(View.GONE);
            episodeLyout.setVisibility(View.GONE);
            FirebaseDatabase.getInstance().getReference().child("Movies").orderByChild("category").equalTo("movie").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot m : dataSnapshot.getChildren()){
                        if (m.child("title").getValue().toString().equals(inormal.getExtras().getString("detailtitle"))){
                            detailvideo = m.child("video").getValue().toString();
                            final Intent videointent = new Intent(ContentDetail.this,ContentPlayer.class);
                            videointent.putExtra("detailvideo",detailvideo);
                            playbutton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {



                                        startActivity(videointent);




                                }
                            });
                            break;
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else{
            playbutton.setVisibility(View.GONE);
            FirebaseDatabase.getInstance().getReference().child("Movies").orderByChild("category").equalTo("series").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Log.d("after", String.valueOf(dataSnapshot.getChildrenCount()));
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            if ( d.child("title").getValue().toString().equals(inormal.getExtras().getString("detailtitle"))){
                                for (DataSnapshot episode : d.child("episodeList").getChildren()){
                                    episodetitle.add(episode.getKey());
                                    episodelink.add(episode.getValue().toString());
                                }
                                break;
                            }
                        }
                    ArrayAdapter<String> spinneradapter = new ArrayAdapter<>(ContentDetail.this,R.layout.spinnertext,episodetitle);
                    spinneradapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
                    episodespinner.setAdapter(spinneradapter);
                    AdapterView.OnItemSelectedListener spinneritemlistener = new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (check){
                                check = false;
                            }
                            else{

                                if (position == 0){

                                }
                                else{

                                    detailvideo = episodelink.get(position - 1);

                                    final Intent videointent2 = new Intent(ContentDetail.this,ContentPlayer.class);

                                    videointent2.putExtra("detailvideo",detailvideo);




                                    startActivity(videointent2);



                                }


                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    };

                    episodespinner.setOnItemSelectedListener(spinneritemlistener);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }










        AdRequest adRequest = new AdRequest.Builder().build();

        mAdView.loadAd(adRequest);





    }


    @Override
    protected void onResume() {
        super.onResume();

        episodespinner.setSelection(0);

    }
}
