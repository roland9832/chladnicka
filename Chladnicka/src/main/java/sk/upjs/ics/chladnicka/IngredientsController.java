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
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.Allergie;
import sk.upjs.ics.chladnicka.storage.AllergieDao;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.Ingredient;
import sk.upjs.ics.chladnicka.storage.IngredientDao;
import sk.upjs.ics.chladnicka.storage.Measure;
import sk.upjs.ics.chladnicka.storage.MeasureDao;
import sk.upjs.ics.chladnicka.storage.ObjectUndeletableException;

public class IngredientsController {
	
	private DialogPane dialog;
	
	IngredientFxModel model;
	
	AllergieDao allergieDao = DaoFactory.INSTANCE.getAllergieDao();
	
	MeasureDao measureDao = DaoFactory.INSTANCE.getMeasureDao();
	
	IngredientDao ingredientDao = DaoFactory.INSTANCE.getIngredientDao();
	
	private ObservableList<Ingredient> selectedIngredientModel = FXCollections
			.observableArrayList(new ArrayList<Ingredient>());
	
//	private ObservableList<Measure> selectedMeasureModel = FXCollections.observableArrayList(new ArrayList<Measure>());
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
//    	selectedIngredientModel.setAll(ingredientsListView.getItems());
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
    	
    	if(name.isBlank()) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Nebol zadany nazov ingrediencie, prosim zadajte nazov ingrediencie");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
    	}
    	
    	if(!quantityTextField.getText().isBlank()) {
    		quantity = Double.parseDouble(quantityTextField.getText());
    	}else {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Nebolo zadane mnozstvo v chladnicke");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
    	}
    	
    	if(alergie == null) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Neboli vybrate alergeny, prosim vyberte alergen");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
    	}
    	if(measure == null) {
    		Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Nebola vybrana jednotka merania, prosim vyberte jednotku merania");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
    	}
    	
    	List<Ingredient> allIngredients = ingredientDao.getAll();
    	for (Ingredient ingredient : allIngredients) {
			if(ingredient.getName().equals(name)) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Ingrediencia sa uz nachadza v ingredienciach");
				dialog = alert.getDialogPane();
				dialog.getStyleClass().add("dialog");
				alert.show();
				return;
			}
		}
    	
    	Ingredient ingredient = new Ingredient(name,quantity,alergie,measure);
    	lastIngredient  = ingredientDao.save(ingredient);
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
    	if(ingredient != null) {
    		try {
				ingredientDao.delete(ingredient);
			}catch (ObjectUndeletableException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(e.getMessage());
	    		alert.showAndWait();
			}
    		ingredients.remove(ingredient);
    		selectedIngredientModel = FXCollections.observableArrayList(ingredients);
    		ingredientsListView.setItems(selectedIngredientModel);
    	}
    }
    
    @FXML
    void editIngredientButton(ActionEvent event) {
//    	Ingredient ingredient = ingredientsListView.getSelectionModel().getSelectedItem();
//    	try {
//			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditIngredient.fxml"));
//			editQuantityController controller = new editQuantityController(ingredient);
//			fxmlLoader.setController(controller);
//			Parent parent = fxmlLoader.load();
//			Scene scene = new Scene(parent);
//			Stage stage = new Stage();
//			stage.setScene(scene);
//			stage.setTitle("Edit Favourites");
//			stage.initModality(Modality.APPLICATION_MODAL);
//			stage.showAndWait();
//		
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	
    }

}
