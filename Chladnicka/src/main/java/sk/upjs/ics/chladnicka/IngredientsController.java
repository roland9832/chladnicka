package sk.upjs.ics.chladnicka;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.Allergie;
import sk.upjs.ics.chladnicka.storage.AllergieDao;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Measure;
import sk.upjs.ics.chladnicka.storage.MeasureDao;

public class IngredientsController {
	
	IngredientFxModel model;
	
	AllergieDao allergieDao = DaoFactory.INSTANCE.getAllergieDao();
	
	MeasureDao measureDao = DaoFactory.INSTANCE.getMeasureDao();
	
	IngredientDao ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
	
	private ObservableList<Ingredient> selectedIngredientModel = FXCollections
			.observableArrayList(new ArrayList<Ingredient>());

    @FXML
    private ComboBox<Allergie> allergenComboBox;

    @FXML
    private TextField ingredientNameTextField;

    @FXML
    private ListView<Ingredient> ingredientsListView;

    @FXML
    private ComboBox<Measure> measureComboBox;

    @FXML
    private TextField quantityTextField;
    
    public IngredientsController() {
    	model = new IngredientFxModel();
    }
    
    @FXML
    void initialize() {
    	ingredientsListView.setItems(model.getIngredientModel());
    	selectedIngredientModel.setAll(ingredientsListView.getItems());
    }

    @FXML
    void addButton(ActionEvent event) {

    }

    @FXML
    void removeButton(ActionEvent event) {

    }
    
    @FXML
    void editQuantityButton(ActionEvent event) {

    }

}
