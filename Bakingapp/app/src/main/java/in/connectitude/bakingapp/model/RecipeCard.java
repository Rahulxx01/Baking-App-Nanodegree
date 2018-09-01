package in.connectitude.bakingapp.model;

public class RecipeCard {

    private String itemName;
    private int imageUrl;


    public RecipeCard() {

    }

    public RecipeCard(String itemName, int imageUrl) {
        this.itemName = itemName;
        this.imageUrl = imageUrl;
    }


    public int getImageUrl() {
        return imageUrl;
    }

    public String getItemName() {
        return itemName;
    }
}
