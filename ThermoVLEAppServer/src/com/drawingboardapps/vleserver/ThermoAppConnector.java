package com.drawingboardapps.vleserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

@WebServlet(name = "ThermoVLEAppServer",
urlPatterns = {"/"})
public class ThermoAppConnector extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private String TAG = "ThermoAppConnector()";
	public ThermoAppConnector(){
		super();
	}
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		System.out.println("in get");
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String filename = "/WEB-INF/thermo.html";
		ServletContext context = getServletContext();
		InputStream inps = context.getResourceAsStream(filename);

		if (inps != null){
			InputStreamReader inpsr = new InputStreamReader(inps);
			BufferedReader reader = new BufferedReader(inpsr);
			String text = "";
			while((text = reader.readLine())!=null){
				out.println(text);
			}
		}out.close();
	}

	@Override
	protected void doPost(final HttpServletRequest request, 
			final HttpServletResponse response){
		Thread postThread = new Thread(){
			public void run(){
//				checkNewDatabase();
				System.out.println("in post");
				Decoder d = new Decoder();
				Responder r = new Responder();
				JSONArray resp_ary = new JSONArray();
				resp_ary = d.handleRequest(request);
				System.out.println("The response --> " + resp_ary.toString());
				try {
					r.sendResponse(response, resp_ary);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		postThread.run();
	}

//	public boolean checkNewDatabase() {
//		
		//checks to see if the database has been updated
//		boolean complete = false;
//		String csvPath = "/Users/Tricknology/Desktop/Programming/Thermo/";
//		String dbPath = "/Users/Tricknology/Desktop/Programming/Thermo/";
//		String dbFilename = "chemdb.cdb";
//		String csvFilename = "chemdb.csv";
//		String recFilename = "modDate.txt";
//		File db = new File(dbPath+dbFilename); 
//		File csv = new File(csvPath+csvFilename);
//		File rec = new File(recFilename);
//		long record = 0;
//		if (rec.exists()){
//			//if there is a time record 
//			BufferedReader br;
//			try {
//				br = new BufferedReader(new FileReader(rec));
//				String line;
//				if ((line = br.readLine()) != null) {
//					record = Long.valueOf(line).longValue();
//				}
//			} catch (NumberFormatException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			record = 0;
//		}else{
//			//time record does not exist, create one:
//			try {
//				rec.createNewFile();
//				FileOutputStream fos = new FileOutputStream(rec);
//				String time = Calendar.getInstance().getTimeInMillis()+"";
//			    fos.write(time.getBytes());
//				fos.close();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				Log.v(TAG, "IOException "+e.toString());
//				
//			}
//		}
//		
//		if (rec.exists()){
//			long date = rec.lastModified();
//			
//			if (date > record) {
//				DecodeCSV dc = new DecodeCSV();
//				try {
//					String dbname = "chemdb.cdb";
//					String tablename = "chemdb";
//					if (!db.exists()){
//						CreateDb cdb = new CreateDb(dbname);
//						cdb.makeDb(tablename);
//						ArrayList<HashMap<String,String>> matrix = dc.toMatrix(csvPath, csvFilename);
//						QueryDb qdb = new QueryDb(tablename);
//						qdb.insertBlock(tablename, matrix);
//						return true;
//					}
//					
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					Log.v(TAG, "IOException" + e.toString());
//					return false;
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					Log.v(TAG, "SQLException" + e.toString());
//					return false;
//				}
//			}else Log.v(TAG, "Something Wrong creating file"); //investigate this if it comes up
//		}
//		return true;
//	}
}