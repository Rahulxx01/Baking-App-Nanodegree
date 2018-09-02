package in.connectitude.bakingapp.ui;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.adapters.StepsAdapter;
import in.connectitude.bakingapp.model.Ingredients;
import in.connectitude.bakingapp.model.Steps;

public class RecipeDetailsTablet extends AppCompatActivity {


   /* @BindView(R.id.ingredientsList)
    TextView ingredentsList;*/

    @BindView(R.id.steps_recyclerView)
    RecyclerView stepsRecyclerView;

   /*@BindView(R.id.widgetButton)
    FloatingActionButton widgetButtom;*/

    String ingredients="";

    String title;

    String stepDescription="";

    String videoURl;

    StepsAdapter stepsAdapter;


    Boolean tablet = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details_tablet);
        ViewModelProviders.of(this).get(StepPresenterViewModel.class);
        ButterKnife.bind(this);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tablet = true;

        if(null == savedInstanceState) {


            if (findViewById(R.id.fragmentContainerTablet) != null) {


                List<Ingredients> ingredientsList = (List<Ingredients>) bundle.getSerializable("recipe_ingredients");
                for (int i = 0; i < ingredientsList.size(); i++) {
                    ingredients = ingredients + ingredientsList.get(i).getIngredient() + "(" + ingredientsList.get(i).getMeasure() + " " + ingredientsList.get(i).getQuantity() + ")\n";
                }
                Log.d("INGREDIENTS", ingredients);
                title = getIntent().getStringExtra("recipe_name");

                RecipeIngredientDescriptionFragment recipeIngredientDescriptionFragment = new RecipeIngredientDescriptionFragment();
                Bundle fragmentBundle1 = new Bundle();
                fragmentBundle1.putString("ingredients", ingredients);
                fragmentBundle1.putString("recipe_name", title);
                recipeIngredientDescriptionFragment.setArguments(fragmentBundle1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeIngredientsContainer, recipeIngredientDescriptionFragment)
                        .commit();


                List<Steps> stepsList = (List<Steps>) bundle.getSerializable("recipe_steps");


                // ingredentsList.setText(ingredients);
                setTitle(title);

                stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                stepsAdapter = new StepsAdapter(getApplicationContext(), stepsList, true, ingredients, title);
                stepsRecyclerView.setAdapter(stepsAdapter);


            } else {
                List<Ingredients> ingredientsList = (List<Ingredients>) bundle.getSerializable("recipe_ingredients");
                for (int i = 0; i < ingredientsList.size(); i++) {
                    ingredients = ingredients + ingredientsList.get(i).getIngredient() + "(" + ingredientsList.get(i).getMeasure() + " " + ingredientsList.get(i).getQuantity() + ")\n";
                }
                Log.d("INGREDIENTS", ingredients);
                title = getIntent().getStringExtra("recipe_name");
                RecipeIngredientDescriptionFragment recipeIngredientDescriptionFragment = new RecipeIngredientDescriptionFragment();
                Bundle fragmentBundle1 = new Bundle();
                fragmentBundle1.putString("ingredients", ingredients);
                fragmentBundle1.putString("recipe_name", title);
                recipeIngredientDescriptionFragment.setArguments(fragmentBundle1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipeIngredientsContainer, recipeIngredientDescriptionFragment)
                        .commit();


                List<Steps> stepsList = (List<Steps>) bundle.getSerializable("recipe_steps");
                title = getIntent().getStringExtra("recipe_name");

                // ingredentsList.setText(ingredients);
                setTitle(title);

                stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
                stepsAdapter = new StepsAdapter(getApplicationContext(), stepsList, true, ingredients, title);
                stepsRecyclerView.setAdapter(stepsAdapter);
            }


        }





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


}
