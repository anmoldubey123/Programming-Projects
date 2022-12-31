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
        Pixel[][] pixels = this.getPixels();
        for (Pixel[] rowArray : pixels)
        {
            for (Pixel pixelObj: rowArray)
            {
                pixelObj.setBlue(0);
            }
        }    }

    /** remove everything BUT blue tint from a picture */
    public void keepOnlyBlue()
    {
        Pixel[][] pixels = this.getPixels();
        Pixel pixel = null;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                pixel = pixels[row][col];
                pixel.setRed(0);
                pixel.setGreen(0);
            }
        }
    }

    /** invert a picture's colors */
    public void negate()
    {
        Pixel[][] pixels = this.getPixels();
        Pixel pixel = null;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                pixel = pixels[row][col];
                pixel.setRed(255-pixel.getRed());
                pixel.setGreen(255-pixel.getGreen());
                pixel.setBlue(255-pixel.getBlue());
            }
        }
    }

    /** simulate the over-exposure of a picture in film processing */
    public void solarize(int threshold)
    {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {
                if (pixels[row][col].getRed() < threshold)
                    pixels[row][col].setRed(255 - pixels[row][col].getRed());
                if (pixels[row][col].getGreen() < threshold)
                    pixels[row][col].setGreen(255 - pixels[row][col].getGreen());
                if (pixels[row][col].getBlue() < threshold)
                    pixels[row][col].setBlue(255 - pixels[row][col].getBlue());
            }
        }
    }

    /** convert an image to grayscale */
    public void grayscale()
    {
        Pixel[][] pixels = this.getPixels();
        Pixel pixel = null;
        int total = 0;
        int average = 0;
        for (int row = 0; row < pixels.length; row++)
        {
            for (int col = 0; col < pixels[0].length; col++)
            {
                total = 0;
                pixel = pixels[row][col];
                total = total + pixel.getRed();
                total = total + pixel.getGreen();
                total = total + pixel.getBlue();
                average = total / 3;
                pixel.setColor(new Color(average, average, average));
            }
        }
    }

    /** change the tint of the picture by the supplied coefficients */
    public void tint(double red, double blue, double green)
    {
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < pixels[row].length; col++) {

                if ((int) (pixels[row][col].getRed() * red) > 255)
                    pixels[row][col].setRed(255);
                else
                    pixels[row][col].setRed((int) (pixels[row][col].getRed() * red));

                if ((int) (pixels[row][col].getGreen() * green) > 255)
                    pixels[row][col].setGreen(255);
                else
                    pixels[row][col].setGreen((int) (pixels[row][col].getGreen() * green));

                if ((int) (pixels[row][col].getBlue() * blue) > 255)
                    pixels[row][col].setBlue(255);
                else
                    pixels[row][col].setBlue((int) (pixels[row][col].getBlue() * blue));
            }
        }
    }

    /** reduces the number of colors in an image to create a "graphic poster" effect */
    public void posterize(int span)
    {
        int red = 0;
        int blue = 0;
        int green = 0;

        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {

                red = (pixels[i][j].getRed() / span) * span;
                blue = (pixels[i][j].getBlue() / span) * span;
                green = (pixels[i][j].getGreen() / span) * span;
                pixels[i][j].setColor(red, green, blue);
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
        Pixel[][] pixels = this.getPixels();
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        int width = pixels[0].length;
        for (int row = 0; row < pixels.length; row++) {
            for (int col = 0; col < width / 2; col++) {
                leftPixel = pixels[row][col];
                rightPixel = pixels[row][width - col - 1];
                leftPixel.setColor(rightPixel.getColor());
            }
        }
    }

    /** mirror about a horizontal midline, top to bottom */
    public void mirrorHorizontal()
    {
        Pixel[][] pixels = this.getPixels();
        Pixel topPixel = null;
        Pixel botPixel = null;
        int height = pixels.length;
        for (int row = 0; row < height / 2; row++) {
            for (int col = 0; col < pixels[0].length; col++) {
                topPixel = pixels[row][col];
                botPixel = pixels[height - row - 1][col];
                botPixel.setColor(topPixel.getColor());
            }
        }
    }

    /** flip an image upside down about its bottom edge */
    public void verticalFlip()
    {
        Pixel upPixel = null;
        Pixel downPixel = null;

        int length = pixels.length;

        for (int r = 0; r < pixels.length / 2; r++) {
            for (int c = 0; c < pixels[r].length; c++) {
                upPixel = pixels[r][c];
                downPixel = pixels[(length - 1) - r][c];
                Pixel tempPixel = new Pixel(downPixel.getRed(), downPixel.getGreen(), downPixel.getBlue());

                downPixel.setColor(upPixel.getColor());
                upPixel.setColor(tempPixel.getColor());
            }
        }
    }

    /** fix roof on greek temple */
    public void fixRoof()
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;

        int width = pixels[0].length;

        for (int r = 0; r < pixels.length; r++) {
            if (r == 150) {
                break;
            }

            for (int c = 0; c < width / 2; c++) {
                if (c == 300) {
                    break;
                }

                leftPixel = pixels[r][c];
                rightPixel = pixels[r][(width - 1) - c];

                rightPixel.setColor(leftPixel.getColor());
            }
        }
    }

    /** detect and mark edges in an image */
    public void edgeDetection(int dist)
    {
        Pixel leftPixel = null;
        Pixel rightPixel = null;
        Pixel[][] pixels = this.getPixels();
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
    public void chromakey(Picture other, Color color, int dist)
    {
        for (int i = 0; i < other.getPixels()[0].length && i < pixels[0].length; i++) {
            for (int j = 0; j < other.getPixels().length && j < pixels.length; j++) {
                if (pixels[j][i].colorDistance(color) <= dist) {
                    pixels[j][i] = other.getPixel(i, j);
                }
            }
        }
    }

    /** steganography encode (hide the message in msg in this picture) */
    public void encode(Picture msg)
    {
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                Pixel x = pixels[i][j];
                if (x.getRed() % 2 > 0) {
                    x.setRed(x.getRed() - 1);
                }
            }

        }
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                Pixel x = msg.pixels[i][j];
                if (x.colorDistance(Color.BLACK) <= 10) {
                    Pixel y = pixels[i][j];
                    if (y.getRed() % 2 == 0) {
                        y.setRed(y.getRed() + 1);
                    }

                }
            }
        }
    }

    /** steganography decode (return a new Picture containing the message hidden in this picture) */
    public Picture decode()
    {
        Picture picture = new Picture(pixels.length, pixels[0].length);
        for (int i = 0; i < pixels.length; i++) {
            for (int j = 0; j < pixels[0].length; j++) {
                Pixel x = pixels[i][j];
                if (x.getRed() % 2 > 0) {
                    Pixel y = picture.pixels[i][j];
                    y.setColor(Color.BLACK);

                }
            }
        }
        return picture;
    }

    /** perform a simple blur using the colors of neighboring pixels */
    public Picture simpleBlur()
    {
        Picture picture = new Picture(pixels.length, pixels[0].length);
        Pixel[][] s = pixels;
        for (int i = 1; i < pixels[0].length - 1; i++) {
            for (int j = 1; j < pixels.length - 1; j++) {
                int r = (pixels[j][i].getRed() + pixels[j + 1][i].getRed() + pixels[j][i + 1].getRed()
                        + pixels[j - 1][i].getRed() + pixels[j][i - 1].getRed()) / 5;
                int g = (pixels[j][i].getGreen() + pixels[j + 1][i].getGreen() + pixels[j][i + 1].getGreen()
                        + pixels[j - 1][i].getGreen() + pixels[j][i - 1].getGreen()) / 5;
                int b = (pixels[j][i].getBlue() + pixels[j + 1][i].getBlue() + pixels[j][i + 1].getBlue()
                        + pixels[j - 1][i].getBlue() + pixels[j][i - 1].getBlue()) / 5;
                s[j][i].setColor(r, g, b);
                s[j + 1][i].setColor(r, g, b);
                s[j][i + 1].setColor(r, g, b);
                s[j - 1][i].setColor(r, g, b);
                s[j][i - 1].setColor(r, g, b);
                picture.pixels = s;
            }
        }
        return picture;
    }

    /** perform a blur using the colors of pixels within radius of current pixel */
    public Picture blur(int radius)
    {
        Picture picture = new Picture(pixels.length, pixels[0].length);
        Pixel[][] s = pixels;
        for (int i = radius; i < pixels[0].length - radius; i++) {
            for (int j = radius; j < pixels.length - radius; j++) {
                int r = 0, g = 0, b = 0;
                for (int k = -1 * radius; k < radius; k++) {
                    for (int l = -1 * radius; l < radius; l++) {
                        r += s[j + k][i + l].getRed();
                        b += s[j + k][i + l].getBlue();
                        g += s[j + k][i + l].getGreen();
                    }
                }
                r /= (2 * radius) * (2 * radius);
                b /= (2 * radius) * (2 * radius);
                g /= (2 * radius) * (2 * radius);
                for (int k = -1 * radius; k < radius; k++) {
                    for (int l = -1 * radius; l < radius; l++) {
                        s[j + k][i + l].setColor(r, g, b);
                    }
                }

                picture.pixels = s;
            }
        }
        return picture;
    }
}