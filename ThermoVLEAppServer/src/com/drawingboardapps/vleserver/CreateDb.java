package com.drawingboardapps.vleserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

//this class creates the tables if they don't exist
public class CreateDb{

	public static final String KEY_ROWID = "_id";
	
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
	public static final String KEY_LDEN = "Vliq"; //will be ldens, changes in MergeDBs
	public static final String KEY_TDEN = "Tdens";
	public static final String KEY_FORMULA = "Formula";
	
	private static final String TAG = "CreateDb";
	private String table_name;
//	String path = "jdbc:sqlite:/Users/Tricknology/Desktop/FlashDrive/ProgrammingStuff/Thermo/DBStuff/";
	String path = DirData.dbdir;
	String file;

	public CreateDb(String tablE_name){
		table_name = tablE_name;
		file = table_name;
	}
	public boolean makeDbFromBlock(ArrayList<String> keys,String tablename){
		boolean made = true;
		Connection connection = null;
		// build path for remote server
		String fullpath = path+file;
		try
		{
			// create a database connection
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection(fullpath);
			Statement statement = connection.createStatement();
			statement.setQueryTimeout(30);  // set timeout to 30 sec.
			
			
				String build_statement = "create table if not exists " + 
						tablename + 
						" (_id integer primary key autoincrement, ";
				int i = 0;
				for (i = 0; i < keys.size()-1;i++){
					build_statement = build_statement+keys.get(i)+" text, ";
				}
				build_statement = build_statement+keys.get(i)+" text)";
				System.out.println(build_statement);
				statement.execute(build_statement);
		}
		catch(SQLException e)
			{
				// if the error message is "out of memory", 
				// it probably means no database file is found
				Log.v(TAG, e.getMessage());
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				Log.v(TAG, e.getMessage());
			}
			finally
			{
				try
				{
					if(connection != null)
						connection.close();
				}
				catch(SQLException e)
				{
					// connection close failed.
					Log.v(TAG, e.getMessage());
				}
			}
			return made;
	}
	
}