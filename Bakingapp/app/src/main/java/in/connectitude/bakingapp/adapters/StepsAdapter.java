package in.connectitude.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.model.Steps;
import in.connectitude.bakingapp.ui.RecipeDetails;
import in.connectitude.bakingapp.ui.RecipeIngredientDescriptionFragment;
import in.connectitude.bakingapp.ui.RecipeStepDescriptionActivity;
import in.connectitude.bakingapp.ui.RecipeStepDescriptionFragment;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.StepsCardViewHolder> {

    public Context mContext;

    public List<Steps> mListItems;

    //public List<Recipe> recipeList;


    public String favourites = "null";


    Steps listItem;

    boolean tablet;

    //Recipe recipelistItem;
    String ingredients;

String name;
    public StepsAdapter(Context context, List<Steps> mListItems,boolean tablet) {

        mContext = context;
        this.mListItems = mListItems;
        this.tablet = tablet;

    }

    public StepsAdapter(Context context, List<Steps> mListItems, boolean tablet,String ingredients,String name) {

        mContext = context;
        this.mListItems = mListItems;
        this.tablet = tablet;
        this.ingredients = ingredients;
        this.name = name;

    }


    @Override
    public StepsAdapter.StepsCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.steps_list_item, parent, false);
        return new StepsAdapter.StepsCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final StepsAdapter.StepsCardViewHolder holder, int position) {


        listItem = mListItems.get(position);
        //  recipelistItem = recipeList.get(position);


        holder.stepNo.setText(position + ".");
        holder.stepName.setText(listItem.getShortDescription());

    }

    @Override
    public int getItemCount() {
        return mListItems.size();
    }


    public class StepsCardViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.stepNo)
        TextView stepNo;
        public @BindView(R.id.stepName)
        TextView stepName;


        public StepsCardViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int position = getAdapterPosition();
                    Intent intent = null;
                    if (tablet) {



                        RecipeStepDescriptionFragment recipeStepDescriptionFragment = new RecipeStepDescriptionFragment();
                        Bundle fragmentBundle = new Bundle();

                        fragmentBundle.putString("description", mListItems.get(position).getDescription());
                        fragmentBundle.putString("videoURL", mListItems.get(position).getVideoURL());
                        fragmentBundle.putBoolean("tablet", true);
                        fragmentBundle.putString("ingredients",ingredients);
                        fragmentBundle.putString("name",name);
                        recipeStepDescriptionFragment.setArguments(fragmentBundle);
                        ((AppCompatActivity) context).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.recipeStepDescriptionContainer, recipeStepDescriptionFragment)
                                .commit();


                    } else {
                        intent = new Intent(view.getContext(), RecipeStepDescriptionActivity.class);
                        intent.putExtra("step_description", "step_description");
                        intent.putExtra("description", mListItems.get(position).getDescription());
                        intent.putExtra("videoURL", mListItems.get(position).getVideoURL());
                        intent.putExtra("name", name);

                        // intent.putParcelableArrayListExtra("recipe_ingredients",  recipeList.get(position).getIngredients());
                        //intent.putExtra("recipe_steps", (Serializable) recipeList.get(position).getSteps());
                        context.startActivity(intent);

                    }


                }
            });


        }
    }


}


