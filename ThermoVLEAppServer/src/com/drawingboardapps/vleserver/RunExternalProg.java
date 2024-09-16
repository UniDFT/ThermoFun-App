package com.drawingboardapps.vleserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class RunExternalProg {
	static Runtime run;
	private static String userHome;
	static String TAG = "RunExternalProgram";
	static String path = DirData.thermodir;
	
	public static void goFortran(String os) {

		run = Runtime.getRuntime();
		userHome = System.getProperty( "user.home" );

		if(os.startsWith("Windows")){
			runExe();
		}else if (os.startsWith("Mac")){
			runMacExec();
		}else if (os.startsWith("Linux")){
			runLinuxExec();
		}

	}



	private static void runLinuxExec() {
		try{
//			String path = FileWrite.getPath();
			Process pp=run.exec(path+"ThermoVLEFORTRAN");

			BufferedReader in =new BufferedReader(new InputStreamReader(pp.getErrorStream()));
			String line;
			while ((line = in.readLine()) != null) {
//				System.out.println(TAG+" "+line);
			}
			int exitVal = pp.waitFor();
//			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println(e.getMessage());
		}
	}



	private static void runMacExec() {
		try{
//			String path = FileWrite.getPath();
//			path = "/Users/Tricknology/Desktop/FlashDrive/ProgrammingStuff/Thermo/ThermoInOut/";
	
			Process pp=run.exec(path+"ThermoVLEFORTRAN");

			BufferedReader in =new BufferedReader(new InputStreamReader(pp.getErrorStream()));
			String line;
			while ((line = in.readLine()) != null) {
//				System.out.println(TAG+" "+line);
			}
			int exitVal = pp.waitFor();
//			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println(e.getMessage());
		}
	}


	private static void runExe() {
		try {

			Process pp=run.exec("cubic4.exe");
			BufferedReader in =new BufferedReader(new InputStreamReader(pp.getErrorStream()));
			String line;
			while ((line = in.readLine()) != null) {
//				System.out.println(line);
			}
			int exitVal = pp.waitFor();
//			System.out.println("Process exitValue: " + exitVal);
		} catch (Exception e) {
			e.printStackTrace();
//			System.out.println(e.getMessage());
		}

	}
}

