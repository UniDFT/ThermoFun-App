package com.drawingboardapps.vleserver;

import java.io.File;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONStringer;

public class QueryDb {

	private static final String TAG = "QueryDb";
	String dbname = "chemdb.cdb"; //default
	Connection connection;
	JSONObject jobject;
	ArrayList<String> colnames;
	public QueryDb(String dbname){
		this.dbname = dbname;

	}

	public Statement createConnection(){
		connection = null;
		// build path for remote server
		String path = DirData.dbdir;
		String filename = dbname;
		String fullpath = path+filename;
		// create a database connection
		Statement statement = null;
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(fullpath);
			statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return statement;
	}

	public void closeConnection(){
		try
		{
			if(connection != null)
				connection.close();
		}
		catch(SQLException e)
		{
			// connection close failed.
			Log.v(TAG,e.toString());
		}
	}

	public boolean insertXMLBlock(String tablename, 
			ArrayList<HashMap<String,String>> matrix) throws SQLException{
		Statement statement = createConnection();
		//check for duplicates
		BuildInsertStatement build = new BuildInsertStatement(matrix, tablename);
		//generate the insert commands
		ArrayList<String> commands = build.getCommands();
		statement = createConnection();
		//insert row by row
		for (int i = 0; i < commands.size(); i++)
			try {
				//execute statement
//				System.out.println(commands.get(i));
				statement.executeUpdate(commands.get(i));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("nice try insertXMLBlock");
				System.out.println(dbname);
				Log.v(TAG, e.getMessage());
				closeConnection();
				return false;
			}
		closeConnection();
		return true;
	}
	public boolean insertBlock( 
			String tablename, 
			ArrayList<HashMap<String,String>> matrix) throws SQLException{

		Statement statement = createConnection();
		//check for duplicates
		BuildInsertStatement build = new BuildInsertStatement(matrix, tablename);
		//generate the insert commands
		ArrayList<String> commands = build.getCommands();
		statement = createConnection();
		//insert row by row
		for (int i = 0; i < commands.size(); i++)
			try {
				//execute statement
//				System.out.println(commands.get(i));
				statement.executeUpdate(commands.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
				System.out.println("nice try insertBlock");
				closeConnection();
				return false;
			}
		closeConnection();
		return true;
	}

	//this class returns everything about the users location: username, location, timestamp, continuous
	public ArrayList<HashMap<String, Object>> getChemByID(int chemID, String tablename) throws SQLException, JSONException {

		String qry = "SELECT * FROM " + 
				tablename +
				" WHERE " +
				"_id = " +
				chemID;
		System.out.println(TAG+" "+qry);
		Statement statement = createConnection();
		ResultSet rs = statement.executeQuery(qry);

		ResultSetMetaData rsmd = rs.getMetaData();

		return decodeRSMD(rs, rsmd.getColumnCount(),  getColnames(rsmd));
	}

	private ArrayList<HashMap<String, Object>> decodeRSMD(ResultSet rs, int columnCount,
			ArrayList<String> colnames) {

		ArrayList<HashMap<String, Object>> matrix = new ArrayList<HashMap<String, Object>>();
		int j = 0;
		try {
			while(rs.next())
			{
				int num_cols = colnames.size();
//				System.out.println(j++ +" QDB !!!!!! "+ num_cols);
				//pack it up and ship it to json back down to phone
				HashMap<String,Object> chem = new HashMap<String,Object>();
				for (int i = 0; i < num_cols; i++){ //get the columns
//					System.out.println(i + " " +num_cols);
					String key = colnames.get(i);
					Object value = (rs.getString(key));
					chem.put(key,value);// these are the columns
//					System.out.println(key +","+value);
				}
				matrix.add(chem);//these are the rows
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
			Log.v(TAG, e.getMessage());
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			Log.v(TAG, e.getMessage());
			return null;
		}
		closeConnection(); 
		return matrix;
	}

	public ArrayList<HashMap<String, Object>> getChemByName(String chemName, String tablename) throws SQLException, JSONException {

		String qry = "SELECT * FROM " + 
				tablename +
				" WHERE " +
				"name LIKE " +
				"'"+chemName+"';";
//		System.out.println(TAG+" "+qry);
		Statement statement = createConnection();
		ResultSet rs = statement.executeQuery(qry);

		ResultSetMetaData rsmd = rs.getMetaData();

		return decodeRSMD(rs, rsmd.getColumnCount(), getColnames(rsmd));
	}


	private ArrayList<String> getColnames(ResultSetMetaData rsmd) {
		int numel;
		ArrayList<String> colnames = new ArrayList<String>();
		try {
			numel = rsmd.getColumnCount();
			for (int i = 1; i <= numel; i++){
				colnames.add(rsmd.getColumnName(i));
			}
		} catch (SQLException e) {
			Log.v(TAG, e.getMessage());
			e.printStackTrace();
		}
		return colnames;
	}

	// returns all results in the table
	public ArrayList<HashMap<String, Object>> fetchEverything(String tablename) 
			throws SQLException {

		CreateDb db = new CreateDb(tablename);
		Statement statement = createConnection();
		String qry = "SELECT * FROM " + tablename;
//		System.out.println(TAG+" "+qry);
		//and?
		ResultSet rs = statement.executeQuery(qry);
		ResultSetMetaData rsmd = rs.getMetaData();
		int col_count = rsmd.getColumnCount();

		return decodeRSMD(rs, col_count,  getColnames(rsmd));
	}

	public JSONArray removeColumn(String tablename, String keyword, String column) 
			throws SQLException{
		Statement statement = createConnection();
		String qry = "DELETE FROM " 
				+tablename 
				+" WHERE "
				+column
				+" = '"
				+ "'"
				+keyword;

		ResultSet rs = statement.executeQuery(qry);
		ResultSetMetaData rsmd = rs.getMetaData();
		int numCols = rsmd.getColumnCount();
		JSONArray jaryout = new JSONArray();
		jaryout.put("success");
		
		return jaryout;
	}

	public JSONArray removeAll(String tablename) 
			throws SQLException{
		Statement statement = createConnection();
		String qry = "TRUNCATE TABLE "+tablename;
		ResultSet rs = statement.executeQuery(qry);
		ResultSetMetaData rsmd = rs.getMetaData();
		int numCols = rsmd.getColumnCount();
		JSONArray jaryout = new JSONArray();
		jaryout.put("success");
		
		return jaryout;
	}

	public ArrayList<HashMap<String, Object>> getChemByCAS(String casNo, String tablename) throws SQLException, JSONException {

		String qry = "SELECT * FROM " + 
				tablename +
				" WHERE " +
				"CAS LIKE " +
				"'"+casNo+"';";
//		System.out.println(TAG+" "+qry);
		Statement statement = createConnection();
		ResultSet rs = statement.executeQuery(qry);

		ResultSetMetaData rsmd = rs.getMetaData();

		return decodeRSMD(rs, rsmd.getColumnCount(), getColnames(rsmd));
	}
}


