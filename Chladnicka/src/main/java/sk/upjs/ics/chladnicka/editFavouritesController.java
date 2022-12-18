package sk.upjs.ics.chladnicka;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.Recipe;

public class editFavouritesController {

	private FavouriteFxModel model;
		
    @FXML
    private ListView<Favourite> favoriteListView;

    @FXML
    private ListView<Recipe> recipesListView;
    
 
    public editFavouritesController() {
    	model = new FavouriteFxModel();
    }
    
    public editFavouritesController(ListView<Favourite> favouriteListView) {
    	model = new FavouriteFxModel(favouriteListView);
    }
    
    

	@FXML
    void initialize() {
    	recipesListView.setItems(model.getRecipeModel());
    	favoriteListView.setItems(model.getFavouriteModel());
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

