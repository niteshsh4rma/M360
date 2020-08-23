package com.nitesh.movie360;

import android.app.Activity;

import com.nitesh.movie360.Dashboard.*;


import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;


public class SearchAdapter extends FirebaseRecyclerAdapter<Content, SearchAdapter.SearchViewHolder> {










    public SearchAdapter(@NonNull FirebaseRecyclerOptions<Content> options) { super(options); }

    @Override
    protected void onBindViewHolder(@NonNull SearchViewHolder holder, int position, @NonNull final Content model) {
        Picasso.get().load(model.getPic()).into(holder.poster);

        holder.contentname.setText(model.getTitle());







        holder.poster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                final Intent detail = new Intent(v.getContext(), ContentDetail.class);

                detail.putExtra("detailposter",model.getPic());
                detail.putExtra("detailtitle",model.getTitle());
                detail.putExtra("detailyear",model.getYear());
                detail.putExtra("detailquality",model.getQuality());
                detail.putExtra("detaildescription",model.getDescription());
                detail.putExtra("detailcategory",model.getCategory());


                if (model.getCategory() != "series"){
                    detail.putExtra("detailvideo", model.getVideo());
                }






                    v.getContext().startActivity(detail);

            }
        });





    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.content_item,parent,false);
        return new SearchViewHolder(view);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{


        ImageView poster;
        TextView contentname;


        public SearchViewHolder(@NonNull View itemView) {
            super(itemView);


            poster = itemView.findViewById(R.id.posterview);
            contentname = itemView.findViewById(R.id.postertext);



        }
    }










}
