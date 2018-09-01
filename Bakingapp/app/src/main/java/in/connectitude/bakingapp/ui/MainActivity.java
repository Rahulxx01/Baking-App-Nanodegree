package in.connectitude.bakingapp.ui;

import android.content.Context;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.adapters.RecipeAdapter;
import in.connectitude.bakingapp.model.Recipe;
import in.connectitude.bakingapp.model.RecipeCard;
import in.connectitude.bakingapp.network.ApiService;
import in.connectitude.bakingapp.network.RetrofitClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    List<Recipe> recipeList;



    List<RecipeCard> mainList;

    @BindView(R.id.mainListRecyclerView)
    RecyclerView mainListRecyclerView;

    boolean tablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);




        if(findViewById(R.id.mainActivityTwoPane)!=null){
                tablet = true;
        }else{
            tablet = false;
        }


        if(checkInternetConnectivity()){

            ApiService apiService = RetrofitClient.getApiService();

            Call<List<Recipe>> call = apiService.getRecipes();


            call.enqueue(new Callback<List<Recipe>>() {

                @Override
                public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                    recipeList = response.body();
                    String nameArray[] = new String[recipeList.size()] ;
                    for (int i = 0; i < recipeList.size(); i++) {
                        Log.d("name.............",recipeList.get(i).getName());
                        nameArray[i] = recipeList.get(i).getName();
                    }

                    mainList = new ArrayList<RecipeCard>();
                    mainList.add(new RecipeCard(nameArray[0], R.drawable.nutella_pie));
                    mainList.add(new RecipeCard(nameArray[1], R.drawable.brownies));
                    mainList.add(new RecipeCard(nameArray[2], R.drawable.yellow_cake));
                    mainList.add(new RecipeCard(nameArray[3], R.drawable.cheesecake));

                    RecipeAdapter reciperAdapter = new RecipeAdapter(getApplicationContext(), mainList,recipeList,tablet);
                    mainListRecyclerView.setAdapter(reciperAdapter);

                }

                @Override
                public void onFailure(Call<List<Recipe>> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "Error has occured", Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getApplicationContext(),"NO internet connection",Toast.LENGTH_LONG).show();
        }

        /*Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/

        if (isTablet(this) || getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mainListRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
            tablet = true;
        } else {
            mainListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            tablet = false;
        }





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean isTablet(Context context) {
        boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
        boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
        return (xlarge || large);
    }


    public boolean checkInternetConnectivity(){
        //Check internet connection//
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network//
        NetworkInfo netInformation = connectivityManager.getActiveNetworkInfo();
        // If there is a network connection, then fetch data//
        if(netInformation!=null && netInformation.isConnected()){
            return true;
        }else{
            return false;
        }
    }

}
