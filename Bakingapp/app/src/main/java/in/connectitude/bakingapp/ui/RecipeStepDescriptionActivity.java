package in.connectitude.bakingapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import in.connectitude.bakingapp.R;

public class RecipeStepDescriptionActivity extends AppCompatActivity {


    RecipeStepDescriptionFragment recipeStepDescriptionFragment;

    String ingredients = "";

    String title;

    String stepDescription = "";

    String videoURl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_step_description);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Description");

        if(null == savedInstanceState){

            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            //String title = getIntent().getStringExtra("name");



            recipeStepDescriptionFragment = new RecipeStepDescriptionFragment();
            Bundle fragmentBundle = new Bundle();

            fragmentBundle.putString("description", getIntent().getStringExtra("description"));
            videoURl = getIntent().getStringExtra("videoURL");
            fragmentBundle.putString("videoURL", videoURl);
            fragmentBundle.putBoolean("tablet", false);

            recipeStepDescriptionFragment.setArguments(fragmentBundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.recipeStepsActivityContainer, recipeStepDescriptionFragment).commit();
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

}
