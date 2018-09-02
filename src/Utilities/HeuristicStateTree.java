package Utilities;

import java.io.PrintStream;
import java.lang.reflect.Array;
import java.util.Arrays;

public class HeuristicStateTree extends StateTree {
    public HeuristicStateTree(int r, int c, int w, int t, boolean p1, boolean p2, StateTree p) {
        super(r, c, w, t, p1, p2, p);
    }


    public int eval()
    {
            int Heuristic = 0;
            int player = 0;
            int[] Count = new int[4];
            int[] NumOfOpenEnd = new int[4];
            for(int i=0; i< parent.rows; i++)
            {
                for(int j=0; j<parent.columns; j++)
                {
                    if(parent.getBoardMatrix()[i][j] == 0)
                    {
                        player = 0;
                        for(int x=0; x<4; x++)
                        {
                            Count[x] = 0;
                        }
                    }
                    else
                    {
                        player = parent.getBoardMatrix()[i][j];
                        for(int x=0; x<parent.winNumber; x++) {
                            //check if next is connected
                            if ((j + x < parent.columns) && (parent.getBoardMatrix()[i][j + x] == player)) {
                                Count[0]++;
                            }
                            //if next is not connected and within grid
                            else if ((j + x < parent.columns) && (parent.getBoardMatrix()[i][j + x] != player)) {
                                //if next is open
                                if (parent.getBoardMatrix()[i][j + x] == 0) {
                                    NumOfOpenEnd[0]++;
                                }
                                //if the previous one is open
                                if (((j - 1) >= 0) && (parent.getBoardMatrix()[i][j - 1] == 0)) {
                                    NumOfOpenEnd[0]++;
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }

                        for(int x=0; x<parent.winNumber; x++) {
                            //check if next is connected
                            if ((i+x < parent.rows) && (parent.getBoardMatrix()[i+x][j] == player)) {
                                Count[1]++;
                            }
                            //if next is not connected and within grid
                            else if ((i + x < parent.rows) && (parent.getBoardMatrix()[i + x][j] != player)) {
                                //if next is open
                                if (parent.getBoardMatrix()[i][j + x] == 0) {
                                    NumOfOpenEnd[1]++;
                                }
                                //if the previous one is open
                                if (((i - 1) >= 0) && (parent.getBoardMatrix()[i - 1][j] == 0)) {
                                    NumOfOpenEnd[1]++;
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }


                        for(int x=0; x<parent.winNumber; x++) {
                            //check if next is connected
                            if ((i+x < parent.rows) && (j+x < parent.columns) && (parent.getBoardMatrix()[i+x][j+x] == player)) {
                                Count[2]++;
                            }
                            //if next is not connected and within grid
                            else if ((i+x < parent.rows) && (j+x < parent.columns) && (parent.getBoardMatrix()[i+x][j+x] != player)) {
                                //if next is open
                                if (parent.getBoardMatrix()[i + x][j + x] == 0) {
                                    NumOfOpenEnd[2]++;
                                }
                                //if the previous one is open
                                if (((i - 1) >= 0) && ((j - 1) >= 0) && (parent.getBoardMatrix()[i - 1][j - 1] == 0)) {
                                    NumOfOpenEnd[2]++;
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }

                        for(int x=0; x<parent.winNumber; x++) {
                            //check if next is connected
                            if ((i-x >= 0) && (j+x < parent.columns) && (parent.getBoardMatrix()[i-x][j+x] == player)) {
                                Count[3]++;
                            }
                            //if next is not connected and within grid
                            else if ((i-x >= 0) && (j+x < parent.columns) && (parent.getBoardMatrix()[i-x][j+x] != player)) {
                                //if next is open
                                if (parent.getBoardMatrix()[i - x][j + x] == 0) {
                                    NumOfOpenEnd[3]++;
                                }
                                //if the previous one is open
                                if (((i + 1) < parent.rows) && ((j - 1) >= 0) && (parent.getBoardMatrix()[i + 1][j - 1] == 0)) {
                                    NumOfOpenEnd[3]++;
                                }
                                break;
                            }
                            else {
                                break;
                            }
                        }

                    }
                    if (player == parent.turn)
                    {
                        Heuristic += (NumOfOpenEnd[0]*Math.pow(2d,Count[0]) + NumOfOpenEnd[1]*Math.pow(2d,Count[1]) + NumOfOpenEnd[2]*Math.pow(2d,Count[2]) + NumOfOpenEnd[3]*Math.pow(2d,Count[3]));
                    }
                    else if (player != parent.turn)
                    {
                        Heuristic -= (NumOfOpenEnd[0]*Math.pow(2d,Count[0]) + NumOfOpenEnd[1]*Math.pow(2d,Count[1]) + NumOfOpenEnd[2]*Math.pow(2d,Count[2]) + NumOfOpenEnd[3]*Math.pow(2d,Count[3]));
                    }
                    Arrays.fill(Count,0);
                    Arrays.fill(NumOfOpenEnd,0);
                }
            }
            System.out.println("H: "+Heuristic);
        return Heuristic;
    }
}
