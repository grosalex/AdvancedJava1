
import com.ece.bmb.view.View;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{

	private View v;	


	public void start(Stage primaryStage) {
		v = new View(primaryStage);
		v.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
