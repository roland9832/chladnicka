package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Ingredient;

public class IngredientFxModel {
	
	private StringProperty name = new SimpleStringProperty();
	private ObservableList<Ingredient> ingredient;
	
	
	
	public IngredientFxModel() {
		List<Ingredient> ingredients = DaoFactory.INSTANCE.getIngredientDao().getAll();
		this.ingredient = FXCollections.observableArrayList(ingredients);
	}
	
	public IngredientFxModel(Ingredient ingredient) {
		List<Ingredient> ingredients = DaoFactory.INSTANCE.getIngredientDao().getAll();
		ingredients.add(ingredient);
		this.ingredient = FXCollections.observableArrayList(ingredients);
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
	
	public ObservableList<Ingredient> getIngredientModel() {
		return ingredient;
	}
	
	public List<Ingredient> getIngredient(){
		return new ArrayList<>(ingredient);
	}

}
