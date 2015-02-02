package com.ece.bmb.view;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ece.bmb.controller.Controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class View{

	private Stage primaryStage;
	private Process processGraph;
	private String OS;
	private String DOT;	
	private Controller ctrl;


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


	// class taken from http://www.javalobby.org/java/forums/t17036.html
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

	public void start(Controller controller) {
		this.ctrl = controller;
		primaryStage.setTitle("Traceroute");

		File srcFile= new File("graph.png");

		MenuBar menuBar = new MenuBar();
		Menu menuHelp = new Menu("Help");
		Menu menuExit= new Menu("Exit");
		MenuItem exit = new MenuItem("Close Program");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		MenuItem help = new MenuItem("gifi");
		menuHelp.getItems().addAll(help);
		menuExit.getItems().addAll(exit);
		menuBar.getMenus().addAll(menuHelp,menuExit);

		Image image = new Image("file:graph.png");
		ImageView imageView1 = new ImageView();
		//imageView1.setImage(image);

		Scene vb = new Scene(new VBox(),400,300);
		HBox hb1 = new HBox();
		HBox hb2 = new HBox();
		ScrollPane sp = new ScrollPane();

		TextField url = new TextField();
		TextField name_save = new TextField();
		Button trace = new Button("Start Traceroute");
		trace.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {
				if(!url.getText().isEmpty()) {
					Pattern ipRegEx = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
					Matcher ip = ipRegEx.matcher(url.getText());
					if(ip.matches()) {
						ctrl.doTraceroute(url.getText());
					}
					imageView1.setImage(image);
				}

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
						name_save.clear();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		});
		Button load = new Button("Load Graphic");
		load.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {

			}

		});


		sp.setContent(imageView1);
		hb1.getChildren().addAll(url,trace);
		hb2.getChildren().addAll(name_save,save,load);
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
