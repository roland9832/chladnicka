package sk.upjs.ics.chladnicka.storage;

import java.util.List;
import java.util.Map;

public interface RecipeDao {
	List<Recipe> getAll();

	List<Recipe> getByIngredient(Ingredient ingredient);

	List<Recipe> getByDiet(Diet diet);

	Map<Ingredient, Double> getAmountByRecipe(Recipe recipe);

	Recipe getByID(long id);
}
