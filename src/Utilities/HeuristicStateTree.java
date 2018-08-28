package Utilities;

import java.io.PrintStream;

public class HeuristicStateTree extends StateTree {
    public HeuristicStateTree(int r, int c, int w, int t, boolean p1, boolean p2, StateTree p) {
        super(r, c, w, t, p1, p2, p);
    }


    public int eval()
    {
            int Heuristic = 0;
            int player = 0;
            int[] count = new int[4];
            for(int i=0; i< parent.rows; i++)
            {
                for(int j=0; j<parent.columns; j++)
                {
                    if(parent.getBoardMatrix()[i][j] == 0)
                    {
                        player = 0;
                        for(int x=0; x<4; x++)
                        {
                            count[x] = 0;
                        }
                    }
                    else
                    {
                        player = parent.getBoardMatrix()[i][j];
                        for(int x=0; x<parent.winNumber; x++)
                        {
                            if((j+x < parent.columns) && (parent.getBoardMatrix()[i][j+x] == player))
                                count[0]++;
                            else
                                count[0] = 0;
                            if((i+x < parent.rows) && (parent.getBoardMatrix()[i+x][j] == player))
                                count[1]++;
                            else
                                count[1] = 0;
                            if((i+x < parent.rows) && (j+x < parent.columns) && (parent.getBoardMatrix()[i+x][j+x] == player))
                                count[2]++;
                            else
                                count[2] = 0;
                            if((i-x >= 0) && (j+x < parent.columns) && (parent.getBoardMatrix()[i-x][j+x] == player))
                                count[3]++;
                            else
                                count[3] = 0;
                        }
                    }
                    //TODO: change player name from 1 to actually vaiable
                    if (player == 1)
                    {
                        Heuristic += (Math.pow(2d,count[0]) + Math.pow(2d,count[1]) + Math.pow(2d,count[2]) + Math.pow(2d,count[3]));
                    }
                    else if (player ==2)
                    {
                        Heuristic -= (Math.pow(2d,count[0]) + Math.pow(2d,count[1]) + Math.pow(2d,count[2]) + Math.pow(2d,count[3]));
                    }

                }
            }
            return Heuristic;
    }
}
