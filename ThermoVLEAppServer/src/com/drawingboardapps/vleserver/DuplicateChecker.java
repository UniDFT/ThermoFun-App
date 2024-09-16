package com.drawingboardapps.vleserver;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


public class DuplicateChecker {

	private static final String TAG = "DuplicateChecker";
	int numCols, numRows;
	JSONArray jaryOuter, jaryInner;
	JSONObject jobjTemp;
	String[] colnames;
	ArrayList<String> commands = new ArrayList<String>();
	String tablename;
	
	String wher_ = " WHERE ";
	String an_ = " AND ";

	public DuplicateChecker(JSONArray jaryouter, String tname){
		this.jaryOuter = jaryouter;
		this.tablename = tname;

		System.out.println("tablename = " + tablename);
	
		
		numCols = colnames.length - 1; //id column at 0
		numRows = jaryOuter.length(); //instruction obj in 0 will take care of that later>>;
	}

	//builds insert statements row by row returns an array list of insert commands
	public ArrayList<String> getCommands(){

		for (int i = 0; i < numRows; i++){

			try {
				System.out.println("DupChecker -> getCommands: "+jaryOuter.get(0).toString());
				jaryInner = jaryOuter.getJSONArray(i);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				commands.add(buildStatement());
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("DupChecker Command " + commands.toString());
		}

		return commands;
	}

	private ArrayList<String> buildWhereCols(JSONArray outer) throws JSONException{
		ArrayList<String> combos = new ArrayList<String>();
		String builder = wher_ + " ";
		JSONArray inner = new JSONArray();

		//i = 0 might cause a problem
		for (int i = 0; i < outer.length(); i++){
			inner = outer.getJSONArray(i);
			JSONObject obj = inner.getJSONObject(0);
			int length = obj.length();
			for (int j = 1; j < length -1; j++){
				String col = colnames[j];
				String val = obj.getString(col);
				builder = builder + col +" LIKE " + "'" + val + "' ";
				if (j < (length - 2))
					builder = builder + "AND ";
			}
			combos.add(builder);
		}

		return combos;
	}

	private String buildStatement() throws JSONException {
		ArrayList<String> combos = buildWhereCols(jaryOuter);
		String builder = "";
		for (int i = 0; i < combos.size(); i++){
			builder = "SELECT _id FROM " + 
					tablename +
					combos.get(i);
		}

		System.out.println(TAG+" "+builder);
		return builder;

	}
	public boolean chkDups(ArrayList<String> commands, Statement statement) {
		boolean duplicate = false;
		for (int i = 0; i < commands.size(); i++){

			try {
				//execute statement
				ResultSet rs = statement.executeQuery(commands.get(i));
				ResultSetMetaData rsmd = rs.getMetaData();
				int numCols = rsmd.getColumnCount();
				
				boolean match = true;
				if (match != false)
				{
					duplicate = true;
					System.out.println("DUPLICATE!!");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("no duplicates or cant find db");
				//e.printStackTrace();
			}
			
			
		}
		return duplicate;
	}

}
