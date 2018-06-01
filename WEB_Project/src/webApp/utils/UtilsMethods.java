package webApp.utils;

import java.io.File;
import java.util.Random;

import com.google.common.io.Files;

/**
 * Klasa sa pomocnim metodam
 * 
 * @author user
 *
 */
public final class UtilsMethods {
	/**
	 * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne
	 * moze da se konvertuje vraca se vrednost 0.
	 * 
	 * @param o
	 * @return
	 */
	public static int saftyConversionInt(Object o) {
		int id = 0;
		try {
			id = Integer.parseInt(o == null ? "0" : o.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return id;
	}

	public static long saftyConversionLong(Object o) {
		long id = 0;
		try {
			id = Long.parseLong(o == null ? "0" : o.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Pomocna metoda koja konvertuje object o u broj i u slucaju da object o ne
	 * moze da se konvertuje vraca se vrednost 0.
	 * 
	 * @param o
	 * @return
	 */
	public static double saftyConversionDouble(Object o) {
		double id = 0;
		try {
			id = Double.parseDouble(o == null ? "0" : o.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		return id;
	}

	/**
	 * Pomocna metoda koja sluzi da konvertuje object o u string i u slucaju da je o
	 * == null vraca se prazan string.
	 * 
	 * @param o
	 * @return
	 */
	public static String saftyConversionToStr(Object o) {
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

	static public String generateToken(int length) {
		String CharSet = "abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNOPQRSTUVWXYZ1234567890";
		String Token = "";
		for (int a = 1; a <= length; a++) {
			Token += CharSet.charAt(new Random().nextInt(CharSet.length()));
		}
		return Token;
	}

	static public String searchStatement(String search, String searchValue, int offset) {
		offset *= 10;
		return String.format("SELECT * FROM photo WHERE onSale = true AND %s = '%s' ORDER BY id LIMIT 10 OFFSET %d",
				search, searchValue, offset);
	}

	static public String filterStatement(String filter, int offset) {
		offset *= 10;
		String order;
		if (filter.charAt(filter.length() - 1) == 'R')
			order = "ASC";
		else
			order = "DESC";
		filter = filter.substring(0, filter.length() - 1);

		return String.format("SELECT * FROM photo WHERE onSale = true ORDER BY %s %s LIMIT 10 OFFSET %d", filter, order,
				offset);
	}

	static public String searchAndFilterStatement(String search, String searchValue, String filter, int offset) {
		offset *= 10;
		String order;
		if (filter.charAt(filter.length() - 1) == 'R')
			order = "ASC";
		else
			order = "DESC";
		filter = filter.substring(0, filter.length() - 1);

		return String.format("SELECT * FROM photo WHERE onSale = true AND %s = '%s' ORDER BY %s %s LIMIT 10 OFFSET %d",
				search, searchValue, filter, order, offset);
	}

	static public String getAllStatement(int offset) {
		offset *= 10;
		return String.format("SELECT * FROM photo WHERE onSale = true ORDER BY id LIMIT 10 OFFSET %d", offset);
	}

	static public String openStatement(int id) {
		return String.format("SELECT * FROM photo WHERE onSale = true AND id = %d", id);
	}

	static public boolean savePicture(byte[] photo, String name) {
		try {
			Files.write(photo, new File("D:/Photos/" + name+ ".png"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
