package webApp.utils;
/**
 * Klasa sa pomocnim metodam
 * @author user
 *
 */
public final class UtilsMethods {
	/**
	 * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne moze da se konvertuje vraca se vrednost 0.
	 * @param o
	 * @return
	 */
	public static int saftyConversionInt(Object o){
		int id = 0;
		try{
			id = Integer.parseInt(o == null ? "0" : o.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne moze da se konvertuje vraca se vrednost 0.
	 * @param o
	 * @return
	 */
	public static double saftyConversionDouble(Object o){
		double id = 0;
		try{
			id = Double.parseDouble(o == null ? "0" : o.toString());
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
		return id;
	}
	
	/**
	 * Pomocna metoda koja sluzi da konvertuje object o u string i u slucaju da je o == null vraca se prazan string.
	 * @param o
	 * @return
	 */
	public static String saftyConversionToStr(Object o){
		return o == null ? "" : o.toString();
	}
	
	public static boolean saftyConversionToBoolean(Object o) {
		boolean b = false;
		try {
			b = Boolean.parseBoolean(o.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return b;
	}
}
