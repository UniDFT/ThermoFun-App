package com.drawingboardapps.vleserver;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class XMLParser {

	public ArrayList<Object> parse(String filename_in) throws IOException{
		String path = DirData.thermodir;
		String filename = path+filename_in;
		String tag0 = "<TR>";
		String tag1 = ">";
		String tag2 = "<";
		String tag3 = "</TR>";
		String tag4 = "<TD/>";
		String tag5 = "<TH/>";
		String tag6 = "<Table>";
		String tag7 = "</Table>";
		String tag8 = "<Figure>";
		String tag9 = "</Figure>";
		String tag10 = "Image";
		String tag11 = "</TD>";
		String AT = "Actual Text";
		String tag12 = "</TH>";
		String tag13 = "DelHf0";
		int count = 0,row_count = 0, col_count = 0,num_cols = 0;
		ArrayList<Object> list = new ArrayList<Object>();
		ArrayList<String> keys = new ArrayList<String>();
		HashMap<String,String> component = null;
		//make a buffered reader that reads the file, then:
		FileInputStream fstream = new FileInputStream(filename);
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader buffered_reader = new BufferedReader(new InputStreamReader(in));
		String strLine = buffered_reader.readLine();
		boolean reidprops = filename_in.compareTo("ReidProps1.xml") == 0;
		while (strLine!=(null)){ //begin while loop
			//if data beginning @ <TR>
			if (strLine.contains(tag0) && count == 0){ 
				//getting keys
				boolean flag = false;
				strLine = buffered_reader.readLine();
				//Data not ending at </TR>
				while (!strLine.contains(tag3)){
					//if valid line containing data
					if (strLine.equals("") || strLine.compareTo(tag5) == 0 || strLine.equals("	"))
						//not this file name, we need those headers
						strLine = buffered_reader.readLine();
					else {
						if (reidprops){
							System.out.println("ReidProps" + strLine);
							while (!strLine.contains("</"))
								strLine = buffered_reader.readLine();
						}
						count++; 
						String[] temp_ary = strLine.split(tag1);
						System.out.println("tempAry = "+strLine);
						String value = temp_ary[1];
						temp_ary = value.split(tag2);
						value = temp_ary[0];
						if (value.contains(","))
							value = value.split(",")[0];
						if (value.contains(".")){
							value = value.split("\\.")[0];
						}
						if (value.contains("no")){
							value = value.split("no")[0];
						}
						if (value.contains("factor")){
							value = value.split("factor")[0];
						}
						if (value.contains(" ")){
							value = value.split("\\s")[0];
						}
						if (value.contains("Density")&& flag == false){
							value = "Density_min";
							flag = true;
						}else if (value.contains("Density")){
							value = "Density_max";
						}
						System.out.println("value = " + value);
						keys.add(value);
						strLine = buffered_reader.readLine();
					}
				}list.add(keys);
			}if (strLine.contains(tag6) && count != 0){
				while(!strLine.contains(tag3)){
					strLine = buffered_reader.readLine();
				}strLine = buffered_reader.readLine();
			}else if(strLine.contains(tag0)){
				//Component c = new Component();
				component = new HashMap<String,String>();
				int col = 0;
				strLine = buffered_reader.readLine();
				int i = 0;
				while (!strLine.contains(tag3)){
					if (strLine.contains(tag13)){
						int tag3count = 0;
						strLine = buffered_reader.readLine();
						while (tag3count < 2){
							strLine = buffered_reader.readLine();
							if (strLine.contains(tag3)){
								tag3count++;
								strLine = buffered_reader.readLine();
							}
						}
					}else if (strLine.equals("")||
							strLine.equals(" ")|| 
							strLine.equals("    ")|| 
							strLine.equals(tag0)||
							strLine.equals(tag3)||
							strLine.equals(tag5)||
							strLine.equals(tag6)||
							strLine.equals(tag7)||
							strLine.contains(tag8)||
							strLine.contains(tag9)||
							strLine.contains(tag10)||
							strLine.equals(tag11)){
						strLine = buffered_reader.readLine();
					}
					else if (!strLine.contains("</") && !strLine.contains("/>")){
//						strLine = buffered_reader.readLine();
//						while (!strLine.contains("</") || !strLine.contains("/>"))
//							strLine = buffered_reader.readLine();
					}
					else{
						System.out.println(strLine);
						String value;
						if (strLine.equals("<TD/>")){
							value = null;
						}else{
						String[] temp_ary = strLine.split(tag1);
						value = temp_ary[1];
						temp_ary = value.split(tag2);
						value = temp_ary[0];
						}
						String key = keys.get(col++);
						System.out.println(key + "," + value);
						component.put(key,value);	
						strLine = buffered_reader.readLine();
					}
				}
				if (component != null && !component.containsValue("Cmpd. no.")){
					list.add(component);
				}
//				else strLine = buffered_reader.readLine();
			}else strLine = buffered_reader.readLine();
		}//end while loop
		return list;
	}
}
