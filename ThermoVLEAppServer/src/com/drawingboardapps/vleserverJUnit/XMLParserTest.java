package com.drawingboardapps.vleserverJUnit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.junit.Test;

import com.drawingboardapps.vleserver.*;

public class XMLParserTest {

	private static final String TAG = "XMLParserTest";

	@Test
	public void test() {
		ArrayList<Object> data1 = null, data2 = null;
		String file1 = "reidpropsdens.xml";
		String file2 = "ReidProps1.xml";
		XMLParser xmlp = new XMLParser();
		try {
			data1 = xmlp.parse(file1);
			data2 = xmlp.parse(file2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.v(TAG, e.getLocalizedMessage());
		}
		if (data1 != null){
			ArrayList<String> keys = (ArrayList<String>) data1.get(0);
			for(int i = 1; i < data1.size(); i++){
				Object cpd = (Object) data1.get(i);
				HashMap<String,String> compound = (HashMap<String, String>) cpd;
				for(int j = 0; j < keys.size(); j++){
				}
			}
		}else{
			fail("data is null");
		}
		if (data2 != null){
			ArrayList<String> keys = (ArrayList<String>) data1.get(0);
			for(int i = 1; i < data2.size(); i++){
				HashMap<String,String> compound = (HashMap<String, String>) data2.get(i);
				for(int j = 0; j < keys.size(); j++){
					String value = compound.get(keys.get(j));
				}
			}
		}else{
			fail("data is null");
		}
		String table1 = "reidpropsdens";
		String table2 = "reidprops1";
		createTableFromData(data2,table2);
		createTableFromData(data1,table1);
		data1 = data1;
		data2 = data2;
	}

	@SuppressWarnings("unused")
	private void createTableFromData(ArrayList<Object> d, String tablename) {
		ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
		for(int i = 1; i < d.size(); i++){
			HashMap<String,String> compound = (HashMap<String, String>) d.get(i);
			if (!compound.containsValue("Name"))
				data.add(compound);
		}
		System.out.println("Creating Db...");
		CreateDb cdb = new CreateDb(tablename);
		ArrayList<String> keys = (ArrayList<String>) d.get(0);
		cdb.makeDbFromBlock(keys , tablename);
		System.out.println("Db Created...");
		QueryDb qdb = new QueryDb(tablename);
		try {
			System.out.println("Inserting XML Block...");
			qdb.insertXMLBlock(tablename, data);
			System.out.println("XML Block Inserted...");
		} catch (SQLException e) {
			System.out.println("SQL Exception on XML Block Insert...");
			Log.v(TAG, e.getMessage());
			e.printStackTrace();
			System.out.println(e.getMessage().toString());
		}
		qdb.closeConnection();
		System.out.println("Connection Closed...");
	}
}
