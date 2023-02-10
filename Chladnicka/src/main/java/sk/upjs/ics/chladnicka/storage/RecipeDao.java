package sk.upjs.ics.chladnicka.storage;

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

public interface RecipeDao {
	List<Recipe> getAll();

	List<Recipe> getByIngredient(Ingredient ingredient);

	List<Recipe> getByDiet(Diet diet);

	Map<Ingredient, Double> getAmountByRecipe(Recipe recipe);

	Recipe getByID(long id);

	Recipe save(Recipe recipe, Map<Ingredient, Double> ingredientMap, List<Ingredient> ingredientToSave) throws NoSuchElementException, NullPointerException;

	boolean delete(Recipe recipe) throws EntityUndeletableException;
}
