package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.Recipe;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class ShowRecipeController {

	@FXML
	private ListView<String> ingredientsList2;

	@FXML
	private Label caloriesLabel;

	@FXML
	private Label dietLabel;

	@FXML
	private Label titleLabel;

	@FXML
	private Label stepsLabel;

	private Recipe selectedRecipe;

	private ObservableList<String> ingredients;

	private RecipeDao recipeDao;

	public ShowRecipeController(Recipe selectedRecipe) {
		this.selectedRecipe = selectedRecipe;
		recipeDao = DaoFactory.INSTANCE.getRecipeDao();

	}

	@FXML
	void initialize() {
		titleLabel.setText(selectedRecipe.getRecipe_name().toUpperCase());
		dietLabel.setText(selectedRecipe.getDiet().getName());
		stepsLabel.setText(selectedRecipe.getDescription());
		stepsLabel.getTextOverrun();
		caloriesLabel.setText(String.valueOf(selectedRecipe.getCalorific()));
		// kolko ingrediencii obsahuje moj recept
		List<Ingredient> ingredientsList = new ArrayList<>();
		ingredientsList = selectedRecipe.getIngredient();
		ingredients = FXCollections.observableArrayList();
		Map<Ingredient, Double> mapka = new HashMap<>();
		mapka = recipeDao.getAmountByRecipe(selectedRecipe);
		for (int i = 0; i < ingredientsList.size(); i++) {
			String name = ingredientsList.get(i).getName();
			String quantity = String.valueOf(mapka.get(ingredientsList.get(i)));
			String measure = ingredientsList.get(i).getMeasure().getUnit();
			ingredients.add(name + ": " + quantity + "" + measure);
		}
		ingredientsList2.setItems(ingredients);
	}
}
