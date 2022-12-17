package sk.upjs.ics.chladnicka.storage;

import java.util.List;

public interface RecipeDao {
	List<Recipe> getAll();
	List<Recipe> getByIngredient(Ingredient ingredient);
	List<Recipe> getByDiet(Diet diet);
	Recipe getByID(long id);
}
