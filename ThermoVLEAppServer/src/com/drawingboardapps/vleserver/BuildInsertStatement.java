package com.drawingboardapps.vleserver;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class BuildInsertStatement {



	private static final String TAG = "BuildInsertStatement";


	JSONObject jobjTemp, jObj;


	String dbname = "chemdb";
	String tablename;

	HashMap<String,String> data;
	ArrayList<HashMap<String,String>> matrix;
	Object[] colnames;

	public BuildInsertStatement(ArrayList<HashMap<String,String>> matrix_, String tname){

		if (matrix_.size() != 0){
			this.matrix = matrix_; 
			this.tablename = tname;
			this.data = matrix_.get(0);
			colnames = data.keySet().toArray();		
//			System.out.println(TAG + " wtf");
		}else{
			Log.v(TAG, " matrix size is 0");
		}
	}


	//builds insert statements row by row returns an array list of insert commands
	public ArrayList<String> getCommands(){
		ArrayList<String> commands = new ArrayList<String>();
		//outer(inner(object1/row1, object2/row2, object3/row3, etc..))
		//starts at 1 because of instructions column at 0
		//counts number of rows because each object in the array is an arraylist containing an object!!
		//each json object contains key/value aka col/value for the row
		
		for (int i = 0; i < matrix.size(); i++){
			ArrayList<String> keys = new ArrayList<String>();
			ArrayList<String> values = new ArrayList<String>();
			HashMap<String,String> data = matrix.get(i);
			for (int j = 0; j < colnames.length;j++){
			keys.add(colnames[j]+"");
			values.add(data.get(colnames[j]));
			}
			String key = buildColStmt(keys);
			String val = buildValStmt(values);
			commands.add(buildStatement(key, val));
		}

		

		
//		System.out.println("BIS Command " + commands.toString());
		return commands;
	}

	private String buildStatement(String keys, String vals) {

		String builder = "INSERT INTO " + 
				tablename +
				" "+ 
				keys +
				"VALUES " +
				vals
				;
//		System.out.println(builder);
		return builder;

	}

	//					format									//
	//==============================================================
	//	INSERT INTO Store_Information (store_name, Sales, Date)
	//		VALUES ('Los Angeles', 900, 'Jan-10-1999')
	//==============================================================

	//builds the columns part of the build statement, returns a string in the format (col1, col2, col3, ...)
	private String buildColStmt(ArrayList<String> columns){
		String builder = "(";
		// start at 0  _id column already omitted.
		for (int j = 0; j < columns.size(); j++){
			builder = builder + 
					columns.get(j);
			if (j + 1 == columns.size()){
				builder = builder +
						") ";
			}else{
				builder = builder +
						", ";
			}			
		}
		return builder;
	}

	//builds the values part of the build statement, returns a string in the format ('string1', 'string2', 'string3',...)
	private String buildValStmt(ArrayList<String> vals){
		String builder = "(";
		int size = vals.size();
		for (int j = 0; j < size; j++){
			builder = builder + 
					"'" +
					vals.get(j) +
					"'";
			if (j + 1 == size){
				builder = builder +
						") ";
			}else{
				builder = builder +
						", ";
			}			
		}
//		System.out.println("BuilderInsertStatement values--> " + builder);
		return builder;

	}

}
