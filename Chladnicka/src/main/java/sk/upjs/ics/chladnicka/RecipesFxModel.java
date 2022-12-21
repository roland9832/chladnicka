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
import sk.upjs.ics.chladnicka.storage.Measure;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class RecipesFxModel {

    private Long id;

    private Recipe recipe;
    private StringProperty recipe_name = new SimpleStringProperty();
    private ObservableList<Ingredient> ingredients;
    private ObservableList<Diet> diets;

    private ObservableList<Measure> measures;

    private ObservableList<Recipe> recipes;


    public RecipesFxModel() {
        List<Ingredient> Rlist = DaoFactory.INSTANCE.getIngredientDao().getAll();
        List<Diet> dietList = DaoFactory.INSTANCE.getDietDao().getAll();
        List<Recipe> recipeList = DaoFactory.INSTANCE.getRecipeDao().getAll();
        List<Measure> measureList = DaoFactory.INSTANCE.getMeasureDao().getAll();
        measures = FXCollections.observableArrayList(measureList);
        recipes = FXCollections.observableArrayList(recipeList);
        ingredients = FXCollections.observableArrayList(Rlist);
        diets = FXCollections.observableArrayList(dietList);
    }

	public RecipesFxModel(List<Recipe> recipes, List<Diet> diets, Recipe recipe) {
		List<Ingredient> list = DaoFactory.INSTANCE.getIngredientDao().getAll();
        List<Diet> dietList = DaoFactory.INSTANCE.getDietDao().getAll();
        List<Recipe> recipeList = DaoFactory.INSTANCE.getRecipeDao().getAll();
        List<Measure> measureList = DaoFactory.INSTANCE.getMeasureDao().getAll();
        this.measures = FXCollections.observableArrayList(measureList);
        this.diets = FXCollections.observableArrayList(dietList);
		this.ingredients = FXCollections.observableArrayList(list);
        this.recipes = FXCollections.observableArrayList(recipeList);
	}

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

    public ObservableList<Measure> getMeasureModel() {
        return measures;
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

    public List<Recipe> getRecipeDao(){
        return recipes;
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
