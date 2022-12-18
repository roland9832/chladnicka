package sk.upjs.ics.chladnicka;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.FavouriteDao;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class editFavouritesController {

	private FavouriteDao favouriteDao = DaoFactory.INSTANCE.getFavouriteDao();

	private FavouriteFxModel model;

//	private List<Favourite> favourite;

	private ObservableList<Favourite> selectedFavouriteModel = FXCollections
			.observableArrayList(new ArrayList<Favourite>());

	@FXML
	private ListView<Favourite> favouriteListView;
//    private ListView<Favourite> tempFavouriteListView;

	@FXML
	private ListView<Recipe> recipesListView;

	@FXML
	private TextField hodnotenieTextField;

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
//		System.out.println(recipe);
//		System.out.println("by Recipe");
//		System.out.println(favouriteDao.getByRecipe(recipe));
//		System.out.println(selectedFavouriteModel);
		
		if (!hodnotenieTextField.getText().isBlank() && recipe != null) {
			int hodnotenie = Integer.parseInt(hodnotenieTextField.getText());
			if(hodnotenie>=0 && hodnotenie<=5) {
				System.out.println(hodnotenie);
			}
			
			if(!selectedFavouriteModel.contains(favouriteDao.getByRecipe(recipe))) {
				System.out.println("aaaaaaa");
			}

		}
	}

	@FXML
	void removeFromFavoritesButton(ActionEvent event) {

	}

	@FXML
	void saveFavoritesButton(ActionEvent event) {

	}

}
