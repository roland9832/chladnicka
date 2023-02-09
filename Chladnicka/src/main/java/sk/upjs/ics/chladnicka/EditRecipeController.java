package sk.upjs.ics.chladnicka;
import java.util.List;

import javafx.collections.FXCollections;
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
import sk.upjs.ics.chladnicka.storage.Recipe;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class EditRecipeController {
	
	private RecipesFxModel model;
	
	private Recipe recipe;
	
	private IngredientDao ingredientDao = DaoFactory.INSTANCE.getIngredientDao();

	private DietDao dietDao = DaoFactory.INSTANCE.getDietDao();

	private RecipeDao recipeDao = DaoFactory.INSTANCE.getRecipeDao();

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
    private TextField recipeNameField;

    @FXML
    private TextArea stepsTextArea;
    
    
    public EditRecipeController(Recipe recipe) {
    	this.recipe = recipe;
    }
    
    void initialize() {
    	recipeNameField.setText(recipe.getRecipe_name());
    	stepsTextArea.setText(recipe.getDescription());
    	
    }
    

    @FXML
    void EditButton(ActionEvent event) {

    }

    @FXML
    void addDietButton(ActionEvent event) {

    }

    @FXML
    void addToRecipe(ActionEvent event) {

    }

    @FXML
    void removeFromRecipe(ActionEvent event) {

    }

}