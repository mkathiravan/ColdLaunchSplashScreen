package com.example.kabali.coldlaunchsplashscreen.pagination;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.kabali.coldlaunchsplashscreen.R;
import com.example.kabali.coldlaunchsplashscreen.pagination.models.Result;
import com.example.kabali.coldlaunchsplashscreen.pagination.utils.PaginationAdapterCallback;

import java.util.ArrayList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private static final int HERO = 2;

    private static final String BASE_URL_IMG = "https://image.tmdb.org/t/p/w150";

    private List<Result> movieResults;
    private Context context;

    private boolean isLoadingAdded = false;
    private boolean retryPageLoad = false;

    private PaginationAdapterCallback mCallback;

    private String errorMsg;

    public PaginationAdapter(Context context) {

        this.context = context;
        this.mCallback = (PaginationAdapterCallback) context;
        movieResults = new ArrayList<>();

    }

    public List<Result> getMovies()
    {
        return movieResults;
    }

    public void setMovies(List<Result> movieResults)
    {
        this.movieResults = movieResults;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());

        switch (i)
        {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_list,viewGroup,false);
                viewHolder = new MovieVH(viewItem);
                break;

            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress,viewGroup,false);
                viewHolder = new LoadingVH(viewLoading);
                break;

            case HERO:
                View viewHero = inflater.inflate(R.layout.item_hero,viewGroup,false);
                viewHolder = new HeroVH(viewHero);
                break;
        }


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        Result result = movieResults.get(i);

        switch (getItemViewType(i))
        {
            case HERO:
                final HeroVH heroVH = (HeroVH)viewHolder;

                heroVH.mMovieTitle.setText(result.getTitle());
                heroVH.mYear.setText(formatYearLabel(result));
                heroVH.mMovieDesc.setText(result.getOverview());

                loadImage(result.getBackdropPath())
                        .into(heroVH.mPosterImg);
                break;

            case ITEM:

                final MovieVH movieVH = (MovieVH)viewHolder;

                movieVH.mMovieTitle.setText(result.getTitle());
                movieVH.mYear.setText(formatYearLabel(result));
                movieVH.mMovieDesc.setText(result.getOverview());

                loadImage(result.getPosterPath()).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        movieVH.mProgress.setVisibility(View.GONE);
                        return false;

                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        movieVH.mProgress.setVisibility(View.GONE);
                        return false;
                    }
                }).into(movieVH.mPosterImg);

                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) viewHolder;

                if (retryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            errorMsg != null ?
                                    errorMsg :
                                    context.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }
                break;


        }


    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
        {
            return HERO;
        }
        else
        {
            return (position == movieResults.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
        }

    }


    //To print the year format with the Language Format

    private String formatYearLabel(Result result)
    {
        return result.getReleaseDate().substring(0,4) + " | " +result.getOriginalLanguage().toUpperCase();
    }

    private DrawableRequestBuilder<String> loadImage(@NonNull String posterPath)
    {
        return Glide.with(context)
                .load(BASE_URL_IMG + posterPath)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .crossFade();

    }

    public void add(Result r)
    {
        movieResults.add(r);
        notifyItemInserted(movieResults.size() -1);
    }

    public void addAllList(List<Result> movieResults)
    {
        for(Result result : movieResults){
            add(result);
        }

    }

    public void remove(Result r)
    {
        int position = movieResults.indexOf(r);
        if(position > -1)
        {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void clear()
    {
        isLoadingAdded = false;

        while (getItemCount() > 0)
        {
            remove(getItem(0));
        }
    }


    public boolean isEmpty()
    {
        return getItemCount() == 0;
    }

    public void addLoadingFooter()
    {
        isLoadingAdded = true;
        add(new Result());
    }

    public void removeLoadingFooter()
    {
        isLoadingAdded = false;

        int position = movieResults.size() - 1;

        Result result = getItem(position);

        if(result != null)
        {
            movieResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Result getItem(int position) {
        return movieResults.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg)
    {
        retryPageLoad = show;

        notifyItemChanged(movieResults.size() - 1);

        if(errorMsg != null)
        {
            this.errorMsg = errorMsg;
        }
    }


    @Override
    public int getItemCount() {
        return movieResults == null ? 0 : movieResults.size();
    }


    //ViewHolders

    //Header ViewHolder

    protected class HeroVH extends RecyclerView.ViewHolder
    {
        private TextView mMovieTitle;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;

        public HeroVH(@NonNull View itemView) {
            super(itemView);

            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieDesc = (TextView) itemView.findViewById(R.id.movie_desc);
            mYear = (TextView) itemView.findViewById(R.id.movie_year);
            mPosterImg = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }

    // Main List content viewholder

    protected class MovieVH extends RecyclerView.ViewHolder
    {
        private TextView mMovieTitle;
        private TextView mMovieDesc;
        private TextView mYear; // displays "year | language"
        private ImageView mPosterImg;
        private ProgressBar mProgress;


        public MovieVH(@NonNull View itemView) {
            super(itemView);

            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieDesc = (TextView) itemView.findViewById(R.id.movie_desc);
            mYear = (TextView) itemView.findViewById(R.id.movie_year);
            mPosterImg = (ImageView) itemView.findViewById(R.id.movie_poster);
            mProgress = (ProgressBar) itemView.findViewById(R.id.movie_progress);
        }
    }

    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

}
