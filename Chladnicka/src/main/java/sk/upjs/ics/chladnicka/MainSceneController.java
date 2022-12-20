package sk.upjs.ics.chladnicka;
import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class MainSceneController {

    @FXML
    private VBox suggested;

    @FXML
    void showFavorites(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("Favourites.fxml"));
            FavouritesController controller = new FavouritesController();
            fxmlLoader.setController(controller);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Favourites");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            //updateTable();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void showIngredients(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("Ingredients.fxml"));
            //Subject subject = subjectsComboBox.getSelectionModel().getSelectedItem();
            IngredientsController controller2 = new IngredientsController();
            fxmlLoader.setController(controller2);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ingredients");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            //updateTable();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @FXML
    void showRecipes(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("Recipes.fxml"));
            List<Recipe> list = DaoFactory.INSTANCE.getRecipeDao().getAll();
            RecipesController controller3 = new RecipesController();
            fxmlLoader.setController(controller3);
            Parent parent = fxmlLoader.load();
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Recipes");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
            //updateTable();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}

