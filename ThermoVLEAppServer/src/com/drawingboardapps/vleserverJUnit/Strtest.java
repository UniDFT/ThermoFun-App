package com.drawingboardapps.vleserverJUnit;

import static org.junit.Assert.*;

import org.junit.Test;

public class Strtest {

	@Test
	public void test() {
		String str = "bass";
		String num = "1";
		Object stri = str;
		Object numb = num;
		
		if(stri instanceof Integer)
			System.out.println("stri is integer");
		else if (stri instanceof String)
			System.out.println("stri is String");
		else fail("not string or int");
		
		if(numb instanceof Integer)
			System.out.println("numb is integer");
		else if (numb instanceof String)
			System.out.println("numb is String");
		else fail("not string or int");
	}

}
