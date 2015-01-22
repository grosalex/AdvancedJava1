
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		Process proc;
		try {
			proc = Runtime.getRuntime().exec("traceroute fr.wikipedia.org");
			try {
				proc.waitFor();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			BufferedReader buf = new BufferedReader(new InputStreamReader(
					proc.getInputStream())); 
			String line = "";
			Pattern p = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");

			while ((line = buf.readLine()) != null) {
				Matcher m = p.matcher(line);
				if(m.find())
					System.out.println(m.group());
			} 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
