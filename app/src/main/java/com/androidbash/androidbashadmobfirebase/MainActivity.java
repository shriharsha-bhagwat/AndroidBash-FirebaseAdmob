package com.androidbash.androidbashadmobfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    public static final String TEST_DEVICE_ID = "YOUR_DEVICE_ID";
    private AdView mBannerAd;
    private InterstitialAd mInterstitialAd;
    private RecyclerView mRecyclerView;
    private Toolbar mToolbar;
    private String clickedMovieName;

    public static List<Movie> listOfMovies = new ArrayList<>();

    static {
        listOfMovies.add(new Movie("Avatar 2",
                4252, R.drawable.avatar2));
        listOfMovies.add(new Movie("Power Rangers",
                9993, R.drawable.powerrangers));
        listOfMovies.add(new Movie("Beauty and the Beast",
                9654, R.drawable.beautyandthebeast));
        listOfMovies.add(new Movie("World War Z 2",
                1244, R.drawable.worldwarz2));
        listOfMovies.add(new Movie("Fate of the Furious",
                6324, R.drawable.fastandfurious));
        listOfMovies.add(new Movie("Fifty Shades Darker",
                4574, R.drawable.fiftyshadesdarker));
        listOfMovies.add(new Movie("Pirates of the Carribean",
                2542, R.drawable.pirates));
        listOfMovies.add(new Movie("Smurfs",
                6412, R.drawable.smurfs));
        listOfMovies.add(new Movie("Logan",
                3647, R.drawable.logan));
        listOfMovies.add(new Movie("Transformers",
                9475, R.drawable.transformers));
        listOfMovies.add(new Movie("Thor",
                9475, R.drawable.thorragnorak));
        listOfMovies.add(new Movie("Maze Runner Death Cure",
                9475, R.drawable.mazerunnerdeathcure));
        listOfMovies.add(new Movie("Spider Man Home Coming",
                9475, R.drawable.spidermanhomecoming));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mRecyclerView = (RecyclerView) findViewById(R.id.list_movies);
        mBannerAd = (AdView) findViewById(R.id.banner_ad);
        mInterstitialAd = new InterstitialAd(this);

        setSupportActionBar(mToolbar);

        //Populating the list of Movies
        MoviesAdapter moviesAdapter = new MoviesAdapter(
                this,
                listOfMovies,
                new MoviesAdapter.MovieItemClick() {
                    @Override
                    public void onMovieClick(Movie clickedMovie) {
                        clickedMovieName = clickedMovie.getmName();
                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                            //Sending the name of the clicked movie to next activity
                            intent.putExtra("clickedMovieName", clickedMovieName);
                            startActivity(intent);
                        }
                    }
                });
        mRecyclerView.setAdapter(moviesAdapter);

        //Banner Ad Request
        //Add the following code if you are testing on a real device
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        //You can add the following code if you are testing in an emulator
        /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();*/
        mBannerAd.loadAd(adRequest);
        mBannerAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                Toast.makeText(getApplicationContext(), "Closing the Banner Ad", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onAdLoaded() {

                Toast.makeText(getApplicationContext(), "Banner Ad is loaded", Toast.LENGTH_LONG).show();
            }
        });

        //Interstitial Ad Request
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_ad_unit_id));
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                requestNewInterstitial();
                Intent intent = new Intent(getApplicationContext(), MovieInfoActivity.class);
                intent.putExtra("clickedMovieName", clickedMovieName);
                startActivity(intent);
            }
        });

        requestNewInterstitial();
    }


    private void requestNewInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(TEST_DEVICE_ID)
                .build();
        //You can add the following code if you are testing in an emulator
        /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();*/
        mInterstitialAd.loadAd(adRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mBannerAd != null) {
            mBannerAd.resume();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mBannerAd != null) {
            mBannerAd.pause();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBannerAd != null) {
            mBannerAd.destroy();
        }
    }

}
