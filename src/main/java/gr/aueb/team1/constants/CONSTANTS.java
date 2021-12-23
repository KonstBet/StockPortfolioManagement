package gr.aueb.team1.constants;

import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public final class CONSTANTS {
	
	// Regex Patterns
	public static final Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", Pattern.CASE_INSENSITIVE);
	public static final Pattern phonePattern = Pattern.compile("^69[0-9]{8}$");
	public static final Pattern namePattern = Pattern.compile("^[a-zA-Z]{1,20}$");
	public static final Pattern zipCodePattern = Pattern.compile("^[0-9]{5}$");
	public static final Pattern addNumPattern = Pattern.compile("^[0-9]{1,3}$");
	public static final Pattern streetPattern = Pattern.compile("^[a-zA-Z ]{1,20}$");

	
	// Values
	public static final Double fee = 0.1;
	
	// DateTime format
	public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
}
