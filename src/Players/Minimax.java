package Players;

import Utilities.HeuristicStateTree;
import Utilities.Move;
import Utilities.StateTree;

public class Minimax {
    // Actions are 0-BoardWidth plus Dropout represented by int of value boardWidth

    // Taken from Referee.java class
    // This counts how many n-in-a-rows each player has
    public static int checkConnect(StateTree board) {
        int winner = 0;
        int[] count = new int[4];
        int winTotal = 0;
        for(int i=0; i<board.rows; i++)
        {
            for(int j=0; j<board.columns; j++)
            {
                if(board.getBoardMatrix()[i][j] == 0)
                {
                    winner = 0;
                    for(int x=0; x<4; x++)
                    {
                        count[x] = 0;
                    }
                }
                else
                {
                    winner = board.getBoardMatrix()[i][j];
                    for(int x=0; x<board.winNumber; x++)
                    {
                        if((j+x < board.columns) && (board.getBoardMatrix()[i][j+x] == winner))
                            count[0]++;
                        else
                            count[0] = 0;
                        if((i+x < board.rows) && (board.getBoardMatrix()[i+x][j] == winner))
                            count[1]++;
                        else
                            count[1] = 0;
                        if((i+x < board.rows) && (j+x < board.columns) && (board.getBoardMatrix()[i+x][j+x] == winner))
                            count[2]++;
                        else
                            count[2] = 0;
                        if((i-x >= 0) && (j+x < board.columns) && (board.getBoardMatrix()[i-x][j+x] == winner))
                            count[3]++;
                        else
                            count[3] = 0;
                    }
                }
                for(int x=0; x<4; x++)
                {
                    if(count[x] == board.winNumber)
                    {
                        if(winner == 1)
                            winTotal++;
                        else if(winner == 2)
                            winTotal--;
                    }
                    count[x] = 0;
                }
                winner = 0;
            }
        }
        return winTotal;
    }

    // Taken from Referee.java class
    public static boolean checkFull(StateTree board) {
        for(int i=0; i<board.rows; i++)
        {
            for(int j=0; j<board.columns; j++)
            {
                if(board.getBoardMatrix()[i][j] == 0)
                    return false;
            }
        }
        return true;
    }

    private static boolean terminalTest(StateTree board)
    {
        return checkConnect(board) != 0 && !checkFull(board);
    }

    public static void Utility(HeuristicStateTree board) {
        return;
    }

    private static int Min(int a, int b) {
        return a > b ? b : a;
    }

    private static int Max(int a, int b) {
        return a > b ? a : b;
    }


    private static int maxValue(HeuristicStateTree board) {
        if (Minimax.terminalTest(board))
            Utility(board);

        int v = Integer.MIN_VALUE;

        boolean dropping = false;
        for (int move = 0; move < board.rows * 2; move++) {
            if (move == board.rows) dropping = true;
            Move m = new Move(dropping, move);
            if (board.validMove(m)) {
                board.makeMove(m);
                v = Minimax.Max(v, Minimax.minValue(board));
            }
        }

        return v;
    }



    private static int minValue(HeuristicStateTree board) {
        if (Minimax.terminalTest(board))
            Utility(board);

        int v = Integer.MAX_VALUE;

        boolean dropping = false;

        for (int move = 0; move < board.rows * 2; move++) {
            if (move == board.rows) dropping = true;
            Move m  = new Move(dropping, move);
            if (board.validMove(m)) {
                board.makeMove(m);
                v = Minimax.Min(v, Minimax.maxValue(board));
            }
        }
        return v;
    }

    public Move search(HeuristicStateTree board) {
        return new Move(true, 0);
    }

}
