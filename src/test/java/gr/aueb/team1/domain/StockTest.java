	package gr.aueb.team1.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StockTest {
	private Stock s;
	private LocalDateTime d;
	
	
	@Test
	public void testGettersAndSetters() {
		d = LocalDateTime.of(2021,12,31,0,0,0);
		s = new Stock("P200", "PIRAEUS", d, 10.00, 200.99, 1000.00, 10.00, 3000.00);
		s = new Stock();
		s.setId("P200");
		s.setCompanyName("PIRAEUS");
		s.setDate(d);
		s.setOpen(10.00);
		s.setClose(200.99);
		s.setHigh(1000.00);
		s.setLow(10.00);
		s.setVol(3000.00);
		
		boolean flag = false;
		if (s.getId().equals("P200") && s.getCompanyName().equals("PIRAEUS")
				&& s.getDate().equals(d) && s.getOpen() == 10.00
				&& s.getClose() == 200.99 && s.getHigh() == 1000.00 && s.getLow() == 10.00 && s.getVol() == 3000.00) {
			flag = true;
		}
			
		Assertions.assertTrue(flag);
	}
	
	@Test
	public void toStringTest() {
		d = LocalDateTime.of(2021,12,31,0,0,0);
		s = new Stock("P200", "PIRAEUS", d, 10.00, 200.99, 1000.00, 10.00, 3000.00);
		String st = s.toString();
		assertEquals("ID: " + s.getId() + 
				   "\nCompany Name: " + s.getCompanyName() + 
				   "\nDate: " + s.getDate().toString() +
				   "\nOpen: " + s.getOpen() + "€" +
				   "\nClose: " + s.getClose() + "€" +
				   "\nHigh: " + s.getHigh() + "€" +
				   "\nLow: " + s.getLow() + "€" +
				   "\nVol: " + s.getVol() + "€", st);
	}
}
