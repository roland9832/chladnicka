package sk.upjs.ics.chladnicka;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import sk.upjs.ics.chladnicka.storage.DaoFactory;
import sk.upjs.ics.chladnicka.storage.Diet;
import sk.upjs.ics.chladnicka.storage.DietDao;

public class AddDietController {

	private DialogPane dialog;
	
    @FXML
    private TextField dietTextField;

    DietDao dietDao = DaoFactory.INSTANCE.getDietDao();

    @FXML
    void addButton(ActionEvent event) {
    	String name = dietTextField.getText();
    	if (name.isBlank()) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setContentText("Need to put ingredient name");
			dialog = alert.getDialogPane();
			dialog.getStyleClass().add("dialog");
			alert.show();
			return;
		}
    	Diet diet = new Diet();
    	diet.setName(name);
    	dietDao.save(diet);
    	dietTextField.getScene().getWindow().hide();
    }
    
}
