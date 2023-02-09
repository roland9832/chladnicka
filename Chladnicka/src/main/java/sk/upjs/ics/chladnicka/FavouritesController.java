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
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.EntityUndeletableException;
import sk.upjs.ics.chladnicka.storage.Favourite;
import sk.upjs.ics.chladnicka.storage.FavouriteDao;

public class FavouritesController {
	
	private DialogPane dialog;

	private FavouriteFxModel model;

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
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("EditFavourites.fxml"));
			EditFavouritesController controller = new EditFavouritesController();
			fxmlLoader.setController(controller);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Edit Favourites");
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
			favouriteModel.clear();
			List<Favourite> tempList = favouriteDao.getAll();
			favouriteModel.addAll(tempList);
			favouriteListView.setItems(favouriteModel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void deleteFavoritesButton(ActionEvent event) {
		Favourite favourite = favouriteListView.getSelectionModel().getSelectedItem();
		List<Favourite> favourites = this.favourite;
		if (favourite != null) {
			try {
				favouriteDao.delete(favourite.getRecipe());
			} catch (EntityUndeletableException e) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setContentText(e.getMessage());
				alert.showAndWait();
			}
			favourites.remove(favourite);
			this.favourite = favourites;
			favouriteModel = FXCollections.observableArrayList(this.favourite);
			favouriteListView.setItems(favouriteModel);
		}

	}
	
	@FXML
    void showFavoritesButton(ActionEvent event) {
		Favourite favourite = favouriteListView.getSelectionModel().getSelectedItem();
		
		
		try {
			if(favourite == null) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setContentText("Need to select recipe to show");
				dialog = alert.getDialogPane();
				dialog.getStyleClass().add("dialog");
				alert.show();
				return;
			}
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShowRecipe.fxml"));
			ShowRecipeController controller5 = new ShowRecipeController(favourite.getRecipe());
			fxmlLoader.setController(controller5);
			Parent parent = fxmlLoader.load();
			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle(favourite.getRecipe().getRecipe_name());
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.showAndWait();
//			ingredientsComboBox.getSelectionModel().clearSelection();
//			diet.getSelectionModel().clearSelection();
//			recipesListView.setItems(clear);
		} catch (IOException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to select recipe to show");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
		
		
    }

}
