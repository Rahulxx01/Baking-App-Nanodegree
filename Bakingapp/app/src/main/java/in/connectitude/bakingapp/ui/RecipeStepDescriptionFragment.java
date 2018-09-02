package in.connectitude.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;


public class RecipeStepDescriptionFragment extends Fragment {


    @BindView(R.id.playerView)
    PlayerView playerView;

    @BindView(R.id.stepDescription)
    TextView stepDescription;

    @BindView(R.id.ingredientsCardSteps)
    CardView ingredientsCardSteps;

    @BindView(R.id.ingredientsListStepDescription)
    TextView ingredientsListStepDescription;

    private static final String SAVED_INSTANCE_POSITION = "position";
    private static final String SAVED_PLAYBACK_POSITION = "playback_position";
    private static final String SAVED_PLAYBACK_WINDOW = "current_window";
    private static final String SAVED_PLAY_WHEN_READY = "play_when_ready";

    private int currentStepPosition;
    private long playbackPosition = 0;
    private int currentWindow = 0;
    private boolean playWhenReady = true;


    private SimpleExoPlayer player;

    //Long position;
    //Boolean playWhenReady;

    //Long playbackPosition;


    private static final String TAG = "StepDetail";


    String videoUrl;
    String longDescription;
    boolean tablet;
    String ingredients;
    String name;

    public RecipeStepDescriptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment

        ViewModelProviders.of(this).get(StepPresenterViewModel.class);
        View rootView = inflater.inflate(R.layout.fragment_recipe_step_description, container, false);
        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            videoUrl = getArguments().getString("videoURL");
            longDescription = getArguments().getString("description");
            tablet = getArguments().getBoolean("tablet");
            ingredients = getArguments().getString("ingredients");
            name = getArguments().getString("name");
        }

        if (savedInstanceState != null){
            currentStepPosition = savedInstanceState.getInt(SAVED_INSTANCE_POSITION, 0);
            playbackPosition = savedInstanceState.getLong(SAVED_PLAYBACK_POSITION, 0);
            currentWindow = savedInstanceState.getInt(SAVED_PLAYBACK_WINDOW, 0);
            playWhenReady = savedInstanceState.getBoolean(SAVED_PLAY_WHEN_READY, false);

        }

        stepDescription.setText(longDescription);
        ingredientsCardSteps.setVisibility(View.GONE);



        return rootView;
    }


    private void initViews() {

        int currentOrientation = getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
           // hideSystemUi();
        } else {


                stepDescription.setText(longDescription);
                ingredientsCardSteps.setVisibility(View.GONE);

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        if (Util.SDK_INT > 23) {
            initViews();
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || player == null)) {
            initViews();
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (player != null) {
            releasePlayer();
        }
    }

    private void initializePlayer(){
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());

            playerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            MediaSource mediaSource = buildMediaSource();
            player.prepare(mediaSource, true, false);
            player.seekTo(currentWindow, playbackPosition);
        }
    }


    private void releasePlayer(){
        if (player != null){
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource() {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-baking-app"))
                .createMediaSource(Uri.parse(videoUrl));
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_POSITION, currentStepPosition);
        outState.putLong(SAVED_PLAYBACK_POSITION, playbackPosition);
        outState.putInt(SAVED_PLAYBACK_WINDOW, currentWindow);
        outState.putBoolean(SAVED_PLAY_WHEN_READY, playWhenReady);

    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) playerView.getLayoutParams();
        params.width = params.MATCH_PARENT;
        params.height = params.MATCH_PARENT;
        playerView.setLayoutParams(params);
    }


}
