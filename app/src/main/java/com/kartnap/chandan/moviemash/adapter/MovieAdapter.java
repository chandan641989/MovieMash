package com.kartnap.chandan.moviemash.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.SerializedName;
import com.kartnap.chandan.moviemash.DetailActivity;
import com.kartnap.chandan.moviemash.MainActivity;
import com.kartnap.chandan.moviemash.R;
import com.kartnap.chandan.moviemash.model.Movie;

import java.util.List;

/**
 * Created by Chandan on 8/7/2017.
 */

public class MovieAdapter  extends RecyclerView.Adapter<MovieAdapter.MyViewHolder>{


    private Context mContext;
    private List<Movie> movieList;
    public MovieAdapter(Context mContext,List<Movie> movieList)
    {
        this.mContext = mContext;
        this.movieList = movieList;
    }
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_card,parent,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieAdapter.MyViewHolder holder, int position) {
        holder.movietitle.setText(movieList.get(position).getOrginalTitle());
        String vote = Double.toString(movieList.get(position).getVoteAverage());
        holder.Rating.setText(vote);
        Glide.with(mContext).load(movieList.get(position).getPosterPath())
                .placeholder(R.drawable.load)
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView movietitle,Rating;
        public ImageView image;

        public MyViewHolder(View itemView) {

            super(itemView);
            movietitle = (TextView)itemView.findViewById(R.id.title);
            Rating = (TextView)itemView.findViewById(R.id.userrating);
            image = (ImageView)itemView.findViewById(R.id.thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION){
                        Movie clickedDataItem = movieList.get(pos);
                        Intent in = new Intent(mContext, DetailActivity.class);
                        in.putExtra("original_title",movieList.get(pos).getOrginalTitle());
                        in.putExtra("poster_path",movieList.get(pos).getPosterPath());
                        in.putExtra("overview",movieList.get(pos).getOverview());
                        in.putExtra("vote_average",Double.toString(movieList.get(pos).getVoteAverage()));
                        in.putExtra("movies", clickedDataItem.toString());
                        in.putExtra("release_date",movieList.get(pos).getReleaaseDate());
                        in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        mContext.startActivity(in);
                        Toast.makeText(v.getContext(), "Selected " + clickedDataItem.getOrginalTitle(), Toast.LENGTH_SHORT).show();


                    }
                }
            });

        }
    }
}
