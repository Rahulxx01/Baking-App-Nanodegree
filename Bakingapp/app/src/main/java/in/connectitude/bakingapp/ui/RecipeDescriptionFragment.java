package in.connectitude.bakingapp.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.adapters.StepsAdapter;
import in.connectitude.bakingapp.model.Steps;

import static android.content.Context.MODE_PRIVATE;


public class RecipeDescriptionFragment extends Fragment {

    String recipeName;
    String ingredients;
    List<Steps> recipeList;

    @BindView(R.id.ingredientsList)
    TextView ingredentsList;

    @BindView(R.id.steps_recyclerView)
    RecyclerView stepsRecyclerView;

    @BindView(R.id.widgetButton)
    FloatingActionButton widgetButtom;


    StepsAdapter stepsAdapter;


    public RecipeDescriptionFragment() {
        // Required empty public constructor
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            recipeName = getArguments().getString("recipe_name");
            ingredients = getArguments().getString("ingredients");
            recipeList = (List<Steps>) getArguments().getSerializable("recipe_steps");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_recipe_description, container, false);
        View rootView = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        ButterKnife.bind(this, rootView);
        //ButterKnife.bind((Activity) getContext());
       // ingredentsList = rootView.findViewById(R.id.ingredientsList);


        Log.v("Ingredients",ingredients);
        ingredentsList.setText(ingredients);




        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsAdapter = new StepsAdapter(getContext(),recipeList,false);
        stepsRecyclerView.setAdapter(stepsAdapter);


        widgetButtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("INGREDIENTS",MODE_PRIVATE).edit();
                editor.putString("ingredients",ingredients);
                editor.putString("name",recipeName);
                editor.commit();
                Toast.makeText(getContext(),"Widget Added to Home Screen",Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }


}
