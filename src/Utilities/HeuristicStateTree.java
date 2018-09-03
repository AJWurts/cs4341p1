package Utilities;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class HeuristicStateTree extends StateTree {
    public HeuristicStateTree(int r, int c, int w, int t, boolean p1, boolean p2, StateTree p) {
        super(r, c, w, t, p1, p2, p);
    }

    public void setBoard()
    {
        int[][] b = new int [rows][columns]; // matrix representing the board (0 = empty, 1 = player1, 2 = player2)

            for (int i=rows-1; i>=0; i--)
            {
                for (int j = 0; j < columns; j++)
                {
                    b[i][j] = parent.getBoardMatrix()[i][j];
                }
            }
        this.boardMatrix = b;
    }


    public int eval()
    {
            int Heuristic = 0;
            int player = 0;
            int[] Count = new int[4];
            int[] NumOfOpenEnd = new int[4];
            for(int i=0; i< rows; i++)
            {
                for(int j=0; j<columns; j++)
                {
                    if(this.getBoardMatrix()[i][j] == 0)
                    {
                        player = 0;
                        for(int x=0; x<4; x++)
                        {
                            Count[x] = 0;
                        }
                    }
                    else
                    {
                        player = this.getBoardMatrix()[i][j];
                        for(int x=0; x<=winNumber; x++) {
                            //check if next is connected
                            System.out.println(x);
                            if ((j + x < columns) && (this.getBoardMatrix()[i][j + x] == player)) {
                                Count[0]++;
                            }
                            //if next is the boundary
                            else if (j + x == columns) {
                                //if the previous one is open
                                if (((j - 1) >= 0) && (this.getBoardMatrix()[i][j - 1] == 0)) {
                                    NumOfOpenEnd[0]++;
                                }
                                break;
                            }
                            //if next is not connected and within grid
                            else if ((j + x < columns) && (this.getBoardMatrix()[i][j + x] != player)) {
                                //if next is open
                                if (this.getBoardMatrix()[i][j + x] == 0) {
                                    NumOfOpenEnd[0]++;
                                }
                                //if the previous one is open
                                if (((j - 1) >= 0) && (this.getBoardMatrix()[i][j - 1] == 0)) {
                                    NumOfOpenEnd[0]++;
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }

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
                    if (player == 1) {
                        //System.out.println("turn: " + parent.turn);
                        //System.out.println("C1: " + Count[0] + ",C2: " + Count[1] + ",C3: " + Count[2] + ",C4: " + Count[3]);
                        //System.out.println("O1: " + NumOfOpenEnd[0] + ",O2: " + NumOfOpenEnd[1] + ",O3: " + NumOfOpenEnd[2] + ",O4: " + NumOfOpenEnd[3]);
                        double ccc = NumOfOpenEnd[0] * Math.pow(10d, Count[0]) + NumOfOpenEnd[1] * Math.pow(10d, Count[1]) + NumOfOpenEnd[2] * Math.pow(10d, Count[2]) + NumOfOpenEnd[3] * Math.pow(10d, Count[3]);
                        //System.out.println("i: " + i + "j: " + j);
                        //System.out.println("localH: " + ccc);
                        Heuristic += ccc;
                    }
                    else if (player == 2)
                    {
                        //System.out.println("turn: " + parent.turn);
                        //System.out.println("C1: " + Count[0] + ",C2: " + Count[1] + ",C3: " + Count[2] + ",C4: " + Count[3]);
                        //System.out.println("O1: " + NumOfOpenEnd[0] + ",O2: " + NumOfOpenEnd[1] + ",O3: " + NumOfOpenEnd[2] + ",O4: " + NumOfOpenEnd[3]);
                        double ccc = NumOfOpenEnd[0]*Math.pow(10d,Count[0]) + NumOfOpenEnd[1]*Math.pow(10d,Count[1]) + NumOfOpenEnd[2]*Math.pow(10d,Count[2]) + NumOfOpenEnd[3]*Math.pow(10d,Count[3]);
                        //System.out.println("i: "+i+"j: "+j);
                        //System.out.println("localH: " + ccc);
                        Heuristic -= ccc;
                    }
                    Arrays.fill(Count,0);
                    Arrays.fill(NumOfOpenEnd,0);
                }
            }
            System.out.println("H: "+Heuristic);
        return Heuristic;
    }
}
