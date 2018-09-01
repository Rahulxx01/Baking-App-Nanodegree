package in.connectitude.bakingapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.model.Recipe;
import in.connectitude.bakingapp.model.RecipeCard;
import in.connectitude.bakingapp.ui.RecipeDetails;
import in.connectitude.bakingapp.ui.RecipeDetailsTablet;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeCardViewHolder> {

    public Context mContext;

    public List<RecipeCard> mListItems;

    public List<Recipe> recipeList;


    public String favourites = "null";
    boolean tablet;


    RecipeCard listItem;

    Recipe recipelistItem;




    public RecipeAdapter(Context context, List<RecipeCard> mListItems) {

        mContext = context;
        this.mListItems = mListItems;


    }

    public RecipeAdapter(Context context, List<RecipeCard> mListItems, List<Recipe> recipeList,boolean tablet) {

        mContext = context;
        this.mListItems = mListItems;
        this.recipeList = recipeList;
        this.tablet = tablet;

    }


    @Override
    public RecipeAdapter.RecipeCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_baking, parent, false);
        return new RecipeAdapter.RecipeCardViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final RecipeAdapter.RecipeCardViewHolder holder, int position) {


        listItem = mListItems.get(position);
        recipelistItem = recipeList.get(position);


        holder.foodName.setText(listItem.getItemName());
        holder.foodImage.setImageResource(listItem.getImageUrl());

    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }




    public class RecipeCardViewHolder extends RecyclerView.ViewHolder {

        public @BindView(R.id.foodName_textView)
        TextView foodName;
        public @BindView(R.id.foodItem_ImageView)
        ImageView foodImage;


        public RecipeCardViewHolder(final View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    int position = getAdapterPosition();
                    Intent intent = null;
                    if(tablet){
                        intent = new Intent(view.getContext(), RecipeDetailsTablet.class);
                    }else{
                      intent = new Intent(view.getContext(), RecipeDetails.class);
                    }

                    intent.putExtra("recipe_name", recipeList.get(position).getName());
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("recipe_ingredients", (Serializable) recipeList.get(position).getIngredients());
                    bundle.putSerializable("recipe_steps", (Serializable) recipeList.get(position).getSteps());
                    intent.putExtras(bundle);
                   // intent.putParcelableArrayListExtra("recipe_ingredients",  recipeList.get(position).getIngredients());
                    //intent.putExtra("recipe_steps", (Serializable) recipeList.get(position).getSteps());
                    context.startActivity(intent);

                }
            });


        }
    }


}

