package com.drawingboardapps.vleserverJUnit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import org.junit.Test;

import sun.rmi.runtime.Log;

import com.drawingboardapps.vleserver.CreateDb;
import com.drawingboardapps.vleserver.QueryDb;

public class MergeDBs {
	private String t_name1 = "reidprops1";
	private String t_name2 = "reidpropsdens";
	private String t_name3 = "chemdata";
	private String[] cant_null = {"Tdens","Pc","Vliq", "Mol", "Omega", "Tc", "Tb", "Vc"};
	@Test
	public void test() {


		ArrayList<HashMap<String, Object>> table1 = getTable(t_name1);
		ArrayList<HashMap<String, Object>> table2 = getTable(t_name2);

		ArrayList<String> colnames = getColnames(table1, table2);
		ArrayList<HashMap<String,String>> data = getMatches(table1, table2, colnames);
		data = removeNullData(data);
		CreateDb cdb = new CreateDb(t_name3);
		cdb.makeDbFromBlock(colnames, t_name3);
		QueryDb qdb = new QueryDb(t_name3);
		try {
			qdb.insertBlock(t_name3, data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private ArrayList<HashMap<String, String>> removeNullData(
			ArrayList<HashMap<String, String>> data) {
		for(int i = 0; i < data.size(); i ++){
			HashMap<String, String> component = data.get(i);
			for(int j = 0; j < cant_null.length; j++){
//				System.out.println("i = "+i);
//				System.out.println("j = " + j);
				String cantbe = cant_null[j];
				String value = component.get(cantbe);
				if(value == null){
					data.remove(i--);
					j = cant_null.length;
				}
			}
		}
		return data;
	}


	private ArrayList<HashMap<String, String>> getMatches(ArrayList<HashMap<String, Object>> table1,
			ArrayList<HashMap<String, Object>> table2,
			ArrayList<String> colnames) {

		ArrayList<HashMap<String,String>> matches = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < table1.size(); i++ ){
			HashMap<String,Object> chem1 = table1.get(i);
			HashMap<String,Object> match = new HashMap<String,Object>();
			for (int j = 0; j < table2.size(); j++){
				HashMap<String,Object> chem2 = table2.get(j);
				String cas1 = (String) chem1.get("CAS");
				String cas2 = (String) chem2.get("CAS");
				String vliq = (String) chem2.get("Vliq");
				String molwt = (String) chem1.get("Mol");
				if(cas1.compareTo(cas2) == 0 && 
						(vliq.compareTo("null") != 0) &&
						(molwt.compareTo("null") != 0)){
					//match...
//					System.out.println("VL is empty: " + (vliq.compareTo("null") == 0));
//					System.out.println("VL is :"+vliq);
//					System.out.println("MW is empty: "+ (molwt.compareTo("null") == 0));
//					System.out.println("MW is: "+molwt);
					HashMap<String, String> matchData = getMatchData(i,j,table1,table2,colnames);
					if (matchData.size() >3){
						//convert vliq to ldens with mol wt and insert into matches
//						System.out.println("vliq = " + vliq);
						Double vl = Double.parseDouble(vliq);
//						System.out.println("Mol = " + molwt);
						Double mwt = Double.parseDouble(molwt);
						Double ldens = 1/vl*mwt; 
//						System.out.println("New value for "+
//								matchData.get("Name") +
//								" ldens is " + 
//								ldens);
						matchData.remove("Vliq");
						matchData.put("Vliq",ldens+"");
						matches.add(matchData);
					}
					
				}
			}
		}
		return matches;
	}

	private HashMap<String, String> getMatchData(int index1, int index2, 
			ArrayList<HashMap<String, Object>> table1, 
			ArrayList<HashMap<String, Object>> table2,
			ArrayList<String> colnames) {
		HashMap<String, String> matchData = new HashMap<String, String>();

		ArrayList<String> colnames2 = new ArrayList<String>();
		Set<String> colz1 = table1.get(0).keySet();
		Set<String> colz2 = table2.get(0).keySet();

		String[] cols1 = colz1.toArray(new String[0]);
		String[] cols2 = colz2.toArray(new String[0]);

		//		System.out.println("cols1 length = "+cols1.length);
		//		System.out.println("cols2 length = "+cols2.length);
		HashMap<String, String> matchData_test = getValidMatchData(cols1,colnames2,table1,index1,matchData);
		if (matchData_test != null)
			matchData = matchData_test;

		matchData_test = getValidMatchData(cols2,colnames2,table2,index2,matchData);
		if (matchData_test != null)
			matchData = matchData_test;

		return matchData;
	}


	private HashMap<String, String> getValidMatchData(String[] cols,
			ArrayList<String> colnames2, 
			ArrayList<HashMap<String, Object>> table1, 
			int index, 
			HashMap<String, String> matchData) {
		for (int i = 0; i < cols.length; i++){
			if (!colnames2.contains(cols[i]) && 
					cols[i].compareTo("_id") != 0){
				String key = cols[i];
				String value = (String) table1.get(index).get(key);

				//				if (value.contains("-"))
				//					value = removeIllegalChars(value);
				//				System.out.println("key = " + key + " value = " + value);

				if (value.equals("null")){
					for(int j = 0; j< cant_null.length; j++){
						if (key.compareTo(cant_null[j]) == 0){
							i = cols.length;
							j = cant_null.length;
							return null;
						}
					}
				}
				//to fix the cm^3/mol to g/cm^3 to from vliq to ldens

				colnames2.add(key);
				matchData.put(key, value);
				System.out.println(value);
			}
		}
		return matchData;
	}


	private String removeIllegalChars(String value) {
		String out = "";
		for(int i = 0; i < value.length(); i++){
			if(value.charAt(i) == '-'){
				out += '&';
			}else{
				out += value.charAt(i);
			}
		}
		return out;
	}


	private ArrayList<String> getColnames(
			ArrayList<HashMap<String, Object>> table1,
			ArrayList<HashMap<String, Object>> table2) {


		ArrayList<String> colnames = new ArrayList<String>();
		Set<String> colz1 = table1.get(0).keySet();
		Set<String> colz2 = table2.get(0).keySet();

		String[] cols1 = colz1.toArray(new String[0]);
		String[] cols2 = colz2.toArray(new String[0]);

		//		System.out.println("cols1 length = "+cols1.length);
		//		System.out.println("cols2 length = "+cols2.length);
		for (int i = 0; i < cols1.length; i++){
			if (!colnames.contains(cols1[i]) && 
					cols1[i].compareTo("_id") != 0){
				colnames.add(cols1[i]);
			}
		}
		for (int i = 0; i < cols2.length; i++){
			if (!colnames.contains(cols2[i]) && 
					cols2[i].compareTo("_id") != 0){
				colnames.add(cols2[i]);
			}
		}

		return colnames;
	}

	private ArrayList<HashMap<String, Object>> getTable(String t_name) {
		QueryDb qdb = new QueryDb(t_name);
		try {
			return qdb.fetchEverything(t_name);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
