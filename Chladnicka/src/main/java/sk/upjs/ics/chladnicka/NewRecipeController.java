package sk.upjs.ics.chladnicka;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class NewRecipeController {

    @FXML
    private TextArea Steps;

    @FXML
    private TableView<?> table;

    @FXML
    void addIngredientButton(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader =
                    new FXMLLoader(getClass().getResource("Ingredients.fxml"));
            //Subject subject = subjectsComboBox.getSelectionModel().getSelectedItem();
            IngredientsController controller = new IngredientsController();
            fxmlLoader.setController(controller);
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
    void createButton(ActionEvent event) {

    }

    @FXML
    void ingredients(ActionEvent event) {

    }

    @FXML
    void quantity(ActionEvent event) {

    }

    @FXML
    void recipeName(ActionEvent event) {

    }

    @FXML
    void selectButton(ActionEvent event) {

    }

}
