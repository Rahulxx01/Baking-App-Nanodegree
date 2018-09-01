package in.connectitude.bakingapp.ui;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.io.Serializable;
import java.util.List;

import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.model.Ingredients;
import in.connectitude.bakingapp.model.Steps;

public class RecipeDetails extends AppCompatActivity implements Serializable{


    RecipeDescriptionFragment recipeDescriptionFragment;

    RecipeStepDescriptionFragment recipeStepDescriptionFragment;

    String ingredients="";

    String title;

    String stepDescription="";

    String videoURl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();



        stepDescription = getIntent().getStringExtra("step_description");
        if(stepDescription==null){
            stepDescription = "";
        }
        if(stepDescription.equals("step_description")){
            recipeStepDescriptionFragment = new RecipeStepDescriptionFragment();
            Bundle fragmentBundle = new Bundle();

            fragmentBundle.putString("description",getIntent().getStringExtra("description"));
            videoURl = getIntent().getStringExtra("videoURL");
            fragmentBundle.putString("videoURL",videoURl);
            fragmentBundle.putBoolean("tablet",false);

            recipeStepDescriptionFragment.setArguments(fragmentBundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer,recipeStepDescriptionFragment).commit();

        }else{


            List<Ingredients> ingredientsList = (List<Ingredients>) bundle.getSerializable("recipe_ingredients");
            for(int i=0;i<ingredientsList.size();i++){
                ingredients = ingredients+ingredientsList.get(i).getIngredient()+"("+ingredientsList.get(i).getMeasure()+" "+ingredientsList.get(i).getQuantity()+")\n";
            }
            Log.d("INGREDIENTS",ingredients);


            List<Steps> stepsList = (List<Steps>) bundle.getSerializable("recipe_steps");
            title = getIntent().getStringExtra("recipe_name");



            setTitle(title);

            Bundle fragmentBundle = new Bundle();

            fragmentBundle.putString("ingredients",ingredients);
            fragmentBundle.putString("recipe_name",title);
            fragmentBundle.putSerializable("recipe_steps", (Serializable) stepsList);

            recipeDescriptionFragment = new RecipeDescriptionFragment();
            recipeDescriptionFragment.setArguments(fragmentBundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragmentContainer,recipeDescriptionFragment).commit();

        }






    }

    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


}
