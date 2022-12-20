package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Diet;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class RecipesFxModel {

    private Long id;

    private Recipe recipe;
    private StringProperty recipe_name = new SimpleStringProperty();
    private ObservableList<Ingredient> ingredients;
    private ObservableList<Diet> diets;


    public RecipesFxModel() {
        List<Ingredient> Rlist = DaoFactory.INSTANCE.getIngredientDao().getAll();
        List<Diet> dietList = DaoFactory.INSTANCE.getDietDao().getAll();
        ingredients = FXCollections.observableArrayList(Rlist);
        diets = FXCollections.observableArrayList(dietList);
    }

	public RecipesFxModel(List<Recipe> recipes, List<Diet> diets) {
		List<Ingredient> list = DaoFactory.INSTANCE.getIngredientDao().getAll();
        List<Diet> dietList = DaoFactory.INSTANCE.getDietDao().getAll();
        this.diets = FXCollections.observableArrayList(dietList);
		this.ingredients = FXCollections.observableArrayList(list);
	}

    //public RecipesFxModel(ListView<Recipe> favouriteListView) {
      //  List<Recipe> Rlist = DaoFactory.INSTANCE.getRecipeDao().getAll();
        //recipes = FXCollections.observableArrayList(Rlist);
        //favourites = extracted(favouriteListView);
    //}

    @SuppressWarnings("unchecked")
    //private ObservableList<Recipe> extracted(ListView<Recipe> favouriteListView) {
      //  return (ObservableList<Recipe>) favouriteListView;
    //}

    public StringProperty nameProperty() {
        return recipe_name;
    }

    public String getName() {
        return recipe_name.get();
    }

    public void setRecipe_name(String name) {
        this.recipe_name.set(name);
    }

    public ObservableList<Ingredient> getIngredientModel() {
        System.out.println(ingredients);
        System.out.println("recipes");
        return ingredients;
    }

    public ObservableList<Diet> getDietModel() {
        System.out.println(diets) ;
        System.out.println("diets") ;
        return diets;
    }

    public List<Ingredient> getIngredient(){
        return new ArrayList<>(ingredients);
    }

    public List<Diet> getDiet(){
        return new ArrayList<>(diets);
    }

    public List<Diet> getDietDao(){
        return diets;
    }

    public String getDietName(Diet diet){
        return diet.getName();
    }

    public Ingredient getByName(String name){
        return DaoFactory.INSTANCE.getIngredientDao().getByName(name);
    }

    public Diet getDietByName(String name){
        return DaoFactory.INSTANCE.getDietDao().getByName(name);
    }

    public Diet getDietById(Long id){
        return DaoFactory.INSTANCE.getDietDao().getByID(id);
    }

}
