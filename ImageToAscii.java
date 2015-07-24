
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
			System.out.println("# Usage");
			System.out.println("To use this ASCII converter you can either compile the sources using javac");
			System.out.println();
			System.out.println("	javac ImagetoAscii.java");
			System.out.println("	java ImageToAscii <IMAGE_NAME> [OPTIONS]");
			System.out.println();
			System.out.println("");
			System.out.println("or run the precompiled jar file");
			System.out.println();
			System.out.println("	java -jar ImagetoAscii.jar <IMAGE_NAME> [OPTIONS]");
			System.out.println();
			System.out.println("## OPTIONS");
			System.out.println("####pixel-detail");
			System.out.println("	-d detail_value");
			System.out.println("");
			System.out.println("This option changes the density of pixels displayed. The larger the value the fewer pixels are converted. e.g. a value of 1 will rastorize the full image while a value of 2 will rastorize half the image, skipping every other pixel The default value is 1");
			System.out.println("");
			System.out.println("####output-file");
			System.out.println("	-o output_file");
			System.out.println("");
			System.out.println("When this option is included followed by a name, the program will use the supplied name as the output file. The default value is IMAGE_NAME-ascii-version.txt");
			System.out.println("");
			System.out.println("####print-to-console");
			System.out.println("	-p");
			System.out.println("");
			System.out.println("When this option is supplied it will print to console as well as to an output file. The default value is false");
			System.out.println("");
			System.out.println("#Examples");
			System.out.println("");
			System.out.println();
			System.out.println("java ImageToAscii Artorias.png ");
			System.out.println();
			System.exit(0);
		}

		// Ascii character to pixel conversion array. Pixels will correspond to a certain character in this array
		char[] btw = "$@B%8&WM#*oahkbdpqwmZO0QLCJUYXzcvunxrjft/\\|()1{}[]?-_+~<>i!lI;:,\"^`'. ".toCharArray();
		// char[] btw = "@%&#0*|?+=-!:\". ".toCharArray();
		String filename = args[0];
		int pixelDetail = 1;
		boolean printToConsole = false;
		String outputFileName = filename.substring(0, filename.toCharArray().length - 4) + "-ascii-version.txt";

		for (int i = 1; i < args.length; i++) {
			if (args[i].matches("-(.*)p(.*)"))
				printToConsole = true;
			if (args[i].matches("-(.*)d(.*)o(.*)")) {
				pixelDetail = Integer.parseInt(args[i+1]);
				outputFileName = args[i+2];
			} else if (args[i].matches("-(.*)o(.*)d(.*)")) {
				pixelDetail = Integer.parseInt(args[i+2]);
				outputFileName = args[i+1];
			} else if (args[i].matches("-(.*)d(.*)"))
				pixelDetail = Integer.parseInt(args[i+1]);
			else if (args[i].matches("-(.*)o(.*)"))
				outputFileName = args[i+1];
		}

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
				writer.print(handleSinglePixel(bimage.getRGB(j, i), btw) + "" + handleSinglePixel(bimage.getRGB(j, i), btw));
				if (printToConsole)
					System.out.print(handleSinglePixel(bimage.getRGB(j, i), btw) + "" + handleSinglePixel(bimage.getRGB(j, i), btw));
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
      	return btw[(int)Math.floor(red*btw.length/256.0f)];
 	}
}