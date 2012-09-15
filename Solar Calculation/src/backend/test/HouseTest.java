package backend.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


import backend.calculation.House;

public class HouseTest {

	House House1;
	
	@Before
	public void setUp(){
		
		House1 = new House();
		House1.setLocation("Australia");
		
	}
	
	
	@Test
	public void getCountry(){
		assertTrue(House1.getCountry()=="Australia");
	}
	
	
	
}

