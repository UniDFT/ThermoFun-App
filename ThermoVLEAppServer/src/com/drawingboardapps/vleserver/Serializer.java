package com.drawingboardapps.vleserver;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Serializer {

	// object comes in as:
	// JSONObject { numcomp, calc, p,t, components = JSONArray(names) }
	// JSONArray names = {component1, component2...}

	String TAG = "Serializer()";
	private JSONObject obj;
	public static final String KEY_CALC = "calc";
	public static final String KEY_NUM_COMP = "ncomp";
	public static final String KEY_PCENT = "pcent";
	public static final String KEY_COMPNAMES = "compnames";

	public static final String KEY_FEED = "feed";
	public static final String KEY_VAPOR = "vapor";
	public static final String KEY_LIQUID = "liquid";
	public static final String KEY_LDENS = "ldens";
	public static final String KEY_LVOL = "lvol";
	public static final String KEY_VVOL = "vvol";
	public static final String KEY_VAP_FRAC = "vap_frac";

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
	public static final String KEY_LDEN = "Vliq";
	public static final String KEY_TDEN = "Tdens";
	public static final String KEY_FORMULA = "Formula";
	public static final String KEY_CAS = "CAS";
	public static final String KEY_TEMP = "temp";
	public static final String KEY_PRES = "pres";
	static final String KEY_MOLPCT = "molpct";


	public ArrayList<Component> deserialize(JSONObject jobj) throws JSONException {
		this.obj = jobj;
		ArrayList<Component> component_data = new ArrayList<Component>();
		HashMap<String,Object> data = new HashMap<String,Object>();
		data.put(KEY_NUM_COMP, obj.getDouble(KEY_NUM_COMP));
		data.put(KEY_CALC, obj.getDouble(KEY_CALC));
		data.put(KEY_P, obj.getDouble(KEY_P));
		data.put(KEY_T, obj.getDouble(KEY_T));
		JSONArray compnames = obj.getJSONArray(KEY_COMPNAMES);
		QueryDb qdb = new QueryDb("chemdata");
		for (int i = 0; i < obj.getDouble(KEY_NUM_COMP);i++){
			JSONObject comp = compnames.getJSONObject(i);
			JSONArray names = comp.names();
			String CASno = names.getString(0);
			Double num = comp.getDouble(CASno);
			Component c = new Component(CASno,num,obj.getDouble(KEY_T));
			//				ArrayList<HashMap<String, Object>> stuff = qdb.getChemByName(name);
			component_data.add(c);
		}
		return component_data;

	}

	public JSONArray serialize(ArrayList<Object> data_out) throws JSONException {
		JSONArray jary = new JSONArray();
		JSONObject obj = new JSONObject();
		Component component = null;
		int calc = 0;
		if(data_out.size() > 1){
			component = (Component)data_out.get(1);
			calc = Integer.parseInt(component.getC());
		}else if (data_out.size() == 1){
			calc = 7;
		}
		
		@SuppressWarnings("unchecked") // pos 0 is an object but should always be a hm<s,s>
		HashMap<String,String> properties = (HashMap<String,String>) data_out.get(0);
		switch (calc){

		case 1://dont use

			break;
		case 2://isot flash

			obj.put(KEY_LDENS, properties.get(KEY_LDENS));
			obj.put(KEY_LVOL, properties.get(KEY_LVOL));
			obj.put(KEY_VAP_FRAC, properties.get(KEY_VAP_FRAC));
			jary.put(obj);
			for (int i = 1; i < data_out.size(); i++){
				obj = new JSONObject();
				component = (Component)data_out.get(i);
				obj.put(KEY_CAS, component.getCasNo());
				obj.put(KEY_NAME, component.getName());
				obj.put(KEY_FEED, component.getFeed());
				obj.put(KEY_VAPOR, component.getVapor());
				obj.put(KEY_LIQUID, component.getLiquid());
				jary.put(obj);
			}
			break;
		case 3://DEW TEMP
			obj.put(KEY_LDENS, properties.get(KEY_LDENS));
			obj.put(KEY_LVOL, properties.get(KEY_LVOL));
			obj.put(KEY_TEMP, properties.get(KEY_TEMP));
			jary.put(obj);
			for (int i = 1; i < data_out.size(); i++){
				obj = new JSONObject();
				component = (Component)data_out.get(i);
				obj.put(KEY_CAS, component.getCasNo());
				obj.put(KEY_NAME, component.getName());
				obj.put(KEY_FEED, component.getFeed());
				obj.put(KEY_VAPOR, component.getVapor());
				obj.put(KEY_LIQUID, component.getLiquid());
				jary.put(obj);
			}
			break;
		case 4://Bubble Temp
			obj.put(KEY_LDENS, properties.get(KEY_LDENS));
			obj.put(KEY_VVOL, properties.get(KEY_VVOL));
			obj.put(KEY_TEMP, properties.get(KEY_TEMP));
			jary.put(obj);
			for (int i = 1; i < data_out.size(); i++){
				obj = new JSONObject();
				component = (Component)data_out.get(i);
				obj.put(KEY_CAS, component.getCasNo());
				obj.put(KEY_NAME, component.getName());
				obj.put(KEY_FEED, component.getFeed());
				obj.put(KEY_VAPOR, component.getVapor());
				obj.put(KEY_LIQUID, component.getLiquid());
				jary.put(obj);
			}
			break;
		case 5://BUBBLE PRESSURE
			obj.put(KEY_LDENS, properties.get(KEY_LDENS));
			obj.put(KEY_LVOL, properties.get(KEY_LVOL));
			obj.put(KEY_PRES, properties.get(KEY_PRES));
			jary.put(obj);
			for (int i = 1; i < data_out.size(); i++){
				obj = new JSONObject();
				component = (Component)data_out.get(i);
				obj.put(KEY_CAS, component.getCasNo());
				obj.put(KEY_NAME, component.getName());
				obj.put(KEY_FEED, component.getFeed());
				obj.put(KEY_VAPOR, component.getVapor());
				obj.put(KEY_LIQUID, component.getLiquid());
				jary.put(obj);
			}
			break;
		case 6://DEW PRESSURE
			obj.put(KEY_PRES, properties.get(KEY_PRES));
			obj.put(KEY_LDENS, properties.get(KEY_LDENS));
			obj.put(KEY_VVOL, properties.get(KEY_VVOL));
			jary.put(obj);
			for (int i = 1; i < data_out.size(); i++){
				obj = new JSONObject();
				component = (Component)data_out.get(i);
				obj.put(KEY_CAS, component.getCasNo());
				obj.put(KEY_NAME, component.getName());
				obj.put(KEY_FEED, component.getFeed());
				obj.put(KEY_VAPOR, component.getVapor());
				obj.put(KEY_LIQUID, component.getLiquid());
				jary.put(obj);
			}
			break;
		case 7:
			obj.put(KEY_LDENS, properties.get(KEY_LDENS));
			jary.put(obj);
			break;
		}
		return jary;

	}

}
