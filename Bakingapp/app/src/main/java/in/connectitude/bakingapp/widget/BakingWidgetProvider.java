package in.connectitude.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import in.connectitude.bakingapp.R;
import in.connectitude.bakingapp.model.Recipe;
import in.connectitude.bakingapp.ui.MainActivity;
import in.connectitude.bakingapp.ui.RecipeDetails;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    RemoteViews views;

    String name = "Ingredients:";
    String ingredients = "Ingredients";

   /* static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

       // CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
        views.setOnClickPendingIntent(R.id.ingredientsList_widget,pendingIntent);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }*/

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            views = new RemoteViews(context.getPackageName(),R.layout.baking_widget_provider );
            appWidgetManager.updateAppWidget(appWidgetId, views);

            Intent configIntent = new Intent(context, MainActivity.class);
            PendingIntent configPendingIntent = PendingIntent.getActivity(context, 0, configIntent, 0);
            views.setOnClickPendingIntent(R.id.ingredientsList_widget, configPendingIntent);
            //updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
        String action = intent.getAction();
        views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        Intent mainActivityIntent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mainActivityIntent, 0);
        views.setOnClickPendingIntent(R.id.ingredientsList_widget, pendingIntent);

        if(getName(context)==null){
            views.setTextViewText(R.id.ingredients_name, name);


        }else{
            views.setTextViewText(R.id.ingredients_name, getName(context));
        }
        if(getIngredients(context)==null){
            views.setTextViewText(R.id.ingredientsList_widget, ingredients);
        }else{
            views.setTextViewText(R.id.ingredientsList_widget, getIngredients(context));
        }


        AppWidgetManager.getInstance(context).updateAppWidget(
                new ComponentName(context, BakingWidgetProvider.class), views);


    }


    private String getIngredients(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("INGREDIENTS", Context.MODE_PRIVATE);

        String ingredients = prefs.getString("ingredients", "");
        return ingredients;
    }

    private String getName(Context context) {
        SharedPreferences prefs = context.getSharedPreferences("INGREDIENTS", Context.MODE_PRIVATE);

        String ingredients = prefs.getString("name", "");
        return ingredients;
    }
}

