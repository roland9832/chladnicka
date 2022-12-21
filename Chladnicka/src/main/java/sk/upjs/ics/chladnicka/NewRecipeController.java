package sk.upjs.ics.chladnicka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Diet;
import sk.upjs.ics.chladnicka.storage.DietDao;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Measure;
import sk.upjs.ics.chladnicka.storage.MeasureDao;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class NewRecipeController {

	private IngredientDao ingredientDao;

	private DietDao dietDao;

	private RecipeDao recipeDao;

	private MeasureDao measureDao;

	private ObservableList<Ingredient> ingredientsModel;

	private ObservableList<Measure> measuresModel;

	private ObservableList<Ingredient> ingredient;

	private ObservableList<String> measure;

	private ObservableList<String> diets2;

	private ObservableList<String> oneIngredient;

	private RecipesFxModel model;

	private List<Diet> dietModel;

	private Map<Ingredient, Double> ingredientMap = new HashMap<>();

	@FXML
	private Button createButton;

	@FXML
	private ComboBox<Ingredient> ingredientComboBox;

	@FXML
	private ListView<String> ingredientsListView;

	@FXML
	private ComboBox<String> measureComboBox;

	@FXML
	private ComboBox<String> dietComboBox;

	@FXML
	private TextField quantityField;

	@FXML
	private TextField caloriesField;

	@FXML
	private TextField recipeNameField;

	@FXML
	private TextArea stepsTextArea;

	NewRecipeController() {
		model = new RecipesFxModel();
		ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
		measureDao = DaoFactory.INSTANCE.getMeasureDao();
		dietDao = DaoFactory.INSTANCE.getDietDao();
		recipeDao = DaoFactory.INSTANCE.getRecipeDao();
	}

	@FXML
	void initialize() {
		// ingredientComboBox
		List<Ingredient> ingredients = ingredientDao.getAll();
		ingredientsModel = FXCollections.observableArrayList(ingredients);
		ingredient = FXCollections.observableArrayList();
		for (int i = 0; i < model.getIngredientModel().size(); i++) {
			ingredient.add(model.getIngredientModel().get(i));
		}
		ingredientComboBox.setItems(ingredient);
		ingredientComboBox.getSelectionModel().selectFirst();
		// measureComboBox
		List<Measure> measures = measureDao.getAll();
		measuresModel = FXCollections.observableArrayList(measures);
		measure = FXCollections.observableArrayList();
		measure.add("");
		for (int i = 0; i < model.getMeasureModel().size(); i++) {
			measure.add(model.getMeasureModel().get(i).getUnit());
		}
		measureComboBox.setItems(measure);
		measureComboBox.getSelectionModel().selectFirst();
		// dietComboBox
		List<Diet> diets = dietDao.getAll();
		dietModel = FXCollections.observableArrayList(diets);
		diets2 = FXCollections.observableArrayList();
		diets2.add("");
		for (int i = 0; i < model.getDietModel().size(); i++) {
			diets2.add(model.getDietModel().get(i).getName());
		}
		dietComboBox.setItems(diets2);
		dietComboBox.getSelectionModel().selectFirst();
	}

	@FXML
	void addToRecipe(ActionEvent event) {
		Ingredient ingredient = ingredientComboBox.getSelectionModel().getSelectedItem();
		String ingredientName = (ingredientComboBox.getSelectionModel().getSelectedItem()).getName();
		String measureName = measureComboBox.getSelectionModel().getSelectedItem();
		Long ingredient_id = ingredient.getId(); // ingredient_ingredient_id
		Double amount = Double.parseDouble(quantityField.getText()); // recipe_amount
		// s tymito informaciami to priradime
		oneIngredient = FXCollections.observableArrayList();
		oneIngredient.add(amount + " - " + ingredientName);
		ingredientsListView.setItems(oneIngredient);
		ingredientMap.put(ingredient, amount);
		measureComboBox.getSelectionModel().selectFirst();
		ingredientComboBox.getSelectionModel().selectFirst();
		quantityField.clear();

	}

	@FXML
	void addToStock(ActionEvent event) {

	}

	@FXML
	void createButton(ActionEvent event) {
		System.out.println(ingredientMap);

//        System.out.println("tu som");
//        String recipeName = recipeNameField.getText();
//        String steps = stepsTextArea.getText();
//        String calories = caloriesField.getText();
//        Diet recipeDiet = dietDao.getByName(dietComboBox.getSelectionModel().getSelectedItem());
//        String dietId = String.valueOf(recipeDiet.getId());
//        Recipe newRecipe = new Recipe(recipeName, Double.parseDouble(calories), steps, recipeDiet);
//        recipeDao.save(newRecipe);
//        // [Ingrediencia], amount
//        List<String> igredients = ingredientsListView.getItems();
//        //for () {
//
//        //}
//        Long id = newRecipe.getId();

	}

}
