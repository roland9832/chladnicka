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
		
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Recipe> recipes;
	private ObservableList<Favourite> favourites;
	
	
//	public FavouriteFxModel() {
//		recipes = FXCollections.observableArrayList();
//		favourites = FXCollections.observableArrayList();
//	}
	
	
	
	public FavouriteFxModel() {
		List<Recipe> Rlist = DaoFactory.INSTANCE.getRecipeDao().getAll();
		recipes = FXCollections.observableArrayList(Rlist);
		List<Favourite> Flist = DaoFactory.INSTANCE.getFavouriteDao().getAll();
		favourites = FXCollections.observableArrayList(Flist);
	}
	
	public FavouriteFxModel(Favourite favourite) {
		List<Recipe> Rlist = DaoFactory.INSTANCE.getRecipeDao().getAll();
		recipes = FXCollections.observableArrayList(Rlist);
		List<Favourite> Flist = DaoFactory.INSTANCE.getFavouriteDao().getAll();
		Flist.add(favourite);
		favourites = FXCollections.observableArrayList(Flist);
	}
	
	
	public StringProperty getNameProperty() {
		return name;
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
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
	
	
	public ObservableList<Favourite> getFavourites() {
		return favourites;
	}

	public void setFavourites(ObservableList<Favourite> favourites) {
		this.favourites = favourites;
	}

	public List<Favourite> getFavourite(){
		return new ArrayList<>(favourites);
	}
}