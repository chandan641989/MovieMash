package com.kartnap.chandan.moviemash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by Chandan on 8/7/2017.
 */

public class DetailActivity  extends AppCompatActivity{
    private TextView nameOfMovie,plotSynopsis,userRating,releaseDate1;
    private ImageView imageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initCollapsingToolbar();
        imageView = (ImageView)findViewById(R.id.thumbnail_image_header);
        nameOfMovie = (TextView)findViewById(R.id.title);
        plotSynopsis = (TextView)findViewById(R.id.plotsynopsis);
        userRating = (TextView)findViewById(R.id.userRating);
        releaseDate1 =(TextView)findViewById(R.id.releaseDate);
        Intent intentThatStartedActivity = getIntent();
        if (intentThatStartedActivity.hasExtra("original_title")){
            String thumbnail = getIntent().getStringExtra("poster_path");
            String movieName = getIntent().getStringExtra("original_title");
            String synopsis = getIntent().getStringExtra("overview");
            String releaseDate = getIntent().getStringExtra("release_date");
            String rating = getIntent().getStringExtra("vote_average");
            String movies = getIntent().getStringExtra("movies");


            Glide.with(this)
                        .load(thumbnail)
                    .placeholder(R.drawable.load)
                    .into(imageView);
            nameOfMovie.setText(movieName);
            plotSynopsis.setText(synopsis);
            userRating.setText(rating);
            releaseDate1.setText(releaseDate);

        }else {
            Toast.makeText(this,"No Data Comming..",Toast.LENGTH_LONG).show();
        }
    }
    private void initCollapsingToolbar(){
        final CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout)findViewById(R.id.collapsingToolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout)findViewById(R.id.appbar);
        appBarLayout.setEnabled(true);
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isshow = false;
            int scrollRange = -1;
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1){
                    scrollRange = appBarLayout.getTotalScrollRange();

                }
                if (scrollRange + verticalOffset == 0){
                    collapsingToolbar.setTitle(getString(R.string.movie_details));
                    isshow = true;

                }else if (isshow){
                    collapsingToolbar.setTitle("");
                    isshow = false;
                }

            }
        });
    }
}
