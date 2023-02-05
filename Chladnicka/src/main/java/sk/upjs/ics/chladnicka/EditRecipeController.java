package sk.upjs.ics.chladnicka;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class EditRecipeController {

    @FXML
    private TextField caloriesField;

    @FXML
    private Button createButton;

    @FXML
    private ComboBox<?> dietComboBox;

    @FXML
    private ComboBox<?> ingredientComboBox;

    @FXML
    private ListView<?> ingredientsListView;

    @FXML
    private TextField quantityField;

    @FXML
    private TextField recipeNameField;

    @FXML
    private TextArea stepsTextArea;

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