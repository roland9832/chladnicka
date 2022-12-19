package sk.upjs.ics.chladnicka;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
					favouriteListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Favourite>() {

						@Override
						public void changed(ObservableValue<? extends Favourite> observable, Favourite oldValue,
								Favourite newValue) {
							if (newValue != null) {
								
							}
							
						}
					});
					
					
				}
			}
		}
	}

	@FXML
	void removeFromFavoritesButton(ActionEvent event) {
		Favourite favourite = favouriteListView.getSelectionModel().getSelectedItem();
		List<Favourite> favourites = new ArrayList<>();
		if(favourite != null) {
			
		}
	}

	@FXML
	void saveFavoritesButton(ActionEvent event) {

	}

}
