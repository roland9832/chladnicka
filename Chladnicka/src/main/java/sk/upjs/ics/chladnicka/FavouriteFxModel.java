package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class FavouriteFxModel{
		
	private Long id;
	private StringProperty recipe_name = new SimpleStringProperty();
	private ObservableList<Recipe> recipes;
	private ObservableList<Favourite> favourites;
	
	
	public FavouriteFxModel() {
		List<Recipe> Rlist = DaoFactory.INSTANCE.getRecipeDao().getAll();
		recipes = FXCollections.observableArrayList(Rlist);
		List<Favourite> Flist = DaoFactory.INSTANCE.getFavouriteDao().getAll();
		favourites = FXCollections.observableArrayList(Flist);
	}
	
	

	public FavouriteFxModel(Favourite favourite) {
		this.id = favourite.getId();
		setRecipe_name(DaoFactory.INSTANCE.getRecipeDao().getByID(id).getRecipe_name());
		List<Recipe> list = DaoFactory.INSTANCE.getRecipeDao().getAll();
		recipes = FXCollections.observableArrayList(list);
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
	
	public ObservableList<Recipe> getRecipeModel() {
		return recipes;
	}
	
	public List<Recipe> getRecipe(){
		return new ArrayList<>(recipes);
	}

	public ObservableList<Favourite> getFavouriteModel() {
		return favourites;
	}
	
	public List<Favourite> getFavourite(){
		return new ArrayList<>(favourites);
	}
	



	
//	public Favourite getFavourite() {
//		return new Favourite(id, );
//	}
//	
	
}
