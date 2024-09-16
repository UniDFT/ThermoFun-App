package com.drawingboardapps.vleserver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Test;

public class DecodeCSVTest {


	String TAG = "testcase";
	ArrayList<HashMap<String,String>> output;
	@Test
	public void test() {
		DecodeCSV dc = new DecodeCSV();
		String path = "/Users/Tricknology/Desktop/Programming/Thermo/";
		String filename = "chemdb.csv";
		try {
			output = dc.toMatrix(path, filename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			fail("IO Exception" + e.toString());
			Log.v(TAG, e.toString());
		}
		System.out.println(TAG+" "+"Starting output ................");
		System.out.println();
		for(int i = 0; i<output.size(); i++){
			System.out.print(i + " ");
			HashMap<String,String> row = new HashMap<String,String>();
			row = output.get(i);
			Object[] keys = row.keySet().toArray();
			for (int j = 0; j< keys.length; j++){
		
				System.out.print(TAG+" "+keys[j] +" = ");
				System.out.println(TAG+" "+row.get(keys[j]));
			}
		}
	}

}
