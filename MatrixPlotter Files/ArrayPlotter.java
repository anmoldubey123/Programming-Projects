import java.util.*;
/**
 * ArrayPlotter.java  10/18/14
 *
 * @author - Robert Glen Martin
 * @author - School for the Talented and Gifted
 * @author - Dallas ISD
 *
 * The ArrayPlotter class provides methods for drawing in
 *    a grid by setting the boolean values of a 2D array.
 *
 *    Each drawing method must
 *    - take zero arguments,
 *    - have a void return type, and
 *    - have a name that conforms to the on...ButtonClick format.
 *      (This restriction allows the GridPlotterGUI to recognize methods,
 *       for which it automatically generates buttons.)
 *
 * License Information:
 *   This class is free software; you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation.
 *
 *   This class is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 */

public class ArrayPlotter
{
    /** The Array Plotter Graphical User Interface. */
    private ArrayPlotterGUI gui;

    /** The Color Array.  The element values indicate how to color a grid cell:
     *  - true: Color the cell with the Drawing Color.
     *  - false: Color the cell with the Background Color.
     */
    private boolean[][] colorArray;

    /** Constructs an Array Plotter */
    public ArrayPlotter()
    {
        gui = new ArrayPlotterGUI(this);
        colorArray = null;
    }

    /** Initialize this's Color Array to a new 2D array of boolean values
     *  with the given dimensions.
     *  @param rows the number of rows in the new array.
     *  @param cols the number of columns in the new array.
     *  Postcondition: All of the Color Array's elements have the value false.
     */
    public void initializeColorArray(int rows, int cols)
    {
        colorArray = new boolean[rows][cols];
    }

    // Drawing Methods
    /** Removes all objects from the grid. */
    public void clearGrid()
    {
        colorArray = new boolean[colorArray.length][colorArray[0].length];
        gui.update(colorArray);
    }

    /** This button calls the method above, which clears the grid. */
    public void onClearGridButtonClick()
    {
        clearGrid();
    }

    /** NOTE FROM MR. MCCOY ON HOW THIS THING WORKS. ------------------------------------------------------- 
     * 
     * Complete the methods below using for loops to fill the matrix in a specific orders/patterns.
     * 
     * Your instance variable is a 2-dimensional boolean array called colorArray.
     * To make a cell appear as filled, you have to set it as true and then tell the gui to update.
     * 
     * Example:         colorArray[3][7] = true;
     *                  gui.update(colorArray);
     * 
     * To ensure that you are coding wisely, this should be done efficiently using for loops.
     * Therefore, I am limiting the number of times you are allowed to inclde the gui.update()
     * command in each method.  See the method description for limit.
     * 
     * You can add your own methods also!  Buttons will be created automatically, as long as you follow
     * the correct naming convention.  The authors of this lab built that feature into the GUI!
     * 
     *-----------------------------------------------------------------------------------------------------*/

    /** Fills in all the cells of the grid using a row-major traversal. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onRowMajorFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            //I've provided some code below, but it is obviously wrong since it only fills 1 cell.
            //colorArray[5][5] = true;
            //gui.update(colorArray);
            for (int r = 0; r < colorArray.length; r++)
            {
                for (int c = 0; c < colorArray[r].length; c++)
                {
                    colorArray[r][c] = true;
                    gui.update(colorArray);
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in all the cells of the grid using a column-major traversal. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onColMajorFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for (int c = 0; c < colorArray.length; c++)
            {
                for (int r = 0; r < colorArray[c].length; r++)
                {
                    colorArray[r][c] = true;
                    gui.update(colorArray);
                }
            }


        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in all the cells of the grid bottom-up, going left-to-right across each row.  */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onReverseRowMajorFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.\
            for (int r = colorArray.length-1; r >= 0; r--)
            {
                for (int c = colorArray.length-1; c >= 0; c--)
                {
                    colorArray[r][c] = true;
                    gui.update(colorArray);
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in all the cells of the grid right-to-left, going up each column from the bottom. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onReverseColMajorFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for (int c = colorArray.length-1; c >=0; c--)
            {
                for (int r = colorArray.length-1; r>=0; r--)
                {
                    colorArray[r][c] = true;
                    gui.update(colorArray);
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells creating a diagonal from the upper-left corner down and to the right. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onMainDiagonalLineButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for (int r = 0; r< colorArray.length; r++)
            {
                for (int c = 0; c< colorArray.length; c++)
                {
                    if (r == c)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells of the grid on and below the main diagonal. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onMainTriangleFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for (int r = 0; r< colorArray.length; r++)
            {
                for (int c = 0; c<=r; c++)
                {
                    colorArray[r][c] = true;
                    gui.update(colorArray);
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells creating a diagonal from the upper-right corner down and to the left. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onOtherDiagonalLineButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for (int c = colorArray.length - 1; c >=0; c--)
            {
                for (int r = colorArray.length-1; r>=0;r--)
                {
                    if (c+r == 9)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells of the grid on and below the other diagonal. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onOtherTriangleFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for (int r = 0; r < colorArray.length; r++)
            {
                for (int c = 0; c <colorArray[0].length; c++)
                {
                    if (c>=colorArray[0].length-1-r)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells of the grid on the two diagonals */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 2  ******/
    public void onBothDiagonalsButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.

            for (int r = 0; r< colorArray.length; r++)
            {
                for (int c = 0; c< colorArray.length; c++)
                {
                    if (r == c)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                }
            }

            for (int c = colorArray.length - 1; c >=0; c--)
            {
                for (int r = colorArray.length-1; r>=0;r--)
                {
                    if (c+r == 9)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells of the grid in a serpentine fashion from the top left corner*/
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 2  ******/
    public void onSerpentineFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            boolean leftToRight = true;
            for (int r = 0; r<colorArray.length; r++)
            {

                if (leftToRight)
                {
                    for (int c = 0; c < colorArray.length; c++)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                    leftToRight = false;
                }
                else
                {
                    for (int c = colorArray.length-1; c>=0; c--)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                    leftToRight = true;
                }

            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells of the grid along the outside border starting in the upper left corner and going clockwise. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 4  ******/
    public void onBorderLinesButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            boolean upDown = false;
            boolean rightLeft = false;
            boolean downUp = false;
            boolean fin = false;
            for (int r = 0; r < colorArray.length; r++)
            {
                if (upDown == false)
                {
                    for (int c = 0; c< colorArray[r].length; c++)
                    {
                        colorArray[r][c] = true;
                        gui.update(colorArray);
                    }
                    upDown = true;
                }

                if (upDown)
                {
                    for (int c = colorArray.length-1; c>=0; c--)
                    {
                        if (rightLeft == false)
                        {
                            for (int r2 = 0; r2< colorArray.length; r2++)
                            {
                                colorArray[r2][c] = true;
                                gui.update(colorArray);
                            }
                            rightLeft = true;
                        }
                    }
                }

                if (rightLeft)
                {
                    for (int r3 = colorArray.length-1; r3>=0; r3--)
                    {
                        if (downUp == false)
                        {
                            for (int c = colorArray.length-1; c>=0; c--)
                            {
                                colorArray[r3][c] = true;
                                gui.update(colorArray);
                            }
                            downUp = true;
                        }
                    }
                }

                if (downUp)
                {
                    for (int c = 0; c< colorArray.length; c++)
                    {
                        if (fin == false)
                        {
                            for (int r4 = colorArray.length-1; r4>=0; r4--)
                            {
                                colorArray[r4][c] = true;
                                gui.update(colorArray);
                            }
                            fin = true;
                        }
                    }
                }

                if (fin == true)
                {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells of the grid in a spiral toward center, starting in the upper left corner and going clockwise. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 4  ******/
    public void onSpiralFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            int column = 0;
            int ct = 1;
            for (int r = 0; r< colorArray.length; r++)
            {
                for (int c = column; c< colorArray.length-column; c++)
                {
                    colorArray[r][c] = true;
                    gui.update(colorArray);
                }
                column++;

                for (int c = colorArray.length-ct; c< colorArray.length; c++)
                {
                    for (int r3 = r; r3< colorArray.length; r3++)
                    {
                        colorArray[r3][c] = true;
                        gui.update(colorArray);
                    }
                }
                ct++;

                for (int r4 = (colorArray.length-1)-r; r4< colorArray.length; r4++)
                {
                    for (int c = (colorArray.length-1)-column; c>=0; c--)
                    {
                        colorArray[r4][c] = true;
                        gui.update(colorArray);
                    }
                }

                for (int c = 0; c<=column; c++)
                {
                    for (int r5 = (colorArray.length-1)-r; r5>=0; r5--)
                    {
                        colorArray[r5][c] = true;
                        gui.update(colorArray);
                    }
                }

            }



        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills from top left to bottom right using upward diagonal lines */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 2  ******/
    public void onDiagonalFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for (int n = -colorArray.length; n <= colorArray.length; n++)
            {
                for (int i = 0; i < colorArray.length; i++)
                {
                    if((i-n)>=0 && (i-n< colorArray.length))
                    {
                        colorArray[i][i-n] = true;
                        gui.update(colorArray);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** Fills in the cells of the grid in a spiral toward center, starting in the upper left corner and going clockwise. */
    /******** MAXIMUM NUMBER OF gui.update(colorArray) STATEMENTS: 1  ******/
    public void onRandomFillButtonClick()
    {
        try {
            clearGrid();  //We should always start with a clean grid.
            //Your solution should be here in the try block.
            for(int a = 0; a < (colorArray.length)*(colorArray.length);)
            {
                Random rng = new Random();
                int b = rng.nextInt(colorArray.length);
                int c = rng.nextInt(colorArray.length);
                if(colorArray[b][c] == false)
                {
                    colorArray[b][c] = true;
                    a++;
                }
                gui.update(colorArray);
            }

        } catch (Exception e) {
            System.out.println("Something went wrong! " + e + "\n");
            clearGrid();  //
        }
    }

    /** main method that creates the grid plotter application. */
    public static void main(String[] args)
    {
        new ArrayPlotter();
    }
}
