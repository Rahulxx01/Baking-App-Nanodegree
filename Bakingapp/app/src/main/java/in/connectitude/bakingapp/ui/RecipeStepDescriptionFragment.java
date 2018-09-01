package in.connectitude.bakingapp.ui;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;

import static android.content.Context.MODE_PRIVATE;


public class RecipeStepDescriptionFragment extends Fragment {


    @BindView(R.id.playerView)
    PlayerView playerView;

    @BindView(R.id.stepDescription)
    TextView stepDescription;

    @BindView(R.id.ingredientsCardSteps)
    CardView ingredientsCardSteps;

    @BindView(R.id.ingredientsListStepDescription)
    TextView ingredientsListStepDescription;


    @BindView(R.id.widgetButtonStepDescription)
    FloatingActionButton widgetButton;



    private SimpleExoPlayer player;

    private static final String TAG = "StepDetail";


    String videoUrl;
    String longDescription;
    boolean tablet;
    String ingredients;
    String name;

    public RecipeStepDescriptionFragment() {
        // Required empty public constructor
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            videoUrl = getArguments().getString("videoURL");
            longDescription = getArguments().getString("description");
            tablet = getArguments().getBoolean("tablet");
            ingredients = getArguments().getString("ingredients");
            name = getArguments().getString("name");

            //recipeList = (List<Steps>) getArguments().getSerializable("recipe_steps");*/
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View rootView = inflater.inflate(R.layout.fragment_recipe_step_description, container, false);
        ButterKnife.bind(this, rootView);

        stepDescription.setText(longDescription);

        if(!tablet){
            ingredientsCardSteps.setVisibility(View.GONE);
        }else{
            ingredientsListStepDescription.setText(ingredients);
        }


        if (videoUrl!=null) {
            playerView.setVisibility(View.VISIBLE);
            if(videoUrl.equals("")){
                playerView.setVisibility(View.GONE);
            }else{
                player = ExoPlayerFactory.newSimpleInstance(getContext(), new DefaultTrackSelector());
                playerView.setPlayer(player);
                DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(getContext(), Util.getUserAgent(getContext(), "exo-demo"));
                ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(videoUrl));
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);

            }


        } else {
            playerView.setVisibility(View.GONE);
            // stepDetailTxt.append("Video tutorial for this step will be uploaded soon. Check again after you are done with the cooking and the eating part as well :) .");
        }

        if (savedInstanceState != null && player != null) {
            player.seekTo(savedInstanceState.getLong("current_position"));
            player.setPlayWhenReady(savedInstanceState.getBoolean("play_state"));
        }

        widgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("INGREDIENTS",MODE_PRIVATE).edit();
                editor.putString("ingredients",ingredients);
                editor.putString("name",name);
                editor.commit();
                Toast.makeText(getContext(),"Widget Added to Home Screen",Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (player != null) {
            outState.putLong("current_position", player.getCurrentPosition());
            outState.putBoolean("play_state", player.getPlayWhenReady());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(TAG, "onStop:called ");
        playerView.setPlayer(null);
        if (player != null)
            player.release();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy:called ");
        playerView.setPlayer(null);

    }


}
