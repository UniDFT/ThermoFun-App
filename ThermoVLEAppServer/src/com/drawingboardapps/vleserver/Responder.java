package com.drawingboardapps.vleserver;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

public class Responder{
	
	public void sendResponse(HttpServletResponse response, JSONArray resp_ary) throws IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		out.println(resp_ary.toString());
		out.close(); 
	}

}
