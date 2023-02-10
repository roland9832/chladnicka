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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Diet;
import sk.upjs.ics.chladnicka.storage.DietDao;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Recipe;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class EditRecipeController {
	
	private Map<Ingredient, Double> ingredientMap = new HashMap<>();
	
//	private ObservableList<Ingredient> ingredientsToSave = FXCollections.observableArrayList();

	
	private DialogPane dialog;
	
	private RecipesFxModel model;
	
	private Recipe recipe;
	
	private IngredientDao ingredientDao = DaoFactory.INSTANCE.getIngredientDao();

	private DietDao dietDao = DaoFactory.INSTANCE.getDietDao();

	private RecipeDao recipeDao = DaoFactory.INSTANCE.getRecipeDao();
	
	private ObservableList<Ingredient> ingredientModel;
	
	private ObservableList<Diet> dietModel;
	
	private List<Ingredient> ingredientsInRecipe;
	
	private ObservableList<Ingredient> ingredientsToSave;

    @FXML
    private TextField caloriesField;

    @FXML
    private Button createButton;

    @FXML
    private ComboBox<Diet> dietComboBox;

    @FXML
    private ComboBox<Ingredient> ingredientComboBox;

    @FXML
    private ListView<Ingredient> ingredientsListView;

    @FXML
    private TextField quantityField;

    
    @FXML
    private TextField nameTextField;

    @FXML
    private TextArea stepsTextArea;
    
    
    public EditRecipeController(Recipe recipe) {
    	this.recipe = recipe;
    }
    
    @FXML
    void initialize() {
    	nameTextField.setText(recipe.getRecipe_name());
    	
    	List<Diet> diets = dietDao.getAll();
    	dietModel = FXCollections.observableArrayList(diets);
    	dietComboBox.setItems(dietModel);
    	dietComboBox.setPromptText(recipe.getDiet().toString());
    	
    	
    	List<Ingredient> ingredients = ingredientDao.getAll();
    	ingredientModel = FXCollections.observableArrayList(ingredients);
    	ingredientComboBox.setItems(ingredientModel);
    	ingredientComboBox.setPromptText("Select");
    	
    	ingredientsInRecipe = recipe.getIngredient();
    	this.ingredientsToSave = FXCollections.observableArrayList(ingredientsInRecipe);
    	ingredientsListView.setItems(this.ingredientsToSave);
    	
    	ingredientMap = recipeDao.getAmountByRecipe(recipe);
    	
    	
    	double calories = recipe.getCalorific();
    	caloriesField.setText(String.valueOf(calories));
    	
    	
    	
    	stepsTextArea.setWrapText(true);
    	stepsTextArea.setText(recipe.getDescription());
    
    	

    }
    

    @FXML
    void EditButton(ActionEvent event) {
    	String name;
		double calories;
		String description;
		Diet diet;
		
		if (!nameTextField.getText().isBlank()) {
			name = nameTextField.getText();
			recipe.setRecipe_name(name);
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
				recipe.setCalorific(calories);
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
		recipe.setDescription(description);
		
		if (dietComboBox.getSelectionModel().getSelectedItem() != null) {
			diet = dietComboBox.getSelectionModel().getSelectedItem();
			recipe.setDiet(diet);
		} 		
		List<Ingredient> ingredientToSave = ingredientsToSave;
		
		
		
	
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
    }

    @FXML
    void removeFromRecipe(ActionEvent event) {
    	Ingredient selectedIngredient = ingredientsListView.getSelectionModel().getSelectedItem();
    	if(selectedIngredient != null) {
    		
    		ingredientsToSave.remove(selectedIngredient);
    		ingredientMap.remove(selectedIngredient);
    		recipe.setIngredient(ingredientsToSave);
    	}
    	ingredientsListView.setItems(ingredientsToSave);
    }

}