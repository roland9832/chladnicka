package sk.upjs.ics.chladnicka;
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
import sk.upjs.ics.chladnicka.storage.*;

import java.io.IOException;
import java.util.*;

public class RecipesController {

    private RecipesFxModel model;

    private IngredientDao ingredientDao;

    private DietDao dietDao;

    private RecipeDao recipeDao;

    private ObservableList<String> items;

    private ObservableList<String> ingredient;

    private List<Recipe> selectedRecipes;

    private List<Recipe> selectedByIngredient;
    private List<Recipe> selectedByDiet;

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
        ingredient.add("");
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
        items.add("");
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
        // if ingredient and diet is not initial -> treba najst prienik
        item = FXCollections.observableArrayList(); // tu sa budu ukladat dobre recepty
        if (!ingredientsComboBox.getSelectionModel().getSelectedItem().equals("") && !(diet.getSelectionModel().getSelectedItem().equals(""))) {
            Ingredient ingredient = model.getByName(ingredientsComboBox.getSelectionModel().getSelectedItem()); //Ingredient
            Diet dietIn = model.getDietByName(diet.getSelectionModel().getSelectedItem()); // Diet
            selectedByIngredient = recipeDao.getByIngredient(ingredient);
            selectedByDiet = recipeDao.getByDiet(dietIn);
            for (int i = 0; i < selectedByIngredient.size(); i++) {
                for (int j = 0; j < selectedByDiet.size(); j++) {
                    if (selectedByIngredient.get(i).getRecipe_name().equals(selectedByDiet.get(j).getRecipe_name())) {
                        item.add(selectedByDiet.get(j).getRecipe_name());
                    }
                }
            }
        }
        // if ingredient is not initial and diet is initial
        else if (!ingredientsComboBox.getSelectionModel().getSelectedItem().equals("")  && diet.getSelectionModel().getSelectedItem().equals("")) {
            Ingredient ingredient = model.getByName(ingredientsComboBox.getSelectionModel().getSelectedItem());
            selectedByIngredient = recipeDao.getByIngredient(ingredient);
            for (int i = 0; i < selectedByIngredient.size(); i++) {
                item.add(selectedByIngredient.get(i).getRecipe_name());
            }
        }
        // if ingredient is initial and diet is not initial
        else if (ingredientsComboBox.getSelectionModel().getSelectedItem().equals("") && !(diet.getSelectionModel().getSelectedItem().equals(""))) {
            Diet dietIn = model.getDietByName(diet.getSelectionModel().getSelectedItem());
            selectedByDiet = recipeDao.getByDiet(dietIn);
            for (int i = 0; i < selectedByDiet.size(); i++) {
                item.add(selectedByDiet.get(i).getRecipe_name());
            }
        }
        System.out.println(item);
        recipesListView.setItems(item);
    }

    @FXML
    void showRecipeButton(ActionEvent event) {
        Recipe selectedRecipe = model.getRecipeByName(recipesListView.getSelectionModel().getSelectedItem());
        System.out.println(selectedRecipe);
        System.out.println(recipesListView.getSelectionModel().getSelectedItem());
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("ShowRecipe.fxml"));
            //Subject subject = subjectsComboBox.getSelectionModel().getSelectedItem();
            ShowRecipeController controller5 = new ShowRecipeController(selectedRecipe);
            fxmlLoader.setController(controller5);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(selectedRecipe.getRecipe_name());
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
