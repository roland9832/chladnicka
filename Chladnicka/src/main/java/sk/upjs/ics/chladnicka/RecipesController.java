package sk.upjs.ics.chladnicka;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class RecipesController {

    @FXML
    void containsIngredient(ActionEvent event) {

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
            //updateTable();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @FXML
    void rbGlutenFree(ActionEvent event) {

    }

    @FXML
    void rbMilkFree(ActionEvent event) {

    }

    @FXML
    void rbVegan(ActionEvent event) {

    }

    @FXML
    void rbVegetarian(ActionEvent event) {

    }

    @FXML
    void recipeType(ActionEvent event) {

    }

    @FXML
    void searchButton(ActionEvent event) {

    }

}
