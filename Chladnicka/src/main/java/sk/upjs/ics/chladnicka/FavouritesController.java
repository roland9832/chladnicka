package sk.upjs.ics.chladnicka;
import java.io.IOException;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.FavouriteDao;

public class FavouritesController {
	
	
	private ObservableList<Favourite> favouriteModel;
	 
	private FavouriteDao favouriteDao = DaoFactory.INSTANCE.getFavouriteDao();
	
	private List<Favourite> favourite;
	
	@FXML
    private ListView<Favourite> favouriteListView;
	
	 
    
    @FXML
    void initialize() {
    	favourite = favouriteDao.getAll();
    	favouriteModel = FXCollections.observableArrayList(favourite);
    	favouriteListView.setItems(favouriteModel);
    }
    

    @FXML
    void editFavoritesButton(ActionEvent event) {
    	 try {
             FXMLLoader fxmlLoader =
                     new FXMLLoader(getClass().getResource("EditFavourites.fxml"));
             editFavouritesController controller = new editFavouritesController();
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
