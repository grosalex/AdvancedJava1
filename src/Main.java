

import com.ece.bmb.view.View;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.stage.Stage;


public class Main extends Application{

	private View v;	


	public void start(Stage primaryStage) {
		String result=new String("digraph mon_graphe {\n");
		v = new View(primaryStage);
		v.start();
		ArrayList<String> parents = new ArrayList<String>();
		ArrayList<String> children = new ArrayList<String>();
		Process proc;

		try {
<<<<<<< HEAD
			proc = Runtime.getRuntime().exec("java -jar fakeroute.jar 91.199.6.42");

=======
			proc = Runtime.getRuntime().exec("java -jar fakeroute.jar ece.fr");
>>>>>>> cb13169f5545d9decacbe677a2a6d56203f1ae14
			proc.waitFor();
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
			String line = "";
			Pattern ipRegEx = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");

			while ((line = buf.readLine()) != null) {
				Matcher ip = ipRegEx.matcher(line);
				
				while(ip.find()) {
					//System.out.print(ip.group()+ " ");
					children.add(ip.group());
				}
				for(int i=0;i<parents.size();i++){
					for(int j=0;j<children.size();j++){
						if(parents.get(i).compareTo(children.get(j))!=0)
						System.out.println(parents.get(i)+"->"+children.get(j));
						result=result+"\""+parents.get(i)+"\""+" -> "+"\""+children.get(j)+"\""+";\n";
					}
				}
				parents.clear();
				parents.addAll(children);
				children.clear();
				//System.out.println();
			} 
			result=result+"}";
			System.out.println(result);
			PrintWriter out = new PrintWriter("dotFile.dot");
			out.println(result);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);

	}
}
