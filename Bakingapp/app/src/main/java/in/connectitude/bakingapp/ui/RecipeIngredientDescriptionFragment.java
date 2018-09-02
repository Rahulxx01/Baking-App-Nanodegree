package in.connectitude.bakingapp.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.model.Steps;

import static android.content.Context.MODE_PRIVATE;


public class RecipeIngredientDescriptionFragment extends Fragment {


    public RecipeIngredientDescriptionFragment() {
        // Required empty public constructor
    }
@BindView(R.id.ingredientsListStepIngredients)
    TextView ingredientList;

    @BindView(R.id.widgetButtonIngredients)
    FloatingActionButton widgetButton;

    String ingredients;
    String recipeName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (getArguments() != null) {
            recipeName = getArguments().getString("recipe_name");
            ingredients = getArguments().getString("ingredients");

        }
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_recipe_ingredient_description, container, false);
        ButterKnife.bind(this, rootView);
        ingredientList.setText(ingredients);

        widgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("INGREDIENTS", MODE_PRIVATE).edit();
                editor.putString("ingredients", ingredients);
                editor.putString("name", recipeName);
                editor.commit();
                Toast.makeText(getContext(), "Widget Added to Home Screen", Toast.LENGTH_SHORT).show();
            }
        });


       return rootView;
    }


}
