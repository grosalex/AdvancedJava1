

import com.ece.bmb.view.View;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{

	private View v;	


	public void start(Stage primaryStage) {

		v = new View(primaryStage);
		v.start();

		Process proc;
		try {
			proc = Runtime.getRuntime().exec("java -jar fakeroute.jar ece.fr");

			proc.waitFor();

			BufferedReader buf = new BufferedReader(new InputStreamReader(
					proc.getInputStream())); 
			String line = "";
			Pattern ipRegEx = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
		
			Pattern hostRegEx = Pattern.compile("(\\S+)\\.[a-z]*(\\d)*");

			while ((line = buf.readLine()) != null) {
				Matcher ip = ipRegEx.matcher(line);
				Matcher host = hostRegEx.matcher(line);
				
				while(ip.find()) {
					System.out.print(ip.group()+ " ");
				
				}
				System.out.println();
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
