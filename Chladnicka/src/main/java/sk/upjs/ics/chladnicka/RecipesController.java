package sk.upjs.ics.chladnicka;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Diet;
import sk.upjs.ics.chladnicka.storage.DietDao;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Recipe;
import sk.upjs.ics.chladnicka.storage.RecipeDao;

public class RecipesController {

    private RecipesFxModel model;

    private IngredientDao ingredientDao;

    private DietDao dietDao;

    private RecipeDao recipeDao;

    private ObservableList<String> items;

    private ObservableList<String> ingredient;

    private List<Recipe> selectedRecipes;

    private List<Recipe> recipes;

    private List<Diet> selectedDiets;

    private ObservableList<String> item;

    @FXML
    private ComboBox<String> ingredientsComboBox;

    @FXML
    private ComboBox<String> diet;

    @FXML
    private ListView<String> recipesListView;

    public RecipesController(){
        model = new RecipesFxModel();
        ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
        dietDao = DaoFactory.INSTANCE.getDietDao();
        recipeDao = DaoFactory.INSTANCE.getRecipeDao();
    }

    //private RecipeDao recipeDao = DaoFactory.INSTANCE.getRecipeDao();
    private ObservableList<Ingredient> ingredientsModel;

    private List<Diet> dietModel;

    @FXML
    void initialize() {
        //logger.debug("inicialize running");
        // Ingredient
        List<Ingredient> ingredients = ingredientDao.getAll();
        ingredientsModel = FXCollections.observableArrayList(ingredients);
        ingredient = FXCollections.observableArrayList();
        for (int i = 0; i < model.getIngredientModel().size(); i++) {
            ingredient.add(model.getIngredientModel().get(i).getName());
        }
        System.out.println(ingredientsModel);
        ingredientsComboBox.setItems(ingredient);
        ingredientsComboBox.getSelectionModel().selectFirst();
        // Diet
        List<Diet> diets = dietDao.getAll();
        dietModel = FXCollections.observableArrayList(diets);
        items = FXCollections.observableArrayList();
        for (int i = 0; i < model.getDietModel().size(); i++) {
           items.add(model.getDietModel().get(i).getName());
        }
        diet.setItems(items);
        diet.getSelectionModel().selectFirst();
    }

    @FXML
    void createButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("NewRecipe.fxml"));
            //Subject subject = subjectsComboBox.getSelectionModel().getSelectedItem();
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
    void searchButton(ActionEvent event){
        item = FXCollections.observableArrayList();
        if (ingredientsComboBox.getSelectionModel().getSelectedItem() != null) {
            Ingredient ingredient = model.getByName(ingredientsComboBox.getSelectionModel().getSelectedItem());
            selectedRecipes = recipeDao.getByIngredient(ingredient);
            for(int i = 0; i < selectedRecipes.size(); i++) {
                System.out.println(selectedRecipes.get(i));
                item.add(selectedRecipes.get(i).getRecipe_name());
            }
        }
        if (diet.getSelectionModel().getSelectedItem() != null) {
            Diet dietIn = model.getDietByName(diet.getSelectionModel().getSelectedItem()); // Diet
            recipes = recipeDao.getByDiet(dietIn);
            for(int i = 0; i < recipes.size(); i++) {
                System.out.println(recipes.get(i));
                item.add(recipes.get(i).getRecipe_name());
            }
        }

        recipesListView.setItems(item);
    }

}
