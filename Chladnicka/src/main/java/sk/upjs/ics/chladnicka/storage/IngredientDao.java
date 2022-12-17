package sk.upjs.ics.chladnicka.storage;

import java.util.List;

public interface IngredientDao {
	List<Ingredient> getAll();
	
	List<Ingredient> getByAllergie(Allergie allergie);
	
	List<Ingredient> getByMeasure(Measure measure);
	
	List<Ingredient> getByRecipe(Recipe recipe);
	
	Ingredient getByID(long id);

	Ingredient save(Ingredient ingredient);
}
