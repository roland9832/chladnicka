package sk.upjs.ics.chladnicka.storage;

import java.util.List;
import java.util.NoSuchElementException;

public interface IngredientDao {
	List<Ingredient> getAll();
	
	List<Ingredient> getByAllergie(Allergie allergie);
	
	List<Ingredient> getByMeasure(Measure measure);
	
	List<Ingredient> getByRecipe(Recipe recipe);
	
	Ingredient getByID(long id);

	Ingredient save(Ingredient ingredient) throws NoSuchElementException,NullPointerException;
	
	boolean delete(Ingredient ingredient) throws ObjectUndeletableException;
	
	
}
