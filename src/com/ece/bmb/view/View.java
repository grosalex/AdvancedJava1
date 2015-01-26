package com.ece.bmb.view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class View{
	
	private Stage primaryStage;
	private Process processGraph;
	private final Node rootIcon = new ImageView(new Image("file:folderIcon.png"));
	
	
	public View(Stage primaryStage){
		this.primaryStage=primaryStage;
		try {
			processGraph = Runtime.getRuntime().exec("dot -Tpng dotfile.dot -o graph.png");
			processGraph.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void start() {
		primaryStage.setTitle("Traceroute"); 
		
		
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
		
		TreeItem<String> rootItem = new TreeItem<String> ("Inbox", rootIcon);
        rootItem.setExpanded(true);
        for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<String> ("Message" + i);            
            rootItem.getChildren().add(item);
        }        
        TreeView<String> tree = new TreeView<String> (rootItem);
		
        stack.getChildren().add(tree);
		hb.getChildren().addAll(url,trace);
		vb.getChildren().addAll(hb,stack);
		
		primaryStage.setScene(new Scene(vb, 300, 250));
        primaryStage.show();
	}
}
