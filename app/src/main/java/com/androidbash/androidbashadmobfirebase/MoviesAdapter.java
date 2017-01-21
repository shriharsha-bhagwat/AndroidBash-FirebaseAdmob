package com.androidbash.androidbashadmobfirebase;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.NativeExpressAdView;


import java.util.ArrayList;
import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final Context mContext;
    private List<Movie> mItems = new ArrayList<>();
    private MovieItemClick mItemClickListener;

    private static final int DEFAULT_VIEW_TYPE = 1;
    private static final int NATIVE_AD_VIEW_TYPE = 2;

    public MoviesAdapter(Context context,
                         List<Movie> movies,
                         MovieItemClick movieItemClick) {
        mContext = context;
        mItems = movies;
        mItemClickListener = movieItemClick;
    }

    @Override
    public int getItemViewType(int position) {
        if (position>1 && position % 3 == 0) {
            return NATIVE_AD_VIEW_TYPE;
        }
        return DEFAULT_VIEW_TYPE;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        switch (viewType) {
            default:
                view = layoutInflater
                        .inflate(R.layout.item_movie, parent, false);
                return new MyViewHolder(view);
            case NATIVE_AD_VIEW_TYPE:
                view = layoutInflater.inflate(R.layout.list_item_native_ad, parent, false);
                return new NativeAdViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (!(holder instanceof MyViewHolder)) {
            return;
        }

        Movie currentItem = mItems.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;

        myViewHolder.name.setText(currentItem.getmName());
        myViewHolder.likes.setText(""+currentItem.getLikes());
        myViewHolder.likeImage.setImageResource(R.drawable.ic_favorite_black_24dp);
        myViewHolder.likeImage.setVisibility(View.VISIBLE);

        Glide.with(mContext)
                .load(currentItem.getmImage())
                .centerCrop()
                .into(myViewHolder.movieImage);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements
            View.OnClickListener {
        public TextView name;
        public TextView likes;
        public ImageView likeImage;
        public ImageView movieImage;

        public MyViewHolder(View itemView) {
            super(itemView);
            likeImage=(ImageView)itemView.findViewById(R.id.iv_like_image);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            likes = (TextView) itemView.findViewById(R.id.tv_likes);
            movieImage = (ImageView) itemView.findViewById(R.id.iv_movie_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mItemClickListener.onMovieClick(mItems.get(position));
        }
    }

    public class NativeAdViewHolder extends RecyclerView.ViewHolder {

        private final NativeExpressAdView mNativeAd;

        public NativeAdViewHolder(View itemView) {
            super(itemView);
            mNativeAd = (NativeExpressAdView) itemView.findViewById(R.id.nativeAd);
            mNativeAd.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    super.onAdLoaded();
                    if (mItemClickListener != null) {
                        Log.i("AndroidBash", "onAdLoaded");
                    }
                }

                @Override
                public void onAdClosed() {
                    super.onAdClosed();
                    if (mItemClickListener != null) {
                        Log.i("AndroidBash", "onAdClosed");
                    }
                }

                @Override
                public void onAdFailedToLoad(int errorCode) {
                    super.onAdFailedToLoad(errorCode);
                    if (mItemClickListener != null) {
                        Log.i("AndroidBash", "onAdFailedToLoad");
                    }
                }

                @Override
                public void onAdLeftApplication() {
                    super.onAdLeftApplication();
                    if (mItemClickListener != null) {
                        Log.i("AndroidBash", "onAdLeftApplication");
                    }
                }

                @Override
                public void onAdOpened() {
                    super.onAdOpened();
                    if (mItemClickListener != null) {
                        Log.i("AndroidBash", "onAdOpened");
                    }
                }
            });
            AdRequest adRequest = new AdRequest.Builder()
                    .addTestDevice(MainActivity.TEST_DEVICE_ID)
                    .build();
            //You can add the following code if you are testing in an emulator
            /*AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();*/
            mNativeAd.loadAd(adRequest);
        }
    }

    interface MovieItemClick {
        void onMovieClick(Movie clickedMovie);
    }
}
