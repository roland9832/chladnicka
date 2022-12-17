package sk.upjs.ics.chladnicka;

import java.util.List;

import sk.upjs.ics.chladnicka.storage.AllergieDao;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.DietDao;
import sk.upjs.ics.chladnicka.storage.FavouriteDao;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.MeasureDao;
import sk.upjs.ics.chladnicka.storage.Recipe;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class App {
	public static void main(String[] args) {
		DaoFactory.INSTANCE.setTesting();
		DietDao dietDao = DaoFactory.INSTANCE.getDietDao();
		AllergieDao allergieDao = DaoFactory.INSTANCE.getAllergieDao();
		MeasureDao measureDao = DaoFactory.INSTANCE.getMeasureDao();
		IngredientDao ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
		RecipeDao recipeDao = DaoFactory.INSTANCE.getRecipeDao();
		FavouriteDao favouriteDao = DaoFactory.INSTANCE.getFavouriteDao();
		
		
//		List<Diet> diets = dietDao.getAll();
//		System.out.println(diets);
//		
		
//		List<Allergie> allergies = allergieDao.getAll();
//		System.out.println(allergies);
//		
		
//		List<Measure> measures = measureDao.getAll();
//		System.out.println(measures);
//		System.out.println(measureDao.getByID(3));
		
		
		Recipe recipe = new Recipe();
		Long a = (long) 1;
		recipe.setId(a);
//		List<Ingredient> Ringredient = ingredientDao.getByRecipe(recipe);
//		System.out.println(Ringredient);
//		Allergie allergie = new Allergie();
//		Long b = (long) 7;
//		allergie.setId(b);
//		List<Ingredient> Aingredient = ingredientDao.getByAllergie(allergieDao.getByID(7));
//		System.out.println(Aingredient);
//		Measure measure = new Measure();
//		List<Ingredient> Mingredient = ingredientDao.getByMeasure(measureDao.getByID(3));
//		System.out.println(Mingredient);
//		Ingredient ingredient = DaoFactory.INSTANCE.getIngredientDao().getByID(1);
//		System.out.println(ingredient);
		List<Ingredient> allIngredient = ingredientDao.getAll();
		System.out.println(allIngredient);
//		List<Recipe> recipe = recipeDao.getAll();
//		System.out.println(recipe);
//		List<Favourite> favourites = favouriteDao.getAll();
//		System.out.println(favourites);
//		Favourite favourite = favouriteDao.getByRecipe(recipe);
//		System.out.println(favourite);
		
//		System.out.println(recipeDao.getAmountByRecipe(recipe));
		
	}

}
