package com.ece.bmb.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class Model {
	private String OS;
	private String DOT;	
	 

	public Model Model(){
		OS=new String();
		DOT=new String();
		return this;
	}
	public void generateDotFile(){
		OS = System.getProperty("os.name").toLowerCase();
		Process processGraph;
		try {
			if(isWindows()) {
				DOT = "C:/Program Files (x86)/Graphviz2.38/bin/dot.exe";
			}
			if(isUnix()) {
				DOT= "dot";
			}
			processGraph = Runtime.getRuntime().exec(DOT+" -Tpng dotFile.dot -o graph.png");
			processGraph.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void copyFile(File source, File dest) throws IOException{

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
	
	public boolean isWindows() {
		return (OS.indexOf("win") >= 0);
	}

	public boolean isUnix() {
		return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 );
	}
}
