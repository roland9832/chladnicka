package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Allergie;
import sk.upjs.ics.chladnicka.storage.AllergieDao;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Measure;
import sk.upjs.ics.chladnicka.storage.MeasureDao;

public class EditIngredientController {

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
	private Label ingredientLable;

	@FXML
	private ComboBox<Measure> measureComboBox;

	@FXML
	private TextField quantityTextField;

	private Ingredient ingredient;

	public EditIngredientController(Ingredient ingredient) {
		this.ingredient = ingredient;

	}

	@FXML
	void initialize() {
		ingredientLable.setText(ingredient.getName());
		List<Measure> measures = measureDao.getAll();
		selectedMeasureModel = FXCollections.observableArrayList(measures);
		measureComboBox.setItems(selectedMeasureModel);
		measureComboBox.setPromptText("Select");
		List<Allergie> allergies = allergieDao.getAll();
		selectedAllergieModel = FXCollections.observableArrayList(allergies);
		allergenComboBox.setItems(selectedAllergieModel);
		allergenComboBox.setPromptText("Select");
	}

	@FXML
	void editButton(ActionEvent event) {
	
		Ingredient ingredient = new Ingredient();
		ingredient.setId(this.ingredient.getId());
		ingredient.setName(this.ingredient.getName());
		if(!quantityTextField.getText().isBlank()) {
    		ingredient.setQuantity_fridge(Double.parseDouble(quantityTextField.getText()));
    	}else {
    		ingredient.setQuantity_fridge(this.ingredient.getQuantity_fridge());
    	}
		if(allergenComboBox.getSelectionModel().getSelectedItem() != null) {
			ingredient.setAlergie(allergenComboBox.getSelectionModel().getSelectedItem());
		}else {
			ingredient.setAlergie(this.ingredient.getAlergie());
		}
		if(measureComboBox.getSelectionModel().getSelectedItem() != null) {
			ingredient.setMeasure(measureComboBox.getSelectionModel().getSelectedItem());
		}else {
			ingredient.setMeasure(this.ingredient.getMeasure());
		}
		ingredientDao.save(ingredient);
		allergenComboBox.getScene().getWindow().hide();
	}
}
