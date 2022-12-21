package sk.upjs.ics.chladnicka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Diet;
import sk.upjs.ics.chladnicka.storage.DietDao;
import sk.upjs.ics.chladnicka.storage.EntityUndeletableException;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Recipe;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class RecipesController {

	private DialogPane dialog;
	
	private RecipesFxModel model;

	private IngredientDao ingredientDao;

	private DietDao dietDao;

	private RecipeDao recipeDao;

	private ObservableList<Diet> items;

	private ObservableList<Ingredient> ingredients;

	private List<Recipe> selectedRecipes;

	private List<Recipe> selectedByIngredient;
	private List<Recipe> selectedByDiet;

	private List<Recipe> recipes;

	private List<Diet> selectedDiets;

	private ObservableList<Recipe> item;

	@FXML
	private ComboBox<Ingredient> ingredientsComboBox;

	@FXML
	private ComboBox<Diet> diet;

	@FXML
	private ListView<Recipe> recipesListView;

	public RecipesController() {
		model = new RecipesFxModel();
		ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
		dietDao = DaoFactory.INSTANCE.getDietDao();
		recipeDao = DaoFactory.INSTANCE.getRecipeDao();
	}

	private ObservableList<Ingredient> ingredientsModel;

	private List<Diet> dietModel;

	private ObservableList<Recipe> listView = FXCollections.observableArrayList();
	private ObservableList<Recipe> clear = FXCollections.observableArrayList();
	@FXML
	void initialize() {
		// logger.debug("inicialize running");
		// Ingredient
		List<Ingredient> ingredients = ingredientDao.getAll();
		ingredientsModel = FXCollections.observableArrayList(ingredients);
		ingredientsComboBox.setItems(ingredientsModel);
		ingredientsComboBox.getSelectionModel();
		// Diet
		List<Diet> diets = dietDao.getAll();
		items = FXCollections.observableArrayList(diets);
		diet.setItems(items);
		diet.getSelectionModel();
	}

	@FXML
	void createButton(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("NewRecipe.fxml"));
			NewRecipeController controller4 = new NewRecipeController();
			fxmlLoader.setController(controller4);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Recipes");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void searchButton(ActionEvent event) {
		// if ingredient and diet is not initial -> treba najst prienik
		item = FXCollections.observableArrayList(); // tu sa budu ukladat dobre recepty
		if (ingredientsComboBox.getSelectionModel().getSelectedItem() != null
				&& diet.getSelectionModel().getSelectedItem() != null) {
			Ingredient ingredient = ingredientsComboBox.getSelectionModel().getSelectedItem(); // Ingredient
			Diet dietIn = diet.getSelectionModel().getSelectedItem(); // Diet
			selectedByIngredient = recipeDao.getByIngredient(ingredient);
			selectedByDiet = recipeDao.getByDiet(dietIn);
			List<Recipe> recipes = new ArrayList<>();
			for (Recipe recipe : selectedByIngredient) {
				if (selectedByDiet.contains(recipe)) {
					recipes.add(recipe);
				}
			}
			listView = FXCollections.observableArrayList(recipes);

		}
		// if ingredient is not initial and diet is initial
		else if (ingredientsComboBox.getSelectionModel().getSelectedItem() != null
				&& diet.getSelectionModel().getSelectedItem() == null) {
			Ingredient ingredient = ingredientsComboBox.getSelectionModel().getSelectedItem();
			List<Recipe> recipes = recipeDao.getByIngredient(ingredient);
			listView = FXCollections.observableArrayList(recipes);
		}
		// if ingredient is initial and diet is not initial
		else if (ingredientsComboBox.getSelectionModel().getSelectedItem() == null
				&& diet.getSelectionModel().getSelectedItem() != null) {
			Diet dietIn = diet.getSelectionModel().getSelectedItem();
			List<Recipe> recipes = recipeDao.getByDiet(dietIn);
			listView = FXCollections.observableArrayList(recipes);
		} else {
			List<Recipe> recipes = recipeDao.getAll();
			listView = FXCollections.observableArrayList(recipes);
		}
		recipesListView.setItems(listView);
	}

	@FXML
	void showRecipeButton(ActionEvent event) {
		Recipe selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
		
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowRecipe.fxml"));
			ShowRecipeController controller5 = new ShowRecipeController(selectedRecipe);
			fxmlLoader.setController(controller5);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(selectedRecipe.getRecipe_name());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			ingredientsComboBox.getSelectionModel().clearSelection();
			diet.getSelectionModel().clearSelection();
			recipesListView.setItems(clear);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to select recipe to show");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
	}
	
	 @FXML
	    void deleteRecipeButton(ActionEvent event) {
		 Recipe selectedRecipe = recipesListView.getSelectionModel().getSelectedItem();
		 List<Recipe> recipes = this.listView;
		 if(recipesListView.getSelectionModel().getSelectedItem() != null) {
			 try {
					recipeDao.delete(selectedRecipe);
				} catch (EntityUndeletableException e) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setContentText(e.getMessage());
					alert.showAndWait();
				}
		 }
		 recipes.remove(selectedRecipe);
		 this.recipes = recipes;
		 listView = FXCollections.observableArrayList(this.recipes);
		 recipesListView.setItems(listView);
		 
	 
	 }
}
