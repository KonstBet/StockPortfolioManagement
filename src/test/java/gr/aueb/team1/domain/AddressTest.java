package gr.aueb.team1.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AddressTest {
	Address ad = new Address();
	
	@Test //Setters Getters Test
	void setGetTest() {
		ad.setNumber("37");
		ad.setZipCode("17561");
		ad.setStreet("Alkionis");
		assertTrue(ad.getNumber() == "37" && ad.getZipCode() == "17561" && ad.getStreet() == "Alkionis");
	}
	
	@Test 
	void toStringTest() {
		ad = new Address("Alkionis", "17561", "37");
		String s = ad.toString();
		assertEquals("Street: " + ad.getStreet()
		+ "\nNumber: " + ad.getNumber()
		+ "\nZipCode: " + ad.getZipCode(),s);
	}
}
