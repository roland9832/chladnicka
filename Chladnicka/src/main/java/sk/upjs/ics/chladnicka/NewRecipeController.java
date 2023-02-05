package sk.upjs.ics.chladnicka;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Diet;
import sk.upjs.ics.chladnicka.storage.DietDao;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.MeasureDao;
import sk.upjs.ics.chladnicka.storage.Recipe;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class NewRecipeController {

	private DialogPane dialog;

	private IngredientDao ingredientDao;

	private DietDao dietDao;

	private RecipeDao recipeDao;

	private ObservableList<Ingredient> ingredientsModel;





	private ObservableList<Diet> dietModel;



	private RecipesFxModel model;

//	private List<Diet> dietModel;

	private Map<Ingredient, Double> ingredientMap = new HashMap<>();

	@FXML
	private ComboBox<Ingredient> ingredientComboBox;

	@FXML
	private ListView<Ingredient> ingredientsListView;

	@FXML
	private ComboBox<Diet> dietComboBox;

	@FXML
	private TextField quantityField;

	@FXML
	private TextField caloriesField;

	@FXML
	private TextField recipeNameField;

	@FXML
	private TextArea stepsTextArea;

	private ObservableList<Ingredient> ingredientsToSave = FXCollections.observableArrayList();

	NewRecipeController() {
		model = new RecipesFxModel();
		ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
		dietDao = DaoFactory.INSTANCE.getDietDao();
		recipeDao = DaoFactory.INSTANCE.getRecipeDao();
//        recipeHasIngredientDao = DaoFactory.INSTANCE.getRecipeHasIngredientDao();
	}

	@FXML
	void initialize() {
		// ingredientComboBox
		List<Ingredient> ingredients = ingredientDao.getAll();
		ingredientsModel = FXCollections.observableArrayList(ingredients);
		ingredientComboBox.setItems(ingredientsModel);
		ingredientComboBox.setPromptText("Select");
		List<Diet> diets = dietDao.getAll();
		dietModel = FXCollections.observableArrayList(diets);
		dietComboBox.setItems(dietModel);
		

	}

	@FXML
	void addToRecipe(ActionEvent event) {
		Ingredient ingredient = ingredientComboBox.getSelectionModel().getSelectedItem();
		double quantity;
		if (ingredientComboBox.getSelectionModel().getSelectedItem() != null) {
			if (!quantityField.getText().isBlank()) {
				try {
					quantity = Double.parseDouble(quantityField.getText());
				} catch (Exception e) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setContentText("Zadajte ciselnu hodnotu");
					dialog = alert.getDialogPane();
					dialog.getStyleClass().add("dialog");
					alert.show();
					return;
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Nebola vyplnena quantita");
				dialog = alert.getDialogPane();
				dialog.getStyleClass().add("dialog");
				alert.show();
				return;
			}
			ingredientsToSave.add(ingredient);
			ingredientMap.put(ingredient, quantity);
		}
		ingredientsListView.setItems(ingredientsToSave);

//		System.out.println(ingredientMap.size());

	}

	@FXML
	void addToStock(ActionEvent event) {

	}

	@FXML
	void createButton(ActionEvent event) {
		String name;
		double calories;
		String description;
		Diet diet;
		if (!recipeNameField.getText().isBlank()) {
			name = recipeNameField.getText();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Nebol vyplneny nazov");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
		if (!caloriesField.getText().isBlank()) {
			try {
				calories = Double.parseDouble(caloriesField.getText());
			} catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Zadajte ciselnu hodnotu");
				dialog = alert.getDialogPane();
				dialog.getStyleClass().add("dialog");
				alert.show();
				return;
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Neboli vyplnene kalorie");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
		if (!stepsTextArea.getText().isBlank()) {
			description = stepsTextArea.getText();
		} else {
			description = "";
		}

		if (dietComboBox.getSelectionModel().getSelectedItem() != null) {
			diet = dietComboBox.getSelectionModel().getSelectedItem();
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Nebol vybraty dietny typ");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
	
		Recipe recipe = new Recipe(name, calories, description, diet);
		List<Ingredient> ingredientToSave = ingredientsToSave;
//		System.out.println(ingredientToSave);
		recipeDao.save(recipe, ingredientMap, ingredientToSave);
		
		ingredientsListView.getScene().getWindow().hide();
	}
	@FXML
    void addDietButton(ActionEvent event) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddDiet.fxml"));
			AddDietController controller = new AddDietController();
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Add Diet");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			dietModel.clear();
			List<Diet> tempList = dietDao.getAll();
			dietModel.addAll(tempList);
			dietComboBox.setItems(dietModel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	@FXML
    void removeFromRecipe(ActionEvent event) {
		Ingredient selectedIngredient = ingredientsListView.getSelectionModel().getSelectedItem();
	if(selectedIngredient != null) {
		ingredientsToSave.remove(selectedIngredient);
		ingredientMap.remove(selectedIngredient);
	}
	ingredientsListView.setItems(ingredientsToSave);
	
	
	
	}

}