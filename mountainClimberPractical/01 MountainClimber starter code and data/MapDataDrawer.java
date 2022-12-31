import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class MapDataDrawer
{
	/** the 2D array containing the elevations */
	private int[][] grid;

	/** constructor, parses input from the file into grid */
	public MapDataDrawer(String fileName, int rows, int cols) throws IOException
	{
		//TODO
	}

	/** @return the min value in the entire grid */
	public int findMin()
	{
		//TODO

		return -1; //REPLACE
	}

	/** @return the max value in the entire grid */
	public int findMax()
	{
		//TODO

		return -1; //REPLACE
	}

	/**
	 * Draws the grid using the given Graphics object.
	 * Colors should be grayscale values 0-255, scaled based on min/max values in grid
	 */
	public void drawMap(Graphics g)
	{
		//TODO
	}

	/**
	 * Find a path from West-to-East starting at given row.
	 * Choose a forward step out of 3 possible forward locations, using greedy method described in assignment.
	 * @return the total change in elevation traveled from West-to-East
	 */
	public int drawLowestElevPath(Graphics g, int row)
	{
		//TODO

		return -1;
	}

	/** @return the index of the starting row for the lowest-elevation-change path in the entire grid. */
	public int indexOfLowestElevPath(Graphics g)
	{
		//TODO

		return 0;
	}

	public int getRows()
	{
		//TODO

		return -1;
	}

	public int getCols()
	{
		//TODO

		return -1;
	}
}