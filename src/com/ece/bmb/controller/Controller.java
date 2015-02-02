package com.ece.bmb.controller;


import com.ece.bmb.model.Model;
import com.ece.bmb.view.View;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;


public class Controller extends Application{

	private View v;	
	private Model model;

	public void start(Stage primaryStage) {
		model=new Model();
		v = new View(primaryStage);
		v.start(this);
		
	}
	// Method taken from http://www.javalobby.org/java/forums/t17036.html
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
			model.generateDotFile(dest);
			model.generatePngFile();


	}


}
