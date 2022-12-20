package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.Allergie;
import sk.upjs.ics.chladnicka.storage.AllergieDao;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Measure;
import sk.upjs.ics.chladnicka.storage.MeasureDao;

public class editQuantityController {

	AllergieDao allergieDao = DaoFactory.INSTANCE.getAllergieDao();

	MeasureDao measureDao = DaoFactory.INSTANCE.getMeasureDao();

	IngredientDao ingredientDao = DaoFactory.INSTANCE.getIngredientDao();

	private ObservableList<Ingredient> selectedIngredientModel = FXCollections
			.observableArrayList(new ArrayList<Ingredient>());

	private ObservableList<Measure> selectedMeasureModel;

	private ObservableList<Allergie> selectedAllergieModel;
	@FXML
	private ComboBox<Allergie> allergenComboBox;

	@FXML
	private TextField ingredientNameTextField;

	@FXML
	private ComboBox<Measure> measureComboBox;

	@FXML
	private TextField quantityTextField;

	private Ingredient ingredient;

	public editQuantityController(Ingredient ingredient) {
		this.ingredient = ingredient;
	}
	
	void initialize() {
		List<Measure> measures = measureDao.getAll();
    	selectedMeasureModel = FXCollections.observableArrayList(measures);
    	measureComboBox.setItems(selectedMeasureModel);
    	measureComboBox.setPromptText("Select");
    	List<Allergie> allergies = allergieDao.getAll();
    	selectedAllergieModel = FXCollections.observableArrayList(allergies);
    	allergenComboBox.setItems(selectedAllergieModel);
	}
	
	
	@FXML
	void editButton(ActionEvent event) {
		
	}
}