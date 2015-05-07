package model;

public class DateUtils {
	
	public static int t1900toEpoch(String timeStamp) {
		double time = Double.parseDouble(timeStamp);
		return (int) (time - 25567) * 86400;
	}
	

}
