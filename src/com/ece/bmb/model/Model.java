package com.ece.bmb.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Model {
	private String OS;
	private String DOT;	
	 

	public Model Model(){
		OS=new String();
		DOT=new String();
		return this;
	}
	public void generatePngFile(){
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
	public void generateDotFile(String dest) {
		Process proc;
		ArrayList<String> parents = new ArrayList<String>();
		ArrayList<String> children = new ArrayList<String>();
		String result=new String("digraph mon_graphe {\n");
		try {

			proc = Runtime.getRuntime().exec("java -jar fakeroute.jar "+dest);
			proc.waitFor();
			
			BufferedReader buf = new BufferedReader(new InputStreamReader(proc.getInputStream())); 
			String line = "";
			Pattern ipRegEx = Pattern.compile("(\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3})");
			System.out.println(InetAddress.getLocalHost().getHostAddress());
			parents.add(InetAddress.getLocalHost().getHostAddress());
			buf.readLine();
			while ((line = buf.readLine()) != null) {
				Matcher ip = ipRegEx.matcher(line);
				
				while(ip.find()) {
					children.add(ip.group());
				} 
				for(int i=0;i<parents.size();i++){
					for(int j=0;j<children.size();j++){
						if(parents.get(i).compareTo(children.get(j))!=0)
						result=result+"\""+parents.get(i)+"\""+" -> "+"\""+children.get(j)+"\""+";\n";
					}
				}
				parents.clear();
				parents.addAll(children);
				children.clear();
			} 
			result=result+"}";

			
			PrintWriter out = new PrintWriter("dotFile.dot");
			out.append(result);
			out.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
