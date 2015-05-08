package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class DateUtils {
	
	public static int t1900toEpoch(String timeStamp) {
		double time = Double.parseDouble(timeStamp);
		return (int) (time - 25567) * 86400;
	}
	
	public static int parseDateSimpleFormat(String timeStamp, String formatpattern) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(formatpattern);
		return (int) format.parse(timeStamp).getTime()/1000;
	}
	

	

}
