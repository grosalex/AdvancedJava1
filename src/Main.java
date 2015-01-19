
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

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
			  String output = "";
			 
			  while ((line = buf.readLine()) != null) {
			    output += line + "\n";
			  } 
			 
			  System.out.println(output);
			  String delim=" ";
			  String[] result = output.split(delim);
			  String[] reg = output.split("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
						"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
			  for(int i=0;i<result.length;i++){
				  if(i%25==0)
				  System.out.println(result[i]+"\n");
				  
			  }
			  System.out.println(reg);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
