package com.kartnap.chandan.moviemash;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.support.v4.content.res.ConfigurationHelper;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kartnap.chandan.moviemash.adapter.MovieAdapter;
import com.kartnap.chandan.moviemash.api.Client;
import com.kartnap.chandan.moviemash.api.Service;
import com.kartnap.chandan.moviemash.model.Movie;
import com.kartnap.chandan.moviemash.model.MoviesResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList;
    private ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    public static final String LOG_TAG = MovieAdapter.class.getName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        swipeContainer = (SwipeRefreshLayout)findViewById(R.id.main_content);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                initView();
                Toast.makeText(getApplicationContext(),"Movie Refreshed", Toast.LENGTH_LONG).show();
            }
        });
    }
    public Activity getActivity(){
        Context  context= this;
        while (context instanceof ContextWrapper){
            if (context instanceof Activity){
                return (Activity)context;
            }
            context=((ContextWrapper)context).getBaseContext();

        }
        return null;
    }
    private void initView(){
        pd= new ProgressDialog(this);
        pd.setMessage("Fetching Movie...");
        pd.setCancelable(false);
        pd.show();
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        movieList = new ArrayList<>();
        adapter = new MovieAdapter(this,movieList);
        if (getActivity().getResources().getConfiguration().orientation== Configuration.ORIENTATION_PORTRAIT){
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        }else {
            recyclerView.setLayoutManager(new GridLayoutManager(this,4));
        }
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        loadJSON();


    }
    private void loadJSON(){
        try{
            if(BuildConfig.MY_MOVIE_DB_API_TOKEN.isEmpty()){
                Toast.makeText(getApplicationContext(),"Please Obtain api from MovieDB",Toast.LENGTH_LONG).show();
                pd.dismiss();
                return;
            }
            Client client = new Client();
            Service apiservice = client.getClient().create(Service.class);
            Call<MoviesResponse> call = apiservice.getPopularMovie(BuildConfig.MY_MOVIE_DB_API_TOKEN);
            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {
                    List<Movie> movies = response.body().getResults();
                    recyclerView.setAdapter(new MovieAdapter(getApplicationContext(),movies));
                    recyclerView.smoothScrollToPosition(0);
                    if (swipeContainer.isRefreshing()){
                        swipeContainer.setRefreshing(false);

                    }
                    pd.dismiss();
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Log.d("Error",t.getMessage());
                    Toast.makeText(getApplicationContext(),"Error Fetching Data..",Toast.LENGTH_LONG).show();


                }
            });
        }catch (Exception e){Log.d("Error",e.getMessage());
            Toast.makeText(getApplicationContext(),"Error Fetching Data.."+e.getMessage().toString(),Toast.LENGTH_LONG).show();

        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.menu_settings:
                return true;
            default:
                super.onOptionsItemSelected(item);
                return true;

        }

    }
}
