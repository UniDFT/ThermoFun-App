package com.drawingboardapps.vleserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class FortranParser {

	private BufferedReader br;
	private ArrayList<Component> components;
	public FortranParser(BufferedReader reader, ArrayList<Component> components){
		this.br = reader;
		this.components = components;
	}
	
	//////////////////Parsers
	public ArrayList<Object> parseITFlash(ArrayList<Object> list, String strLine,int calc) 
			throws IOException {
//		System.out.println("ISOTHERMAL-FLASH");
		HashMap<String, String> params = new HashMap<String,String>();
		String vapor = null;
		String liquid = null;
		String[] params1 = null, params2 = null ,params3 = null;
		while (!strLine.contains("CALCULATED")){
			strLine = br.readLine();
		}
		params1 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params2 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params3 = strLine.split("[\\s]+");
		String vap_frac = params1[4];
		String liq_vol = params2[3];
		String liq_dens = params3[3];
		params.put("vap_frac",vap_frac);
		params.put("lvol",liq_vol);
		params.put("ldens",liq_dens);
		list.add(params);
		while (!strLine.contains("--")){
			strLine = br.readLine();
		}strLine = br.readLine();
		int index = 0;
		while (!strLine.equals("")){
			Component comp = components.get(index);
			String[] split_string = strLine.split("[\\s]+");					
			vapor = split_string[3];
			liquid = split_string[4];
			comp.setC(calc+"");
			comp.setVapor(vapor);
			comp.setLiquid(liquid);
			list.add(comp);
			strLine = br.readLine();
			index++;
		}

		return list;
	}
	
	public ArrayList<Object> parseLDens(ArrayList<Object> list,
			String strLine, int calc) throws IOException {
		System.out.println("LIQ DENSITY");
		HashMap<String, String> params = new HashMap<String,String>();
		String[] params1 = null;
		while (!strLine.contains("CALCULATED")){
			strLine = br.readLine();
		}
		params1 = strLine.split("[\\s]+");
		String liq_dens = params1[4];
		params.put("ldens",liq_dens);
		list.add(params);
		return list;
	}
	
	public ArrayList<Object> parseDPres(ArrayList<Object> list,
			String strLine, int calc) throws IOException {
//		System.out.println("DEW PRESSURE");
		HashMap<String, String> params = new HashMap<String,String>();
		String vapor = null;
		String liquid = null;
		String[] params1 = null, params2 = null ,params3 = null;
		while (!strLine.contains("CALCULATED")){
			strLine = br.readLine();
		}
		params1 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params2 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params3 = strLine.split("[\\s]+");
		String pressure = params1[3];
		String liq_vol = params2[3];
		String liq_dens = params3[3];
		params.put("pres",pressure);
		params.put("lvol",liq_vol);
		params.put("ldens",liq_dens);
		list.add(params);
		while (!strLine.contains("--")){
			strLine = br.readLine();
		}strLine = br.readLine();
		int index = 0;
		while (!strLine.equals("")){
			Component comp = components.get(index);
			String[] split_string = strLine.split("[\\s]+");					
			vapor = split_string[2];
			liquid = split_string[3];
			comp.setC(calc+"");
			comp.setVapor(vapor);
			comp.setLiquid(liquid);
			list.add(comp);
			strLine = br.readLine();
			index++;
		}

		return list;

	}
	public ArrayList<Object> parseBPres(ArrayList<Object> list,
			String strLine, int calc) throws IOException {
//		System.out.println("BUBBLE PRESSURE");
		HashMap<String, String> params = new HashMap<String,String>();
		String vapor = null;
		String liquid = null;
		String[] params1 = null, params2 = null ,params3 = null;
		while (!strLine.contains("CALCULATED")){
			strLine = br.readLine();
		}
		params1 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params2 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params3 = strLine.split("[\\s]+");
		String pressure = params1[3];
		String liq_vol = params2[3];
		String liq_dens = params3[3];
		params.put("pres",pressure);
		params.put("lvol",liq_vol);
		params.put("ldens",liq_dens);
		list.add(params);
		while (!strLine.contains("--")){
			strLine = br.readLine();
		}strLine = br.readLine();
		int index = 0;
		while (!strLine.equals("")){
			Component comp = components.get(index);
			String[] split_string = strLine.split("[\\s]+");					
			vapor = split_string[2];
			liquid = split_string[3];
			comp.setC(calc+"");
			comp.setVapor(vapor);
			comp.setLiquid(liquid);
			list.add(comp);
			strLine = br.readLine();
			index++;
		}

		return list;
	}
	public ArrayList<Object> parseBTemp(ArrayList<Object> list,
			String strLine, int calc) throws IOException {
//		System.out.println("BUBBLE TEMP");
		HashMap<String, String> params = new HashMap<String,String>();
		String vapor = null;
		String liquid = null;
		String[] params1 = null, params2 = null ,params3 = null;
		while (!strLine.contains("CALCULATED")){
			strLine = br.readLine();
		}
		params1 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params2 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params3 = strLine.split("[\\s]+");
		String temp = params1[3];
		String vap_vol = params2[3];
		String liq_dens = params3[3];
		params.put("temp",temp);
		params.put("vvol",vap_vol);
		params.put("ldens",liq_dens);
		list.add(params);
		while (!strLine.contains("--")){
			strLine = br.readLine();
		}strLine = br.readLine();
		int index = 0;
		while (!strLine.equals("")){
			Component comp = components.get(index);
			String[] split_string = strLine.split("[\\s]+");					
			vapor = split_string[2];
			liquid = split_string[3];
			comp.setC(calc+"");
			comp.setVapor(vapor);
			comp.setLiquid(liquid);
			list.add(comp);
			strLine = br.readLine();
			index++;
		}

		return list;
	}
	public ArrayList<Object> parseDTemp(ArrayList<Object> list,
			String strLine, int calc) throws IOException {
//		System.out.println("DEW TEMP");
		HashMap<String, String> params = new HashMap<String,String>();
		String vapor = null;
		String liquid = null;
		String[] params1 = null, params2 = null ,params3 = null;
		while (!strLine.contains("CALCULATED")){
			strLine = br.readLine();
		}
		params1 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params2 = strLine.split("[\\s]+");
		strLine = br.readLine();
		params3 = strLine.split("[\\s]+");
		String temp = params1[3];
		String liq_vol = params2[3];
		String liq_dens = params3[3];
		params.put("temp",temp);
		params.put("lvol",liq_vol);
		params.put("ldens",liq_dens);
		list.add(params);
		while (!strLine.contains("--")){
			strLine = br.readLine();
		}strLine = br.readLine();
		int index = 0;
		while (!strLine.equals("")){
			Component comp = components.get(index);
			String[] split_string = strLine.split("[\\s]+");					
			vapor = split_string[2];
			liquid = split_string[3];
			comp.setC(calc+"");
			comp.setVapor(vapor);
			comp.setLiquid(liquid);
			list.add(comp);
			strLine = br.readLine();
			index++;
		}

		return list;
	}


}
