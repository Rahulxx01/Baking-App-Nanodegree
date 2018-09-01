package in.connectitude.bakingapp.network;

import android.database.Observable;

import java.util.List;

import in.connectitude.bakingapp.entities.Constants;
import in.connectitude.bakingapp.model.Recipe;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("59121517_baking/baking.json")
    Call<List<Recipe>> getRecipes();


}
