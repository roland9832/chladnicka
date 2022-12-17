package sk.upjs.ics.chladnicka;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Launcher extends Application {
	
	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader fxmlLoader = 
				new FXMLLoader(getClass().getResource("MainScene.fxml"));
		MainSceneController controller = new MainSceneController();
		fxmlLoader.setController(controller);
		Parent parent = fxmlLoader.load();
		Scene scene = new Scene(parent);
		stage.setScene(scene);
		stage.setTitle("Chladnicka");
		stage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}
