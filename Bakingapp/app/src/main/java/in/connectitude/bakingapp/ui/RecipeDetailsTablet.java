package in.connectitude.bakingapp.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

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
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        tablet = true;
        if(findViewById(R.id.fragmentContainerTablet)!=null){

            List<Ingredients> ingredientsList = (List<Ingredients>) bundle.getSerializable("recipe_ingredients");
            for(int i=0;i<ingredientsList.size();i++){
                ingredients = ingredients+ingredientsList.get(i).getIngredient()+"("+ingredientsList.get(i).getMeasure()+" "+ingredientsList.get(i).getQuantity()+")\n";
            }
            Log.d("INGREDIENTS",ingredients);


            List<Steps> stepsList = (List<Steps>) bundle.getSerializable("recipe_steps");
            title = getIntent().getStringExtra("recipe_name");

            // ingredentsList.setText(ingredients);
            setTitle(title);

            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            stepsAdapter = new StepsAdapter(getApplicationContext(),stepsList,true,ingredients,title);
            stepsRecyclerView.setAdapter(stepsAdapter);


        }else{
            List<Ingredients> ingredientsList = (List<Ingredients>) bundle.getSerializable("recipe_ingredients");
            for(int i=0;i<ingredientsList.size();i++){
                ingredients = ingredients+ingredientsList.get(i).getIngredient()+"("+ingredientsList.get(i).getMeasure()+" "+ingredientsList.get(i).getQuantity()+")\n";
            }
            Log.d("INGREDIENTS",ingredients);


            List<Steps> stepsList = (List<Steps>) bundle.getSerializable("recipe_steps");
            title = getIntent().getStringExtra("recipe_name");

            // ingredentsList.setText(ingredients);
            setTitle(title);

            stepsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            stepsAdapter = new StepsAdapter(getApplicationContext(),stepsList,true,ingredients,title);
            stepsRecyclerView.setAdapter(stepsAdapter);
        }





    }


    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


}
