package com.drawingboardapps.vleserverJUnit;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import com.drawingboardapps.vleserver.Component;
import com.drawingboardapps.vleserver.DirData;

public class WriteCSVs {

	public static String filepath = DirData.thermodir+"/Test Data/";

	@SuppressWarnings("unchecked")
	public void writeTvsPcentVapor(String calc, String var,String hold, double holdval, double index, double from, double to, ArrayList<Object> data) throws IOException{
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath+ molpct + name;
		}
		fpath = fpath+var+(int)from+"-"+(int)to+hold+holdval+"/";
		File f = new File(fpath);
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"TvsPvap.csv",true);

		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));
//		System.out.println(var+ " = "+index);
//		System.out.println("vap_frac = "+params.get("vap_frac"));
		out.print(index+",");
		out.println(params.get("vap_frac"));
		out.close();
	}
	@SuppressWarnings("unchecked")
	public void writeTvsLdens(String calc,String var,String hold,double holdval, double index, double from, double to, ArrayList<Object> data) throws IOException{
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
		}
//		fpath = fpath+var+(int)from+"-"+(int)to+hold+holdval+"/";
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"TvsLdens.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(index+",");
		out.println(params.get("ldens"));
		out.close();
	}
	public void writePvsT(String calc, String var, String hold,
			double holdval, double index, double from, double to,
			ArrayList<Object> data) throws IOException{
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
		}
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"PvsT.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(index+",");
		out.println(params.get("temp"));
		out.close();
	}
	public void writePvsLdens(String calc, String var, String hold,
			double holdval, double index, double from, double to,
			ArrayList<Object> data) throws IOException {
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
		}
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"PvsLdens.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(index+",");
		out.println(params.get("ldens"));
		out.close();
	}
	public void writePvsLvol(String calc, String var, String hold,
			double holdval, double index, double from, double to,
			ArrayList<Object> data) throws IOException {
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
		}
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"PvsLvol.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(index+",");
		out.println(params.get("lvol"));
		out.close();
	}
	public void writeTvsP(String calc, String var, String hold,
			double holdval, double t, double from, double to,
			ArrayList<Object> data)throws IOException {
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
		}
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"TvsP.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(t+",");
		out.println(params.get("pres"));
		out.close();
		
	}
	public void writeTvsVvol(String calc, String var, String hold,
			double holdval, double t, double from, double to,
			ArrayList<Object> data)throws IOException {
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
		}
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"TvsVvol.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(t+",");
		out.println(params.get("lvol"));
		out.close();
	}
	public void writeTvsZa(String calc, String var, String hold,
			double holdval, double t, double from, double to,
			ArrayList<Object> data)throws IOException {
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		ArrayList<String> zas = new ArrayList<String>();
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
			zas.add(c.getZa()+"");
		}
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"TvsZA.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(t+",");
		int i = 0;
		if (zas.size() > 1)
		for(; i < zas.size()-1; i++){
			out.print(zas.get(i)+",");
		}
		out.println(zas.get(i));
		out.close();
	}
	public void writePvsVvol(String calc, String var, String hold,
			double holdval, double p, double from, double to,
			ArrayList<Object> data)throws IOException {
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		//build dynamic filename
		String fpath = filepath;
		for(int i = 1; i< data.size();i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
			fpath = fpath + molpct + name;
		}
		fpath = fpath+calc+var+(int)from+"-"+(int)to+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"PvsVvol.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));

//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		out.print(p+",");
		out.println(params.get("lvol"));
		out.close();
	}
	public void writeVLData(String calc, String var, String hold,
			double holdval, double t, double from, double to,
			ArrayList<Object> data)throws IOException {
		HashMap<String, String> params = new HashMap<String,String>();
		params = (HashMap<String, String>) data.get(0);

		String fpath = filepath+calc+var+(int)from+"-"+(int)to+hold+holdval+"/";
//		String fpath = filepath+var+(int)from+"-"+(int)to+hold+holdval+"/";
		File f = new File(fpath);
		if (!f.exists())
		f.mkdir();
		
		FileWriter fstream = new FileWriter(fpath+"VLData.csv",true);
		new BufferedWriter(fstream);
		PrintWriter out = new PrintWriter(new BufferedWriter(fstream));
		//build dynamic filename
		
		
		
		
		int i = 1;
		if (data.size() > 1)
		for(; i< data.size()-1;i++){
			Component c = (Component) data.get(i);
			String[] n = c.getName().split(" ");
			String name = n[0];
			String molpct = c.getPcent()+"";
//			fpath = fpath + molpct + name;
			out.print(t+",");
			out.print(c.getLiquid()+",");
			out.print(c.getVapor()+",");
		}
		Component c = (Component) data.get(i);
		String[] n = c.getName().split(" ");
		String name = n[0];
		String molpct = c.getPcent()+"";
		out.print(t+",");
		out.print(c.getLiquid()+",");
		out.println(c.getVapor());
		
//		System.out.println(var+ " = "+index);
//		System.out.println("ldens = "+params.get("ldens"));
		
		out.close();
	}
	
}
