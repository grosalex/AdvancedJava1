package com.ece.bmb.view;

import java.io.*;
import java.nio.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;


public class View{
	
	private Stage primaryStage;
	private Process processGraph;
	private String OS;
	private String DOT;	
	
	
	public View(Stage primaryStage){
		this.primaryStage=primaryStage;
		OS = System.getProperty("os.name").toLowerCase();
		
		try {
			if(isWindows()) {
				DOT = "C:/Program Files (x86)/Graphviz2.38/bin/dot.exe";
			}
			if(isUnix()) {
				DOT= "dot";
			}
			processGraph = Runtime.getRuntime().exec(DOT+" -Tpng dotfile.dot -o graph.png");
			processGraph.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void copyFile(File source, File dest) throws IOException{
		
		if(!dest.exists()) {
			  dest.createNewFile();
			 }
			 InputStream in = null;
			 OutputStream out = null;
			 try {
			  in = new FileInputStream(source);
			  out = new FileOutputStream(dest);
			    
			  // Transfer bytes from in to out
			  byte[] buf = new byte[1024];
			  int len;
			  while ((len = in.read(buf)) > 0) {
			   out.write(buf, 0, len);
			  }
			 }
			 finally {
			  if(in != null) {
			   in.close();
			  }
			  if(out != null) {
			   out.close();
			  }
			 }
	}

	public void start() {
		primaryStage.setTitle("Traceroute");
		
		File srcFile= new File("graph.png");
		
		MenuBar menuBar = new MenuBar();
		Menu menuHelp = new Menu("Help");
		menuBar.getMenus().addAll(menuHelp);
		
		Image image = new Image("file:graph.png");
		ImageView imageView1 = new ImageView();
        imageView1.setImage(image);
		
		Scene vb = new Scene(new VBox(),300,300);
		vb.setFill(Color.OLDLACE);
		HBox hb1 = new HBox();
		HBox hb2 = new HBox();
		ScrollPane sp = new ScrollPane();
		
		TextField url = new TextField();
		TextField name_save = new TextField();
		Button trace = new Button("Start Traceroute");
		trace.setOnAction(new EventHandler<ActionEvent>() {		 
	          @Override
	          public void handle(ActionEvent event) {

	          }
	    });
		Button save = new Button("Save Graphic");
		save.setOnAction(new EventHandler<ActionEvent>() {		 
	          @Override
	          public void handle(ActionEvent event) {
	        	  if ((name_save.getText() != null && !name_save.getText().isEmpty())){
	        		  File destFile= new File("SavedGraph/"+name_save.getText()+".png");
	        		  try {
						copyFile(srcFile,destFile);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	  }
	          }

	    });
		
		
        sp.setContent(imageView1);
		hb1.getChildren().addAll(url,trace);
		hb2.getChildren().addAll(name_save,save);
		((VBox) vb.getRoot()).getChildren().addAll(menuBar,hb1,sp,hb2);

		
		
		primaryStage.setScene(vb);
        primaryStage.show();
	}
	
	public boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}
	
	public boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
}
