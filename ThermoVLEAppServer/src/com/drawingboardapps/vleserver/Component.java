package com.drawingboardapps.vleserver;
import org.apache.commons.math3.analysis.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.math3.analysis.function.Pow;
import org.apache.commons.math3.analysis.function.Power;
import org.codehaus.jettison.json.JSONException;

public class Component {
	double mw;
	double w;
	double tn;
	double _id;
	double zc;
	double tc;
	double pc;
	double vc;
	double lden;
	double tden;
	double za;
	double exp, base;
	String name;
	double T = -1;
	double P = -1;
	String formula;
	double pcent;
	//values
	String casNo;
	public String getFormula() {
		return formula;
	}
	public void setFormula(String formula) {
		this.formula = formula;
	}
	public String getCasNo() {
		return casNo;
	}
	public void setCasNo(String casNo) {
		this.casNo = casNo;
	}
	public String getFeed() {
		return feed;
	}
	public void setFeed(String feed) {
		this.feed = feed;
	}
	public String getVapor() {
		return vapor;
	}
	public void setVapor(String vapor) {
		this.vapor = vapor;
	}
	public String getLiquid() {
		return liquid;
	}
	public void setLiquid(String liquid) {
		this.liquid = liquid;
	}
	public String getC() {
		return c;
	}
	public void setC(String c) {
		this.c = c;
	}
	String feed;
	String vapor;
	String liquid;
	String c;
	//rable firstperrys
	
	

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

	HashMap<String,Double> values;


	public Component(String _CASno,double p_cent, double _T) {
		this.casNo = _CASno;
		this.T = _T;
		this.pcent = p_cent;
		fillComponent();
	}



	private void fillComponent() {
		QueryDb qdb = new QueryDb("chemdata");
		try {
			ArrayList<HashMap<String, Object>> chemdetails = qdb.getChemByCAS(casNo, "chemdata");
			HashMap<String, Object> details = chemdetails.get(0);
			mw =  Double.parseDouble(details.get(KEY_MWT)+"");
			name = (String) details.get(KEY_NAME);
			formula = details.get(KEY_FORMULA).toString();
			w = Double.parseDouble(details.get(KEY_OMEGA)+"");
			tn = Double.parseDouble(details.get(KEY_TBP)+"");
			tc = Double.parseDouble(details.get(KEY_TC)+"");
			pc = Double.parseDouble(details.get(KEY_PC)+"");
			vc = Double.parseDouble(details.get(KEY_VC)+"");
			lden = Double.parseDouble(details.get(KEY_LDEN)+"");
			tden = Double.parseDouble(details.get(KEY_TDEN)+"");
			casNo = details.get(KEY_CAS).toString();
			za = calculateZA();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public double getPcent() {
		return pcent;
	}

	public void setPcent(double pcent) {
		this.pcent = pcent;
	}

	public double getT() {
		return T;
	}
	public void setT(double T) {
		this.T = T;
	}
	public double getLden() {
		return lden;
	}
	public void setLden(double lden) {
		this.lden = lden;
	}
	public double getTden() {
		return tden;
	}
	public void setTden(double tden) {
		this.tden = tden;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getMw() {
		return mw;
	}
	public void setMw(Double mw) {
		this.mw = mw;
	}
	public Double getW() {
		return w;
	}
	public void setW(Double w) {
		this.w = w;
	}
	public Double getTn() {
		return tn;
	}
	public void setTn(Double tn) {
		this.tn = tn;
	}
	public Double get_id() {
		return _id;
	}
	public void set_id(Double _id) {
		this._id = _id;
	}
	public Double getZc() {
		return zc;
	}
	public void setZc(Double zc) {
		this.zc = zc;
	}
	public Double getTc() {
		return tc;
	}
	public void setTc(Double tc) {
		this.tc = tc;
	}
	public Double getPc() {
		return pc;
	}
	public void setPc(Double pc) {
		this.pc = pc;
	}
	public Double getVc() {
		return vc;
	}
	public void setVc(Double vc) {
		this.vc = vc;
	}
	public Double getZa() {
		return za;
	}
	public void setZa(Double za) {
		this.za = za;
	}

	public HashMap<String,Double> makeValues(){
		HashMap<String,Double> values = new HashMap<String,Double>();
		values.put(KEY_MWT,mw);
		values.put(KEY_OMEGA,w);
		values.put(KEY_TBP,tn);
		values.put("id",_id);
		values.put(KEY_ZC,zc);
		values.put(KEY_TC,tc);
		values.put(KEY_PC,pc);
		values.put(KEY_VC,vc);
		values.put(KEY_LDEN,lden);
		values.put(KEY_TDEN,tden);
		values.put(KEY_ZA,za);
		values.put("pcent",pcent);
		this.values = values;
		return values;
	}

	public void sortData(ArrayList<HashMap<String, Double>> stuff) {
		HashMap<String, Double> meat = stuff.get(0);
		this.mw = meat.get("mw");
		this.w = meat.get("w");
		this.tn = meat.get("tn");
		this._id = meat.get("_id");
		this.zc = meat.get("zc");
		this.tc = meat.get("tc");
		this.pc = meat.get("pc");
		this.vc = meat.get("vc");
		this.vc = meat.get("pcent");
		this.za = calculateZA();
		makeValues();
	}
	private double calculateZA() {
//		//get ZA using function in book and return it
//		double n = (2.0/7.0);
////		System.out.println("n = "+n+"");
//		double l = T/tc;
////		System.out.println("l = "+l+"");
//		double q = Math.abs(1-l);
////		System.out.println("q = "+q+"");
//		double a = Math.exp(n*Math.log(q));
////		System.out.println("a = "+a+"");
//		double m = (tden/tc);
////		System.out.println("m = "+m+"");
//		double r = Math.abs(1-m);
////		System.out.println("r = "+r+"");
//		double b =  Math.exp(n*Math.log(r));
////		System.out.println("b = "+b+"");
//		double zacalc = a-b;
////		System.out.println(zacalc+"");
//		return zacalc;
		return 0.29056*0.08775*w;
	}



}
