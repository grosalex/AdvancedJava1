package com.ece.bmb.controller;


import com.ece.bmb.model.Model;
import com.ece.bmb.view.View;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.application.Application;
import javafx.stage.Stage;


public class Controller extends Application{

	private View v;	
	private Model model;

	public void start(Stage primaryStage) {
		model=new Model();
		v = new View(primaryStage);
		doTraceroute("ece.fr");
		v.start(this);
		
	}
	public void copyFile(File srcFile, File destFile) {
		try {
			model.copyFile(srcFile,destFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		launch(args);

	}
	
	public void doTraceroute(String dest) {
		Process proc;
		ArrayList<String> parents = new ArrayList<String>();
		ArrayList<String> children = new ArrayList<String>();
		String result=new String("digraph mon_graphe {\n");
		try {

			proc = Runtime.getRuntime().exec("java -jar fakeroute.jar "+dest);
			proc.waitFor();
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
			String line = "";
			Pattern ipRegEx = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");

			while ((line = buf.readLine()) != null) {
				Matcher ip = ipRegEx.matcher(line);
				
				while(ip.find()) {
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
			} 
			result=result+"}";
			System.out.println(result);
			PrintWriter out = new PrintWriter("dotFile.dot");
			model.generateDotFile();
			out.println(result);
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
