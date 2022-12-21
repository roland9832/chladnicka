package sk.upjs.ics.chladnicka;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.Allergie;
import sk.upjs.ics.chladnicka.storage.AllergieDao;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.EntityUndeletableException;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Measure;
import sk.upjs.ics.chladnicka.storage.MeasureDao;

public class IngredientsController {

	private DialogPane dialog;

	IngredientFxModel model;

	AllergieDao allergieDao = DaoFactory.INSTANCE.getAllergieDao();

	MeasureDao measureDao = DaoFactory.INSTANCE.getMeasureDao();

	IngredientDao ingredientDao = DaoFactory.INSTANCE.getIngredientDao();

	private ObservableList<Ingredient> selectedIngredientModel = FXCollections
			.observableArrayList(new ArrayList<Ingredient>());

	private ObservableList<Measure> selectedMeasureModel;

	private ObservableList<Allergie> selectedAllergieModel;

	Ingredient lastIngredient;

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
	void addButton(ActionEvent event) {
		Allergie alergie = allergenComboBox.getSelectionModel().getSelectedItem();
		Measure measure = measureComboBox.getSelectionModel().getSelectedItem();
		String name = ingredientNameTextField.getText();
		double quantity;

		if (name.isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to put ingredient name");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}

		if (!quantityTextField.getText().isBlank()) {
			try {
				quantity = Double.parseDouble(quantityTextField.getText());
			}catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Insert numerical value");
				dialog = alert.getDialogPane();
				dialog.getStyleClass().add("dialog");
				alert.show();
				return;
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to put quantity of ingredient");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}

		if (alergie == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to select allergen");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
		if (measure == null) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to select unit of measure");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}

		List<Ingredient> allIngredients = ingredientDao.getAll();
		for (Ingredient ingredient : allIngredients) {
			if (ingredient.getName().equals(name)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Ingredient is already in. Try editing it");
				dialog = alert.getDialogPane();
				dialog.getStyleClass().add("dialog");
				alert.show();
				return;
			}
		}

		Ingredient ingredient = new Ingredient(name, quantity, alergie, measure);
		lastIngredient = ingredientDao.save(ingredient);
		List<Ingredient> ingredients = ingredientDao.getAll();
		selectedIngredientModel = FXCollections.observableArrayList(ingredients);
		ingredientsListView.setItems(selectedIngredientModel);
		ingredientNameTextField.clear();
		quantityTextField.clear();
	}

	@FXML
	void removeButton(ActionEvent event) {
		Ingredient ingredient = ingredientsListView.getSelectionModel().getSelectedItem();
		List<Ingredient> ingredients = ingredientDao.getAll();
		if (ingredient != null) {
			try {
				ingredientDao.delete(ingredient);

			} catch (EntityUndeletableException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
				return;
			}
			ingredients.remove(ingredient);
			selectedIngredientModel = FXCollections.observableArrayList(ingredients);
			ingredientsListView.setItems(selectedIngredientModel);
		}
	}

	@FXML
	void editIngredientButton(ActionEvent event) {
		Ingredient ingredient = ingredientsListView.getSelectionModel().getSelectedItem();
		System.out.println(ingredient);
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditIngredient.fxml"));

			EditIngredientController controller = new EditIngredientController(ingredient);

			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Edit Favourites");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			selectedIngredientModel.clear();
			List<Ingredient> tempList = ingredientDao.getAll();
			selectedIngredientModel.addAll(tempList);
			ingredientsListView.setItems(selectedIngredientModel);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to select ingredient to edit");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}

	}

}
