package com.drawingboardapps.vleserver;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Log {

//	static String dPath = "/Users/Tricknology/Desktop/FlashDrive/ProgrammingStuff/Thermo/DBStuff/log.txt";
	static String dPath = DirData.logpath;
	

	public static boolean v(String TAG,String message){

		
		boolean complete = false;
		String Swrite = TAG+"  :  "+message;
		File f = new File(dPath);
		FileOutputStream fop = null;
		try{
			System.out.println(TAG+ "does file exist?");
			if(!f.exists()) {
				System.out.println(TAG+ "does file exist?");
				f.createNewFile();
				System.out.println(TAG+ "does file exist? = "+f.exists());
			}
			fop=new FileOutputStream(f,true);
			String time;
			if(Swrite!=null){
				DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = new Date();
				time = dateFormat.format(date);
				 String s = time + " | " + Swrite;
				fop.write(s.getBytes());
			}
			fop.flush();

			fop.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(TAG+" "+e.toString());
		}
		
		return fop != null;
	}

}


