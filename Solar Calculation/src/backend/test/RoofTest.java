package backend.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import backend.calculation.Roof;

public class RoofTest {

Roof RoofNorth;
Roof RoofWest;
	
	@Before
	public void setUp(){
		
		RoofNorth = new Roof("North Roof", 10, 5, 30, "n", 1, 1);
		RoofWest = new Roof("West Roof", 15, 5, 30, "w", 0, 0);
		
	}
	
	
}
