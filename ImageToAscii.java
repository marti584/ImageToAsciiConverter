
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.awt.image.PixelGrabber;
import java.awt.Graphics2D;
import java.io.File;
import java.io.PrintWriter;
import javax.imageio.ImageIO;
import javax.swing.GrayFilter;

public class ImageToAscii {
	public static void main(String[] args) {

		if (args.length == 0) {
			System.out.println("To use this ASCII converter supply the commands with\n");
			System.out.println("	java ImageToAscii <IMAGE_NAME> [[[int pixel_detail] bool print_to_console] String <OUTPUT_FILENAME>]\n");
			System.out.println("All options in [] are optional and have default values if not supplied\n");
			System.out.println("int [pixel_detail]: The higher the pixel_detail, the more pixels are skipped");
			System.out.println("e.g. a pixel_detail of default=1 will rastorize the entire image");
			System.out.println("whereas a pixel_detail of 2 will rastorize half the image,");
			System.out.println("skipping every other pixel\n");
			System.out.println("bool [print_to_console]: This is a boolean so it accepts default='true' or 'false' as values\n");
			System.out.println("String [OUTPUT_FILENAME]: Supply your own filename to save to. default='<IMAGE_NAME>-ascii-version.txt\n'");
			System.exit(0);
		}

		// Ascii character to pixel conversion array. Pixels will correspond to a certain character in this array
		// char[] btw = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ".toCharArray();
		char[] btw = "@%#*+=-:. ".toCharArray();
		String filename = args[0];
		int pixelDetail = 1;
		boolean printToConsole = true;
		String outputFileName = filename.substring(0, filename.toCharArray().length - 4) + "-ascii-version.txt";

		if (args.length >= 2 && !args[1].equals("0"))
			pixelDetail = Integer.parseInt(args[1]);
		if (args.length >=3 && (args[2].equals("true") || args[2].equals("false")))
			printToConsole = args[2].equals("true") ? true : false;
		if (args.length >=4)
			outputFileName = args[3];

		// Read the image into a buffered image
		BufferedImage colorImage = null;
		try {
			colorImage = ImageIO.read(new File(filename));
		} catch(Exception e) {
			e.printStackTrace();
			System.exit(1);
		}

		// Add a filter to a new image to make it gray scale
		ImageFilter filter = new GrayFilter(true, 50);
		ImageProducer producer = new FilteredImageSource(colorImage.getSource(), filter);
		Image image = Toolkit.getDefaultToolkit().createImage(producer);

		// Read the new image into a buffered image so we can use the pixel information
		BufferedImage bimage = null;
		try {
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);

    		// Draw the image on to the buffered image
		    Graphics2D bGr = bimage.createGraphics();
		    bGr.drawImage(image, 0, 0, null);
		    bGr.dispose();
		    // Prints the gray scale image for testing purposes
			// File outputfile = new File("saved.png");
			// ImageIO.write(bimage, "png", outputfile);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Grab the height and width of the full image
		int height = colorImage.getHeight();
		int width = colorImage.getWidth();

		// Use PrintWriter to write to our new file
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(outputFileName, "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Loop through the height and width incrementing by the pixelDetail
		for (int i = 0; i < height; i+=pixelDetail) {
			for (int j = 0; j < width; j+=pixelDetail) {
				// Write out the converted pixel to the file and optionally to the console
				writer.print(" " + handleSinglePixel(bimage.getRGB(j, i), btw));
				if (printToConsole)
					System.out.print(" " + handleSinglePixel(bimage.getRGB(j, i), btw));
			}
			if (printToConsole)
				System.out.println();
			writer.println();
		}

		// Close the file writer
		writer.close();
	}

	public static char handleSinglePixel(int pixel, char[] btw) {
		// So this information is useless in gray scale because each RGB value is the same
		// e.g red=128 green=128 blue=128
		// I like to keep it here for future reference however
		// The pixel information is laid out as an integer e.g. 1128128128
		// This is contingent upon an 8bit per pixel word size giving 256 possible values 0-255
		// Using & with 0xff cuts off any leading digits
   		int alpha = (pixel >> 24) & 0xff;
      	int red   = (pixel >> 16) & 0xff;
      	int green = (pixel >>  8) & 0xff;
      	int blue  = (pixel      ) & 0xff;

      	// If alpha is 0 then it's an empty space
      	if (alpha == 0 || alpha == 2) return ' ';

      	// Otherwise we'll return the character in the array that corresponds to the certain pixel
      	// The farther left, the total absence (black), the farther right the total presence (white)
      	return btw[(red*btw.length/256)%btw.length];
 	}
}