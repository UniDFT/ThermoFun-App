package com.drawingboardapps.vleserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class DecodeCSV {
	String TAG = "DecodeCSV";
	private String[] colnames;

	public ArrayList<HashMap<String,String>> toMatrix(String path,String filename) throws IOException{
		// path = path to CSV
		// filename = CSV filename
		
		ArrayList<HashMap<String,String>> matrix = new ArrayList<HashMap<String,String>>();
		File file = new File(path+filename);
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line;
		if ((line = br.readLine()) != null) {
			System.out.println(line);
			colnames = line.split(",");
		}
		while ((line = br.readLine()) != null) {
			HashMap<String,String> hm = new HashMap<String,String>();
			String[] values = line.split(",");
			for (int i = 0; i < values.length; i++){
				System.out.println(colnames[i] +" "+values[i]);
				hm.put(colnames[i],values[i]);
			}
			matrix.add(hm);
		}
		br.close();
		System.out.print(TAG +" null matrix = ");
		System.out.println( matrix == null);
		return matrix;
	}

}
