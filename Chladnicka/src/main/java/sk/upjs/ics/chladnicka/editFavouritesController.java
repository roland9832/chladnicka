package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

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
     

	@FXML
	private ListView<Recipe> recipesListView;

	@FXML
	private TextField hodnotenieTextField;
	
	private List<Favourite> favouriteToAdd;

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

		
		if (!hodnotenieTextField.getText().isBlank() && recipe != null) {
			int hodnotenie = Integer.parseInt(hodnotenieTextField.getText());
			if(hodnotenie>=0 && hodnotenie<=5) {
				Favourite favourite = new Favourite(recipe,hodnotenie);
				if(!selectedFavouriteModel.contains(favourite)) {
					FavouriteFxModel model1 = new FavouriteFxModel(favourite);
					ListView<Favourite> tempFavouriteListView = new ListView<Favourite>();
					tempFavouriteListView.setItems(model1.getFavouriteModel());
					favouriteListView = tempFavouriteListView;
					favouriteToAdd.add(favourite);
					
					
				}
			}
		}
	}


	@FXML
	void removeFromFavoritesButton(ActionEvent event) {
		
	}

	@FXML
	void saveFavoritesButton(ActionEvent event) {
		for (Favourite favourite : favouriteToAdd) {
			favouriteDao.save(favourite);
		}
	}

}
