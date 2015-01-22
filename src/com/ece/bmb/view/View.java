package com.ece.bmb.view;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class View{
	
	private Stage primaryStage;
	
	public View(Stage primaryStage){
		this.primaryStage=primaryStage;
	}
	

	public void start() {
		primaryStage.setTitle("Traceroute"); 
		
		
		VBox vb = new VBox();
		
		primaryStage.setScene(new Scene(vb, 300, 250));
        primaryStage.show();
	}
}
