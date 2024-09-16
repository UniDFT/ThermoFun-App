package com.drawingboardapps.vleserverJUnit;

import static org.junit.Assert.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.codehaus.jettison.json.JSONException;
import org.junit.Test;

import com.drawingboardapps.vleserver.*;
public class TestData {

	private String var;
	private String hold;
	private double holdval;
	int calcnum = 6;
	// changes certain values in input.txt 
	// runs the fortran program
	// parses output and saves value to .csv to be displayed as a graph later. 
	@Test
	public void test() {
		try {
			//			testVLE( from, to);
			double from = 100.0, to = 500.0;
			var = "T"; hold = "P";
			testVLE(from,to);
			testVLData(from,to);

			var = "P"; hold = "T";
			from = 1; to = 250;
			testDewT(from,to);

			var = "T"; hold = "P";
			from = 100; to = 500;
			testDewP(from,to);

			var = "T"; hold = "P";
			from = 100; to = 500;
			testBublP(from,to);

			var = "P"; hold = "T";
			from = 1; to = 250;
			testBublT(from,to);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ArrayList<Component> createComponentList(double t) throws SQLException, JSONException{
		ArrayList<Component> components = new ArrayList<Component>();
		QueryDb qdb = new QueryDb("chemdata");
		Component propane = new Component("74-98-6 " , 0.5, t);
		Component methane = new Component("74-82-8 ", 0.5, t);
		components.add(propane);
		components.add(methane);
		return components;
	}
	private void testVLE(double from, double to) throws SQLException, JSONException {
		FileWrite fw = new FileWrite();
		String calc = "VLE";
		calcnum = 2;
		for(double p = 1; p < to; p++){
			System.out.println(p/to + "%");
			for(double t = from; t <= to; t++){
				fw.toFortran(calcnum, t, holdval, createComponentList(t));
				String os = System.getProperty("os.name");
				RunExternalProg.goFortran(os);
				ArrayList<Object> data = fw.FortranRead("OUTPUT");
				WriteCSVs wc = new WriteCSVs();
				try {
					//						System.out.println("Writing to CSV...");
					holdval = p;
					wc.writeTvsPcentVapor(calc,var,hold,holdval,t,from,to,data);
					wc.writeTvsLdens(calc,var,hold,holdval,t,from,to, data);
					wc.writeVLData(calc,var,hold,holdval,t,from,to, data);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	private void testDewT(double from, double to) throws SQLException, JSONException {
		FileWrite fw = new FileWrite();
		String calc = "DEWT";
		calcnum = 3;
		for(double p = from; p < to; p++){
			System.out.println(p/to + "%");
			fw.toFortran(calcnum, (double) -1, holdval, createComponentList(-1));
			String os = System.getProperty("os.name");
			RunExternalProg.goFortran(os);
			ArrayList<Object> data = fw.FortranRead("OUTPUT");
			WriteCSVs wc = new WriteCSVs();
			try {
				//						System.out.println("Writing to CSV...");
				holdval = p;
				wc.writePvsLvol(calc,var,hold,holdval,p,from,to,data);
				wc.writePvsLdens(calc,var,hold,holdval,p,from,to, data);
				wc.writePvsT(calc,var,hold,holdval,p,from,to, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void testDewP(double from, double to) throws SQLException, JSONException {
		FileWrite fw = new FileWrite();
		String calc = "DEWP";
		calcnum = 6;
		for(double t = from; t < to; t++){
			System.out.println(t/to + "%");
			fw.toFortran(calcnum, (double) t, (double) -1, createComponentList(t));
			String os = System.getProperty("os.name");
			RunExternalProg.goFortran(os);
			ArrayList<Object> data = fw.FortranRead("OUTPUT");
			WriteCSVs wc = new WriteCSVs();
			try {
				//						System.out.println("Writing to CSV...");
				holdval = t;
				wc.writeTvsLdens(calc,var,hold,holdval,t,from,to, data);
				wc.writeTvsP(calc,var,hold,holdval,t,from,to, data);
				wc.writeTvsVvol(calc,var,hold,holdval,t,from,to, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void testBublP(double from, double to) throws SQLException, JSONException {
		FileWrite fw = new FileWrite();
		String calc = "BUBLP";
		calcnum = 5;
		for(double t = from; t < to; t++){
			System.out.println(t/to + "%");
			fw.toFortran(calcnum, (double) t, (double) -1, createComponentList(t));
			String os = System.getProperty("os.name");
			RunExternalProg.goFortran(os);
			ArrayList<Object> data = fw.FortranRead("OUTPUT");
			WriteCSVs wc = new WriteCSVs();
			try {
				//						System.out.println("Writing to CSV...");
				holdval = t;
				wc.writeTvsLdens(calc,var,hold,holdval,t,from,to, data);
				wc.writeTvsP(calc,var,hold,holdval,t,from,to, data);
				wc.writeTvsVvol(calc,var,hold,holdval,t,from,to, data);
				wc.writeTvsZa(calc,var,hold,holdval,t,from,to, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void testBublT(double from, double to) throws SQLException, JSONException {
		FileWrite fw = new FileWrite();
		String calc = "BUBLT";
		calcnum = 4;
		for(double p = from; p < to; p++){
			System.out.println(p/to + "%");
			fw.toFortran(calcnum, (double) -1, (double) p, createComponentList(-1));
			String os = System.getProperty("os.name");
			RunExternalProg.goFortran(os);
			ArrayList<Object> data = fw.FortranRead("OUTPUT");
			WriteCSVs wc = new WriteCSVs();
			try {
				//						System.out.println("Writing to CSV...");
				holdval = p;
				wc.writePvsLdens(calc,var,hold,holdval,p,from,to, data);
				wc.writePvsT(calc,var,hold,holdval,p,from,to, data);
				wc.writePvsVvol(calc,var,hold,holdval,p,from,to, data);
				wc.writeTvsZa(calc,var,hold,holdval,p,from,to, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	private void testVLData(double from, double to) throws SQLException, JSONException {
		FileWrite fw = new FileWrite();
		String calc = "VLData";
		calcnum = 2;
		for(double t = from; t < to; t++){
			System.out.println(t/to + "%");
			fw.toFortran(calcnum, (double) t, (double) 20, createComponentList(-1));
			String os = System.getProperty("os.name");
			RunExternalProg.goFortran(os);
			ArrayList<Object> data = fw.FortranRead("OUTPUT");
			WriteCSVs wc = new WriteCSVs();
			try {
				//						System.out.println("Writing to CSV...");
				holdval = 10;
				wc.writeVLData(calc,var,hold,holdval,t,from,to, data);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}






