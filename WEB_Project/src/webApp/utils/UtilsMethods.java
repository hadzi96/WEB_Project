package webApp.utils;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import javax.imageio.ImageIO;

import com.google.common.io.Files;

import net.coobird.thumbnailator.filters.Watermark;
import net.coobird.thumbnailator.geometry.Positions;

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

	static public synchronized boolean savePicture(byte[] photo, String name) {
		try {
			Files.write(photo, new File("D:/Photos/" + name + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	static public synchronized byte[] readFile(String filePath) {
		try {
			Path path = Paths.get(filePath);
			return java.nio.file.Files.readAllBytes(path);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	static public boolean checkCeneAndRez(String c, String r) {
		String[] rezolucije = r.split(";");
		String[] cene = c.split(";");

		if (rezolucije.length != cene.length) {
			System.out.println("cena and resolution length missmatch");
			return false;
		}

		for (String rez : rezolucije) {
			if (!regexMatcher("[0-9]*x[0-9]*$", rez)) {
				System.out.println("rezolution does not match regex");
				return false;
			}
		}
		for (String cen : cene) {
			try {
				Double.parseDouble(cen);
			} catch (Exception e) {
				System.out.println("cenea not double");
				return false;
			}
		}

		return true;
	}

	static public boolean regexMatcher(String patern, String value) {
		java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(patern);
		java.util.regex.Matcher matcher = pattern.matcher(value);

		return matcher.matches();
	}

	static public boolean saveTestPhoto(byte[] photo, String name) {
		try {
			Files.write(photo, new File("D:/Tests/" + name + ".png"));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	static public boolean checkResolutions(webApp.entities.File file) {
		try {
			BufferedImage img = ImageIO.read(new ByteArrayInputStream(file.getData()));
			String[] rezolutions = file.photo.rezolucije.split(";");
			for (String rez : rezolutions) {
				int width = Integer.parseInt(rez.split("x")[0]);
				int height = Integer.parseInt(rez.split("x")[1]);

				if (width > img.getWidth())
					return false;
				if (height > img.getHeight())
					return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	static public byte[] scaleImage(byte[] data, int width, int height) {
		try {
			BufferedImage bsrc = ImageIO.read(new ByteArrayInputStream(data));

			BufferedImage bdest = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bdest.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance((double) width / bsrc.getWidth(),
					(double) height / bsrc.getHeight());
			g.drawRenderedImage(bsrc, at);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(bdest, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();

			return imageInByte;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	static public byte[] watermark(byte[] data) {
		try {
			BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(data));
			BufferedImage watermarkImage = ImageIO.read(new ByteArrayInputStream(readFile("D:/watermark/watermark.png")));

			Watermark filter = new Watermark(Positions.CENTER, watermarkImage, 0.6f);
			BufferedImage watermarkedImage = filter.apply(originalImage);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(watermarkedImage, "png", baos);
			baos.flush();
			byte[] imageInByte = baos.toByteArray();
			baos.close();

			return imageInByte;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
