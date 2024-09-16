package com.drawingboardapps.vleserver;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class FileWrite {
	public static final String KEY_NUM_COMP = "ncomp";
	public static final String KEY_CALC = "calc";
	public static final String KEY_NAME = "Name";
	public static final String KEY_MWT = "Mol";
	public static final String KEY_OMEGA = "Omega"; //group
	public static final String KEY_T = "t";
	public static final String KEY_TC = "Tc"; //group
	public static final String KEY_P = "p";
	public static final String KEY_PC = "Pc"; //group
	public static final String KEY_VC = "Vc"; //group
	public static final String KEY_ZA = "za";
	public static final String KEY_ZC = "Zc";
	public static final String KEY_TBP = "Tb";
	public static final String KEY_LDEN = "Vliq";
	public static final String KEY_TDEN = "Tdens";
	public static final String KEY_Z = "z";
	public static final String KEY_OUTFILE = "OUTPUT";
	public static final String KEY_CAS = "CAS";
	public static String filename = DirData.thermodir;

	private static final String TAG = "FileWrite";
	public ArrayList<Component> components;
	

	public FileWrite(){

	}
	public void printPath() {
		String.format("%s/%s", System.getProperty("user.dir"), this.getClass().getPackage().getName().replace(".", "/"));

	}

	public ArrayList<Object> FortranRead(String filename2){
//		filename = getPath()+filename;
//		filename = "/Users/Tricknology/Desktop/FlashDrive/ProgrammingStuff/Thermo/ThermoInOut/OUTPUT2";
//		filename = "/home/techyes/jvm/apache-tomcat-7.0.34/thermoinout/";
		
//		System.out.println(filename);
		ArrayList<Object> list = new ArrayList<Object>();
		try {
			FileInputStream fstream = new FileInputStream(filename+"OUTPUT2");
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine = br.readLine();

			int calc = 0;
			FortranParser fp;
			fp = new FortranParser(br,components);
			boolean stillParsing = true;
			while (stillParsing){
				if (strLine.contains("ISOTHERMAL-FLASH")){
					list = fp.parseITFlash(list,strLine,2);
//					System.out.println("ISO FLASH");
					stillParsing = false;
				}
				else if(strLine.contains("DEW-TEMPERATURE")){
					list = fp.parseDTemp(list,strLine,3);
//					System.out.println("DEW-TEMPERATURE");
					stillParsing = false;
				}else if(strLine.contains("BUBBLE-TEMPERATURE")){
					list = fp.parseBTemp(list,strLine,4);
//					System.out.println("BUBBLE-TEMPERATURE");
					stillParsing = false;
				}else if(strLine.contains("BUBBLE-PRESSURE")){
					list = fp.parseBPres(list,strLine,5);
//					System.out.println("BUBBLE-PRESSURE");
					stillParsing = false;
				}else if(strLine.contains("DEW-PRESSURE")){
					list = fp.parseDPres(list,strLine,6);
//					System.out.println("DEW-PRESSURE");
					stillParsing = false;
				}else if(strLine.contains("LIQUID-DENSITY")){
					list = fp.parseLDens(list,strLine,7);
//					System.out.println("LIQUID-DENSITY");
					stillParsing = false;
				}
				else strLine = br.readLine();

			}
			br.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	public void delFile(String filename){
		File file = new File(filename);
		file.delete();
	}

	private ArrayList<Object> startFortran() {
		Thread run_fortran = new Thread(){
			@Override
			public void run(){
				String os = System.getProperty("os.name");
				RunExternalProg.goFortran(os);
			}
		};
		run_fortran.start();
		int i = 0;
		while(run_fortran.isAlive()){
			i++;
			if (i%1000 == 0){
//				System.out.println(TAG+" waiting.. "+i);
			}
		}
		return FortranRead(KEY_OUTFILE);
	}

	public ArrayList<Object> toFortran(Integer key_calc, Double T, Double P,ArrayList<Component> components){
		try{

			// Create file 

			FileWriter fstream = new FileWriter(filename+"input2.txt");
//			FileWriter fstream = new FileWriter("/Users/Tricknology/Desktop/FlashDrive/ProgrammingStuff/Thermo/ThermoInOut/input2.txt");
//			FileWriter fstream = new FileWriter("/home/techyes/jvm/apache-tomcat-7.0.34/thermoinout/");
			PrintWriter out = new PrintWriter(new BufferedWriter(fstream));
			out.println(key_calc);
			out.println(components.size());
			this.components = components;
			for (int i = 0; i < components.size(); i++){

				Component c = components.get(i);
				out.println(c.getFormula());
				out.print(c.getMw()+"	");
				out.print(c.getTn()+"	");
				out.print(c.getTc()+"	");
				out.print(c.getPc()+"	");
				out.print(c.getVc()+"	");
				out.print(c.getW()+"	");
				out.print(c.getLden()+"	");
				out.print(c.getTden()+ "	");
				//				out.print(c.getZa()+"	"+"\n");
				out.println(c.getZa());
			}
			out.println(" "+0);
			//			if(T != -1){
			out.print(" "+T+" ");
			//			}
			//			if(P != -1){
			out.println(" "+P+"\n");
			//			}

			for (int i = 0; i < components.size(); i++){
				Component c = components.get(i);
				out.write(c.getPcent()+" "); //%composition
			}
			out.write("\n");
			out.write("   "+"0"+"\n");
			//Close the output stream
			out.close();
			//			writeKeys(values);
		}catch (Exception e){//Catch exception if any
			Log.v(TAG+" Error: ",  e.getMessage());
		}
		return startFortran();
	}

	static String getPath() {
		String path = String.format("%s/%s", System.getProperty("user.dir"), RunExternalProg.class.getClass().getPackage().getName().replace(".", "/"));
		String newpath = path;
		StringBuilder builder = new StringBuilder(); 
		builder.append(newpath);

		while (newpath.length() != path.length() -9){
			builder.deleteCharAt(newpath.length()-1);
			newpath = builder.toString();
		}
//		System.out.println(TAG +" "+newpath);
		return newpath;
	}
}

