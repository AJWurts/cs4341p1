package Utilities;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

// This class provides the heuristic value for each position in the board
public class HeuristicStateTree extends StateTree {
    // Constructor for state tree
    public HeuristicStateTree(int r, int c, int w, int t, boolean p1, boolean p2, StateTree p) {
        super(r, c, w, t, p1, p2, p);
    }

    public boolean getPop1(){
        return pop1;
    }
    public boolean getPop2(){
        return pop2;
    }
    // This function sets the board to what the state tree currently is
    public void setBoard()
    {
        // Initialize the new matrix to represent the board with the correct number of rows and collumns
        int[][] b = new int [rows][columns]; // matrix representing the board (0 = empty, 1 = player1, 2 = player2)
        // Iterate through the rows and columns of the matrix, filling in the correct representation for each place
        for (int i=rows-1; i>=0; i--)
        {
            for (int j = 0; j < columns; j++)
            {
                b[i][j] = parent.getBoardMatrix()[i][j];
            }
        }
        this.boardMatrix = b;
    }

    // This function evaluates the utility value of each node
    public int eval(boolean e)
    {
        // Initialize variables
        // Set initial heuristic value to zero
        int Heuristic = 0;
        // Set player to zero initially, this will be changed to player one or two later
        int player = 0;
        // Initialize an array with four values, for each of the nodes around the current node
        // The elements are [diagonal up, right, diagonal down, down]
        int[] Count = new int[4];
        // Initialize an array with four values, for each of the nodes around the current node as above
        // Each element in the array will have a value equal to the number of open ends at the respective node
        int[] NumOfOpenEnd = new int[4];

        // Iterate through the rows and columns of the board to determine the utility value of each node
        for(int i=0; i< rows; i++)
        {
            for(int j=0; j<columns; j++)
            {
                // If this node is unoccupied
                if(this.getBoardMatrix()[i][j] == 0)
                {
                    // Denote that there is currently no player's piece in the space
                    player = 0;
                    // Set the Count element to zero for each of the four adjacent nodes
                    for(int x=0; x<4; x++)
                    {
                        Count[x] = 0;
                    }
                }
                // Else if the node is occupied by a player
                else
                {
                    // Set the player to be the player occupying the space
                    player = this.getBoardMatrix()[i][j];
                    // Iterate through the four adjacent nodes to see if they are connected to this node
                    for(int x=0; x<=winNumber; x++) {
                        //check if next is connected
                        // If the next node is occupied by the same player, add one connection to the Count for that element
                        if ((j + x < columns) && (this.getBoardMatrix()[i][j + x] == player)) {
                            Count[0]++;
                        }
                        // If the next node is the boundary, and if the previous node is open, add one to the NumOfOpenEnd
                        // for that element
                        else if (j + x == columns) {
                            //if the previous one is open
                            if (((j - 1) >= 0) && (this.getBoardMatrix()[i][j - 1] == 0)) {
                                NumOfOpenEnd[0]++;
                            }
                            break;
                        }
                        // If the next node is not occupied by the same player and is within the game space
                        else if ((j + x < columns) && (this.getBoardMatrix()[i][j + x] != player)) {
                            // If the next node is open, add one to the NumOfOpenEnd for that element
                            if (this.getBoardMatrix()[i][j + x] == 0) {
                                NumOfOpenEnd[0]++;
                            }
                            // If the previous node is open, add one to the NumOfOpenEnd for that element
                            if (((j - 1) >= 0) && (this.getBoardMatrix()[i][j - 1] == 0)) {
                                NumOfOpenEnd[0]++;
                            }
                            break;
                        }
                        else {
                            break;
                        }
                    }
                    // Repeat the iteration for element 1
                    for(int x=0; x<=winNumber; x++) {
                        //check if next is connected
                        if ((i+x < rows) && (this.getBoardMatrix()[i+x][j] == player)) {
                            Count[1]++;
                        }
                        //if next is boundary
                        else if (i + x == rows) {
                            //if the previous one is open
                            if (((i - 1) >= 0) && (this.getBoardMatrix()[i - 1][j] == 0)) {
                                NumOfOpenEnd[1]++;
                            }
                            break;
                        }
                        //if next is not connected and within grid
                        else if (((i + x < rows) && (this.getBoardMatrix()[i + x][j] != player)) || (i + x == rows)) {
                            //if next is open
                            if (this.getBoardMatrix()[i + x][j] == 0) {
                                NumOfOpenEnd[1]++;
                            }
                            //if the previous one is open
                            if (((i - 1) >= 0) && (this.getBoardMatrix()[i - 1][j] == 0)) {
                                NumOfOpenEnd[1]++;
                            }
                            break;
                        }
                        else {
                            break;
                        }
                    }

                    // Repeat the iteration for element 2
                    for(int x=0; x<=winNumber; x++) {
                        //check if next is connected
                        if ((i+x < rows) && (j+x < columns) && (this.getBoardMatrix()[i+x][j+x] == player)) {
                            Count[2]++;
                        }
                        //if next is boundary
                        else if ((i+x == rows) && (j+x == columns)) {
                            //if the previous one is open
                            if (((i - 1) >= 0) && ((j - 1) >= 0) && (this.getBoardMatrix()[i - 1][j - 1] == 0)) {
                                NumOfOpenEnd[2]++;
                            }
                            break;
                        }
                        //if next is not connected and within grid
                        else if ((i+x < rows) && (j+x < columns) && (this.getBoardMatrix()[i+x][j+x] != player)) {
                            //if next is open
                            if (this.getBoardMatrix()[i + x][j + x] == 0) {
                                NumOfOpenEnd[2]++;
                            }
                            //if the previous one is open
                            if (((i - 1) >= 0) && ((j - 1) >= 0) && (this.getBoardMatrix()[i - 1][j - 1] == 0)) {
                                NumOfOpenEnd[2]++;
                            }
                            break;
                        }
                        else {
                            break;
                        }
                    }

                    // Repeat the iteration for element 3
                    for(int x=0; x<=winNumber; x++) {
                        //check if next is connected
                        if ((i-x >= 0) && (j+x < columns) && (this.getBoardMatrix()[i-x][j+x] == player)) {
                            Count[3]++;
                        }
                        //if next is boundary
                        else if ((i-x > -1) && (j+x == columns)) {
                            //if the previous one is open
                            if (((i + 1) < rows) && ((j - 1) >= 0) && (this.getBoardMatrix()[i + 1][j - 1] == 0)) {
                                NumOfOpenEnd[3]++;
                            }
                            break;
                        }
                        //if next is not connected and within grid
                        else if ((i-x >= 0) && (j+x < columns) && (this.getBoardMatrix()[i-x][j+x] != player)) {
                            //if next is open
                            if (this.getBoardMatrix()[i - x][j + x] == 0) {
                                NumOfOpenEnd[3]++;
                            }
                            //if the previous one is open
                            if (((i + 1) < rows) && ((j - 1) >= 0) && (this.getBoardMatrix()[i + 1][j - 1] == 0)) {
                                NumOfOpenEnd[3]++;
                            }
                            break;
                        }
                        else {
                            break;
                        }
                    }

                }

                // If the current player is player one (as determined above), add the utility value for the searched
                // nodes to the Heuristic for player 1
                double ccc = NumOfOpenEnd[0] * Math.pow(10d, Count[0]) + NumOfOpenEnd[1] * Math.pow(10d, Count[1]) + NumOfOpenEnd[2] * Math.pow(10d, Count[2]) + NumOfOpenEnd[3] * Math.pow(10d, Count[3]);

                if(e)
                {
                    if (player == 1)
                    {
                        Heuristic += ccc;
                    }
                    // If the current player is player two, add the utility value for the searched nodes to the Heuristic
                    // for player 2
                    else if (player == 2)
                    {
                        Heuristic -= ccc;
                    }
                }
                else
                {
                    if (player == 1)
                    {
                        Heuristic -= ccc;
                    }
                    // If the current player is player two, add the utility value for the searched nodes to the Heuristic
                    // for player 2
                    else if (player == 2)
                    {
                        Heuristic += ccc;
                    }
                }


                Arrays.fill(Count,0);
                Arrays.fill(NumOfOpenEnd,0);
            }
        }
        return Heuristic;
    }
}