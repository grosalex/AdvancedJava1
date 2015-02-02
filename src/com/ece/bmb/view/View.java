package com.ece.bmb.view;

import java.io.*;
import java.net.MalformedURLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import com.ece.bmb.controller.Controller;

import javafx.embed.swing.SwingFXUtils;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class View{

	private Stage primaryStage;

	private Controller ctrl;

	private Image img;

	public View(Stage primaryStage){
		this.primaryStage=primaryStage;

	}


	public void start(Controller controller) {
		this.ctrl = controller;
		primaryStage.setTitle("Traceroute");

		MenuBar menuBar = new MenuBar();
		Menu menuHelp = new Menu("Help");
		Menu menuExit= new Menu("Exit");
		MenuItem exit = new MenuItem("Close Program");
		exit.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent t) {
				System.exit(0);
			}
		});
		MenuItem help = new MenuItem("Functionalities\n\t-Enter a ip address in the top field\n\t"
				+ "-Start the Traceroute\n\t"
				+ "-The graph of your traceroute will appear\n\t"
				+ "-You can save your graph by putting a name ien the text field and click on Save");
		menuHelp.getItems().addAll(help);
		menuExit.getItems().addAll(exit);
		menuBar.getMenus().addAll(menuHelp,menuExit);
		ImageView imageView1 = new ImageView();

		Scene vb = new Scene(new VBox(),1200,800);
		HBox hb1 = new HBox();
		HBox hb2 = new HBox();
		ScrollPane sp = new ScrollPane();

		TextField ipField = new TextField();

		Button trace = new Button("Start Traceroute");
		trace.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {

				if(!ipField.getText().isEmpty()) {
					Pattern ipRegEx = Pattern.compile("(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)");

					Matcher ipMatcher = ipRegEx.matcher(ipField.getText());
					if(ipMatcher.matches()) {
						ctrl.doTraceroute(ipField.getText());
						img = new Image("file:graph.png");
						imageView1.setImage(img);
					}
					
				}

			}
		});
		Button randoIp = new Button ("Random IP");
		randoIp.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {
				int rando1 = 1 + (int)(Math.random() * ((254 - 0) + 1));
				int rando2 = 1 + (int)(Math.random() * ((254 - 0) + 1));
				int rando3 = 1 + (int)(Math.random() * ((254 - 0) + 1));
				int rando4 = 1 + (int)(Math.random() * ((254 - 0) + 1));
				
				String addip = new String(rando1+"."+rando2+"."+rando3+"."+rando4);				
				ctrl.doTraceroute(addip);
				img = new Image("file:graph.png");
				imageView1.setImage(img);
			}
		});
		Button save = new Button("Save Graphic");
		save.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Save Image");

				File file = fileChooser.showSaveDialog(primaryStage);
				if (file != null) {
					try {
						ImageIO.write(SwingFXUtils.fromFXImage(imageView1.getImage(),
								null), "png", file);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}

		});
		Button load = new Button("Load Graphic");
		load.setOnAction(new EventHandler<ActionEvent>() {		 
			@Override
			public void handle(ActionEvent event) {
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Load Image");
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {

					try {
						imageView1.setImage(new Image(file.toURI().toURL().toExternalForm()));
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}

		});


		sp.setContent(imageView1);

		hb1.getChildren().addAll(ipField,trace,randoIp);
		hb2.getChildren().addAll(save,load);
		((VBox) vb.getRoot()).getChildren().addAll(menuBar,hb1,sp,hb2);



		primaryStage.setScene(vb);
		primaryStage.show();
	}
}
