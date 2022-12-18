package sk.upjs.ics.chladnicka;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class editFavouritesController {

	private FavouriteFxModel model;
	
	private List<Favourite> favourite;
		
    @FXML
    private ListView<Favourite> favouriteListView;

    @FXML
    private ListView<Recipe> recipesListView;
    
 
    public editFavouritesController() {
    	model = new FavouriteFxModel();
    }

//	public editFavouritesController(List<Favourite> favouriteListView) {
//		this.favourite = favouriteListView;
//		this.favouriteListView = FXCollections.observableArrayList(favourite);
//	}

	@FXML
    void initialize() {
    	recipesListView.setItems(model.getRecipeModel());
    	favouriteListView.setItems(model.getFavouriteModel());
    }
    
    
    
    
    
    

    @FXML
    void addToFavoritesButton(ActionEvent event) {

    }

    @FXML
    void insertIntoFavoritesButton(ActionEvent event) {

    }

    @FXML
    void removeFromFavoritesButton(ActionEvent event) {

    }

}

