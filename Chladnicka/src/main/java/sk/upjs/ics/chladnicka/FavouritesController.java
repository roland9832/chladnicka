package sk.upjs.ics.chladnicka;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.Favourite;

public class FavouritesController {

	private FavouriteFxModel model;
	
	@FXML
    private ListView<Favourite> favouriteListView;
	
	 

	 
	public FavouritesController() {
    	model = new FavouriteFxModel();
    }
    
    @FXML
    void initialize() {
    	favouriteListView.setItems(model.getFavouriteModel());
    	
    }
    

    @FXML
    void editFavoritesButton(ActionEvent event) {
    	 try {
             FXMLLoader fxmlLoader =
                     new FXMLLoader(getClass().getResource("EditFavourites.fxml"));
             editFavouritesController controller = new editFavouritesController(favouriteListView);
             fxmlLoader.setController(controller);
             Parent parent = fxmlLoader.load();
             Scene scene = new Scene(parent);
             Stage stage = new Stage();
             stage.setScene(scene);
             stage.setTitle("Edit Favourites");
             stage.initModality(Modality.APPLICATION_MODAL);
             stage.showAndWait();
             //updateTable();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
    }

}
