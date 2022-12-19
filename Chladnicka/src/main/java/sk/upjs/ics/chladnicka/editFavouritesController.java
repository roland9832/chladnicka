package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.FavouriteDao;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class editFavouritesController {

	private DialogPane dialog;

	private FavouriteDao favouriteDao = DaoFactory.INSTANCE.getFavouriteDao();

	private FavouriteFxModel model;

//	private List<Favourite> favourite;

	private ObservableList<Favourite> selectedFavouriteModel = FXCollections
			.observableArrayList(new ArrayList<Favourite>());

	@FXML
	private ListView<Favourite> favouriteListView;

	@FXML
	private ListView<Recipe> recipesListView;

	@FXML
	private TextField hodnotenieTextField;

	private List<Favourite> favouriteToAdd = new ArrayList<>();

	public editFavouritesController() {
		model = new FavouriteFxModel();
	}

	@FXML
	void initialize() {
		recipesListView.setItems(model.getRecipeModel());
		favouriteListView.setItems(model.getFavouriteModel());
//    	favouriteListView.setItems(selectedFavouriteModel);
		selectedFavouriteModel.setAll(favouriteListView.getItems());
//    	System.out.println(selectedFavouriteModel);
	}

	@FXML
	void addToFavoritesButton(ActionEvent event) {
		Recipe recipe = recipesListView.getSelectionModel().getSelectedItem();

		if (recipe != null) {
			if (!hodnotenieTextField.getText().isBlank()) {
				int hodnotenie = Integer.parseInt(hodnotenieTextField.getText());
				if (hodnotenie >= 0 && hodnotenie <= 5) {
					Favourite favourite = new Favourite(recipe, hodnotenie);
					if (!selectedFavouriteModel.contains(favourite)) {
						List<Favourite> favourites = favouriteDao.getAll();
						favourites.add(favourite);
						selectedFavouriteModel = FXCollections.observableArrayList(favourites);
						favouriteListView.setItems(selectedFavouriteModel);
						favouriteToAdd.add(favourite);
					}
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Nebolo zadané hodnotenie, prosím vyplnte pole hodnotenie");
				dialog = alert.getDialogPane();
				dialog.getStyleClass().add("dialog");
				alert.show();
				return;
			}
		} else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Žiadny recept nie je vybraný, prosím vyberte recept");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
	}

	@FXML
	void removeFromFavoritesButton(ActionEvent event) {
		Favourite favourite = favouriteListView.getSelectionModel().getSelectedItem();

	}

	@FXML
	void saveFavoritesButton(ActionEvent event) {
		if (!favouriteToAdd.isEmpty()) {
			for (Favourite favourite : favouriteToAdd) {
				favouriteDao.save(favourite);
			}
		}
		favouriteListView.getScene().getWindow().hide();
	}

}
