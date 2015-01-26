package com.ece.bmb.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class View{
	
	private Stage primaryStage;
	private Process processGraph;

	Process proc;
	
	private static String DOT = "C:/Program Files (x86)/Graphviz2.38/bin/dot.exe";//Windows	
	
	
	public View(Stage primaryStage){
		this.primaryStage=primaryStage;
		try {
			processGraph = Runtime.getRuntime().exec(DOT+" -Tpng dotfile.dot -o graph.png");
			processGraph.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void start() {
		primaryStage.setTitle("Traceroute"); 
		
		Image image = new Image("file:graph.png");
		ImageView imageView1 = new ImageView();
        imageView1.setImage(image);
		
		VBox vb = new VBox();
		HBox hb = new HBox();
		StackPane stack = new StackPane();
		
		TextField url = new TextField();
		Button trace = new Button("Start Traceroute");
		trace.setOnAction(new EventHandler<ActionEvent>() {		 
	          @Override
	          public void handle(ActionEvent event) {

	          }
	    });
		
		
        stack.getChildren().addAll(imageView1);
		hb.getChildren().addAll(url,trace);
		vb.getChildren().addAll(hb,stack);
		
		primaryStage.setScene(new Scene(vb, 300, 250));
        primaryStage.show();
	}
}
