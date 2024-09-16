package com.drawingboardapps.vleserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;



public class Decoder{

	private static final String TAG = "Decoder()";
	private JSONArray outer_ary;
	private JSONArray inner_ary;
	private JSONObject request_object;
	private String user;
	private String pass;

	public JSONArray handleRequest(HttpServletRequest request){
		request_object = decodeRequest(request);
		return filterRequest(request_object);
	}

	private JSONObject decodeRequest(HttpServletRequest request){

		StringBuffer jb = new StringBuffer();
		String line = null;
		BufferedReader reader;
		try {
			reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb.append(line);

			String obj = jb.toString();
			System.out.println("decodeRequest: " + obj);

			return new JSONObject(obj);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null; // if there is an error

	} 
	
	private JSONArray filterRequest(JSONObject obj) {
		String req ="";
		JSONArray jary_out = new JSONArray();
		try {
			req = obj.getString("req");
		} catch (JSONException e) {
			Log.v(TAG, "JSONException "+e.toString());
		}
		if (req.equals("")){
			Serializer ser = new Serializer();
			ArrayList<Component> data_in  = new ArrayList<Component>();
			FileWrite fw = new FileWrite();
			try {// the meat is in here
				// takes serialized data and feeds it to FORTRAN then serializes it out for the responder
				data_in = ser.deserialize(obj);
				int calc = obj.getInt("calc");
				double T = obj.getInt("t");
				double P = obj.getInt("p");
				ArrayList<Object> data_out = fw.toFortran(calc,T,P,data_in);
				jary_out = ser.serialize(data_out);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}return jary_out;

	}
}
