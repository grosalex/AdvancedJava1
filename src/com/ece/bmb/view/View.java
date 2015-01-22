package com.ece.bmb.view;

import fr.ece.fauberte.model.Student;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
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
		HBox hb = new HBox();
		StackPane root = new StackPane();
		
		TextField url = new TextField();
		Button trace = new Button("Start Traceroute");
		trace.setOnAction(new EventHandler<ActionEvent>() {		 
	          @Override
	          public void handle(ActionEvent event) {

	          }
	      });
		
		hb.getChildren().addAll(url,trace);
		vb.getChildren().addAll(hb,root);
		
		primaryStage.setScene(new Scene(vb, 300, 250));
        primaryStage.show();
	}
}
