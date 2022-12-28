import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A class that represents a picture made up of a rectangle of {@link Pixel}s
 */
public class Picture {

    /** The 2D array of pixels that comprise this picture */
	private Pixel[][] pixels;

    /**
     * Creates a Picture from an image file in the "images" directory
     * @param picture The name of the file to load
     */
    public Picture(String picture) {
        File file = new File("./images/"+picture);
        BufferedImage image;
        if (!file.exists()) throw new RuntimeException("No picture at the location "+file.getPath()+"!");
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        pixels = new Pixel[image.getHeight()][image.getWidth()];
        for (int y = 0; y<pixels.length; y++) {
            for (int x = 0; x<pixels[y].length; x++) {
                int rgb = image.getRGB(x, y);
                /*
                 * For the curious - BufferedImage saves an image's RGB info into a hexadecimal integer
                 * The below extracts the individual values using bit-shifting and bit-wise ANDing with all 1's
                 */
                pixels[y][x] = new Pixel((rgb>>16)&0xff, (rgb>>8)&0xff, rgb&0xff);
            }
        }
    }

    /**
     * Creates a solid-color Picture of a given color, width, and height
     * @param red The red value of the color
     * @param green The green value of the color
     * @param blue The blue value of the color
     * @param height The height of the Picture
     * @param width The width of the Picture
     */
    public Picture(int red, int green, int blue, int height, int width) {
        pixels = new Pixel[height][width];
        for (int y = 0; y<pixels.length; y++) {
            for (int x = 0; x<pixels[y].length; x++) {
                pixels[y][x] = new Pixel(red, green, blue);
            }
        }
    }

    /**
     * Creates a solid white Picture of a given width and height
     * @param color The {@link Color} of the Picture
     * @param height The height of the Picture
     * @param width The width of the Picture
     */
    public Picture(int height, int width) {
        this(Color.WHITE, height, width);
    }

    /**
     * Creates a solid-color Picture of a given color, width, and height
     * @param color The {@link Color} of the Picture
     * @param width The width of the Picture
     * @param height The height of the Picture
     */
    public Picture(Color color, int height, int width) {
        this(color.getRed(), color.getGreen(), color.getBlue(), height, width);
    }

    /**
     * Creates a Picture based off of an existing {@link Pixel} 2D array
     * @param pixels A rectangular 2D array of {@link Pixel}s. Must be at least 1x1
     */
    public Picture(Pixel[][] pixels) {
        if (pixels.length==0 || pixels[0].length==0) throw new RuntimeException("Can't have an empty image!");
        int width = pixels[0].length;
        for (int i = 0; i<pixels.length; i++) if (pixels[i].length!=width)
            throw new RuntimeException("Pictures must be rectangles. pixels[0].length!=pixels["+i+"].length!");
        this.pixels = new Pixel[pixels.length][width];
        for (int i = 0; i<pixels.length; i++) {
            for (int j = 0; j<pixels[i].length; j++) {
                this.pixels[i][j] = new Pixel(pixels[i][j].getColor());
            }
        }
    }

    /**
     * Creates a Picture based off of an existing Picture
     * @param picture The Picture to copy
     */
    public Picture(Picture picture) {
        this(picture.pixels);
    }

    /**
     * Gets the width of the Picture
     * @return The width of the Picture
     */
    public int getWidth() {
        return pixels[0].length;
    }

    /**
     * Gets the height of the Picture
     * @return The height of the Picture
     */
    public int getHeight() {
        return pixels.length;
    }

    /**
     * Gets the {@link Pixel} at a given coordinate
     * @param x The x location of the {@link Pixel}
     * @param y The y location of the {@link Pixel}
     * @return The {@link Pixel} at the given location
     */
    public Pixel getPixel(int x, int y) {
        if (x>=getWidth() || y>=getHeight() || x<0 || y<0) throw new RuntimeException("No pixel at ("+x+", "+y+")");
        return pixels[y][x];
    }

    /**
     * Sets the {@link Pixel} at a given coordinate
     * @param x The x location of the {@link Pixel}
     * @param y The y location of the {@link Pixel}
     * @param pixel The new {@link Pixel}
     */
    public void setPixel(int x, int y, Pixel pixel) {
        if (x>=getWidth() || y>=getHeight() || x<0 || y<0) throw new RuntimeException("No pixel at ("+x+", "+y+")");
        if (pixel==null) throw new NullPointerException("Pixel is null"); //guard is required because pixel's value isn't used in this method
        pixels[y][x] = pixel;
    }

    /**
     * Opens a {@link PictureViewer} to view this Picture
     * @return the {@link PictureViewer} viewing the Picture
     */
    public PictureViewer view() {
        return new PictureViewer(this);
    }

    /**
     * Return the 2D array of pixels that comprise this Picture
     * You can save a reference to a Picture's pixels with the following:
     * Pixel[][] pixels = pic.getPixels(); //for a Picture object called pic
     * @return 2D array of pixels that make up this Picture
     */
    public Pixel[][] getPixels() {
    		return pixels;
    }

    /********************************************************
     *************** STUDENT METHODS BELOW ******************
     ********************************************************/

    /** remove all blue tint from a picture */
    public void zeroBlue()
    {
    	for (Pixel[] row: pixels)
        {
            for (Pixel pixelVal : row)
            {
                pixelVal.setBlue(0);
            }
        }
    }

    /** remove everything BUT blue tint from a picture */
    public void keepOnlyBlue()
    {
    	for (Pixel[] pixelRow : pixels)
        {
            for(Pixel pixelVal : pixelRow)
            {
                pixelVal.setRed(0);
                pixelVal.setGreen(0);
            }
        }
    }

    /** invert a picture's colors */
    public void negate()
    {
        for (Pixel[] row: pixels)
        {
            for (Pixel pixelVal:row)
            {
                pixelVal.setRed(255-pixelVal.getRed());
                pixelVal.setGreen(255-pixelVal.getGreen());
                pixelVal.setBlue(255-pixelVal.getBlue());
            }
        }
    }

    /** simulate the over-exposure of a picture in film processing */
    public void solarize(int threshold)
    {
        for (Pixel[] row: pixels)
        {
            for (Pixel pixelVal:row)
            {
                if(pixelVal.getGreen()<threshold)
                {
                    pixelVal.setGreen(255-pixelVal.getGreen());
                }
                if(pixelVal.getRed()<threshold)
                {
                    pixelVal.setRed(255-pixelVal.getRed());
                }
                if(pixelVal.getBlue()<threshold)
                {
                    pixelVal.setBlue(255-pixelVal.getBlue());
                }
            }
        }

    }

    /** convert an image to grayscale */
    public void grayscale()
    {
    	for(Pixel[] row : pixels)
        {
            for (Pixel pixval:row)
            {
                pixval.setRed(((pixval.getRed()+ pixval.getBlue()+ pixval.getGreen())/3));
                pixval.setGreen(((pixval.getRed()+ pixval.getBlue()+ pixval.getGreen())/3));
                pixval.setBlue(((pixval.getRed()+ pixval.getBlue()+ pixval.getGreen())/3));

            }
        }
    }

	/** change the tint of the picture by the supplied coefficients */
	public void tint(double red, double blue, double green)
	{
        double rv;
        double rg;
        double rb;
		for(Pixel[] row: pixels)
        {
            for(Pixel val:row)
            {
                if(val.getRed()*red < 255)
                {
                     rv = val.getRed()*red;
                     val.setRed((int)rv);
                }
                else
                {
                    val.setRed(255);
                }
                if(val.getGreen()*green < 255)
                {
                    rg = val.getGreen()*green;
                    val.setGreen((int)rg);
                }
                else
                {
                    val.setGreen(255);
                }
                if(val.getBlue()*blue < 255)
                {
                    rb = val.getBlue()*blue;
                    val.setGreen((int)rb);
                }
                else
                {
                    val.setBlue(255);
                }
            }
        }
	}
	
	/** reduces the number of colors in an image to create a "graphic poster" effect */
	public void posterize(int span)
	{
        for(Pixel[] row: pixels)
        {
            for (Pixel val : row)
            {
                val.setRed(val.getRed()/span * span);
                val.setGreen(val.getGreen()/span * span);
                val.setBlue(val.getBlue()/span * span);
            }
        }
	}

    /** mirror an image about a vertical midline, left to right */
    public void mirrorVertical()
    {
		Pixel leftPixel  = null;
		Pixel rightPixel = null;

		int width = pixels[0].length;

		for (int r = 0; r < pixels.length; r++)
		{
			for (int c = 0; c < width / 2; c++)
			{
				leftPixel  = pixels[r][c];
				rightPixel = pixels[r][(width - 1) - c];

				rightPixel.setColor(leftPixel.getColor());
			}
		}
    }

    /** mirror about a vertical midline, right to left */
    public void mirrorRightToLeft()
    {
        Pixel leftPixel  = null;
        Pixel rightPixel = null;

        int width = pixels[0].length;

        for (int r = 0; r < width/2; r++)
        {
            for (int c = 0; c < pixels.length; c++)
            {
                leftPixel  = pixels[r][c];
                rightPixel = pixels[r][(width - 1) - c];

                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** mirror about a horizontal midline, top to bottom */
    public void mirrorHorizontal()
    {
        Pixel leftPixel  = null;
        Pixel rightPixel = null;

        int height = pixels.length;

        for (int r = 0; r < pixels.length; r++)
        {
            for (int c = 0; c < pixels[0].length; c++)
            {
                leftPixel  = pixels[r][c];
                rightPixel = pixels[(height - 1) - r][c];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** flip an image upside down about its bottom edge */
    public void verticalFlip()
    {
        Pixel leftPixel  = null;
        Pixel rightPixel = null;

        int width = pixels[0].length;

        for (int r = 0; r < pixels.length; r++)
        {
            for (int c = 0; c < width / 2; c++)
            {
                leftPixel  = pixels[r][c];
                rightPixel = pixels[r][(width - 1) - c];

                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** fix roof on greek temple */
    public void fixRoof()
    {
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;

        for (int row = 27; row < 97; row++) {
            for (int col = 13; col < mirrorPoint; col++) {

                leftPixel = pixels[row][col];
                rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** detect and mark edges in an image */
    public void edgeDetection(int dist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Color rightColor = null;

        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0;
                 col < pixels[0].length-1; col++)
            {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][col+1];
                rightColor = rightPixel.getColor();
                if (leftPixel.colorDistance(rightColor) >
                        dist)
                    leftPixel.setColor(Color.BLACK);
                else
                    leftPixel.setColor(Color.WHITE);
            }
        }
    }
	/** copy another picture's pixels into this picture, if a color is within dist of param Color */
	public void chromakey(Picture target, Color bgColor, int threshold, int targetX, int targetY)
	{
        Pixel currPixel = null;
        Pixel newPixel = null;

        // loop through the columns
        for (int srcX=0, trgX=targetX;
             srcX<getWidth() && trgX<target.getWidth();
             srcX++, trgX++) {

            // loop through the rows
            for (int srcY=0, trgY=targetY;
                 srcY<getHeight() && trgY<target.getHeight();
                 srcY++, trgY++) {

                // get the current pixel
                currPixel = this.getPixel(srcX,srcY);

                /* if the color at the current pixel is within threshold of
                 * the input color, then don't copy the pixel
                 */
                if (currPixel.colorDistance(bgColor)>threshold) {
                    target.getPixel(trgX,trgY).setColor(currPixel.getColor());
                }
            }
        }
    }
	/** steganography encode (hide the message in msg in this picture) */
	public void encode(Picture msg)
	{
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;

        for (int row = 27; row < 97; row++) {
            for (int col = 13; col < mirrorPoint; col++) {

                leftPixel = pixels[row][col];
                rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }
	}

	/** steganography decode (return a new Picture containing the message hidden in this picture) */
	public Picture decode()
	{
        int mirrorPoint = 276;
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int count = 0;

        for (int row = 27; row < 97; row++) {
            for (int col = 13; col < mirrorPoint; col++) {

                leftPixel = pixels[row][col];
                rightPixel = pixels[row][mirrorPoint - col + mirrorPoint];
                rightPixel.setColor(leftPixel.getColor());
            }
        }

		return null; //REPLACE
	}

	/** perform a simple blur using the colors of neighboring pixels */
	public Picture simpleBlur()
	{


		return null; //REPLACE
	}

	/** perform a blur using the colors of pixels within radius of current pixel */
	public Picture blur(int radius)
	{
		//TODO

		return null; //REPLACE
	}
	
	/**
	 * Simulate looking at an image through a pane of glass
	 * @param dist the "radius" of the neighboring pixels to use
	 * @return a new Picture with the glass filter applied
	 */
	public Picture glassFilter(int dist) 
	{
		//TODO
		
		return null; //REPLACE
	}
}
