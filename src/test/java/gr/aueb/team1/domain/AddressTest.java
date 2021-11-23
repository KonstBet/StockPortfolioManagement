package gr.aueb.team1.domain;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AddressTest {
	Address ad = new Address();
	
	@Test //Setters Getters Test
	public void setGetTest() {
		ad.setNumber("37");
		ad.setZipCode("17561");
		ad.setStreet("Alkionis");
		assertTrue(ad.getNumber() == "37" && ad.getZipCode() == "17561" && ad.getStreet() == "Alkionis");
	}
}
