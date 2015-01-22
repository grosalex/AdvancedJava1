package com.ece.bmb.view;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class View{
	
	private Stage primaryStage;
	
	public View(Stage primaryStage){
		this.primaryStage=primaryStage;
	}
	

	public void start() {
		primaryStage.setTitle("Traceroute"); 
		
		
		StackPane stack = new StackPane();
		
		primaryStage.setScene(new Scene(stack, 300, 250));
        primaryStage.show();
	}
}
