package Players;

import Utilities.HeuristicStateTree;
import Utilities.Move;
import Utilities.StateTree;

public class Minimax {
    // The current optimal move that the player can choose
    Move OptimalMove;

    // Constructor for Minimax
    public Minimax() {
        OptimalMove = new Move(false,0);
    }

    // Actions are 0-BoardWidth plus Dropout represented by int of value boardWidth
    // Taken from Referee.java class
    // This counts how many n-in-a-rows each player has

    // Check to see if a player has one by iterating through the nodes of the board and determining if any of the connections are of n-length
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
                    for(int x=0; x < board.winNumber; x++)
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

    // Function to check to see if the game is over
    private static boolean terminalTest(StateTree board)
    {
        return checkConnect(board) != 0 && !checkFull(board);
    }

    // Return the utility value of each node based on our heuristic
    public static int Utility(HeuristicStateTree board) {
        return board.eval();
    }

    // Return the lesser of two values
    private static int Min(int a, int b) {
        return a > b ? b : a;
    }

    // Return the greater of two values
    private static int Max(int a, int b) {
        return a > b ? a : b;
    }

    // Minimax function: input board, depth to search, alpha and beta for alphabeta pruning, and if currently maximizing
    // Returns the best heursitic for a certain turn to ply 'depth'
    public int minMax(HeuristicStateTree board, int depth, int alpha, int beta, boolean playerMax)
    {
        // If the game is over, return the state of the board
        if (Minimax.terminalTest(board) || depth == 0)
        {
            return Utility(board);
        }

        // If maximizing case
        if (playerMax)
        {
            // Set pop to false
            boolean pop = false;
            // Set the current max evalutation to the worst case scenario
            int maxEval = Integer.MIN_VALUE;
            // For each move, first iterate through possible moves when pop is not available, and then when pop is available
            for (int p = 0; p < 2; p++)
            {
                if(p==1)
                {
                    pop = true;
                }
                // Iterate through each possible move placing a piece in each column in turn
                for (int move = 0; move < board.columns; move++)
                {
                    // Initialize a new move
                    Move m = new Move(pop, move);
                    System.out.println(m.getColumn()+"this max move is :");
                    // Check if current move is valid
                    if (board.validMove(m))
                    {
                        // Initialize a new board
                        HeuristicStateTree NewBoard = new HeuristicStateTree(board.rows,board.columns,board.winNumber,board.turn,board.pop1,board.pop2,board);
                        // Create a copy of the current board
                        NewBoard.setBoard();
                        // Make the current move on the copy of the current board
                        NewBoard.makeMove(m);
                        // Make a recursive call to minimax with Alphabeta pruning
                        int currentEval = minMax(NewBoard,depth-1,alpha,beta,false);
                        // Store the value of the max current node between the current evaluation and the max evaluation
                        maxEval = Max(maxEval, currentEval);
                        // If the current evaluation is the optimal move, record the move as the new best move
                        if(maxEval == currentEval)
                        {
                            OptimalMove = m;
                            //System.out.println(m.getColumn()+"maxmove");
                        }
                        //alpha = Max(alpha,currentEval);
                        //if (beta <= alpha)
                        //{
                        //    break;
                        //}
                    }
                }
            }

            return maxEval;
        }
        // If minimizing case
        else
        {
            // Set pop to false
            boolean pop = false;
            // Set minimum to the current worst case scenario
            int minEval = Integer.MAX_VALUE;
            // For each move, iterate through possible moves for both pop and non-pop options
            for (int p = 0; p < 2; p++) {
                if (p == 2) {
                    pop = true;
                }
                // Iteratively check placing a piece in each column
                for (int move = 0; move < board.columns; move++)
                {
                    // Initialize a new move
                    Move m = new Move(pop, move);
                    System.out.println(m.getColumn()+"this min move is :");
                    // If the move is valid
                    //System.out.println(m.getColumn()+"this min move is :");
                    if (board.validMove(m))
                    {
                        // Initialize a new board
                        HeuristicStateTree NewBoard = new HeuristicStateTree(board.rows,board.columns,board.winNumber,board.turn,board.pop1,board.pop2,board);
                        // Create a copy of the current board
                        NewBoard.setBoard();
                        // Make the move
                        NewBoard.makeMove(m);
                        // Make a recursive call to minimax with Alphabeta pruning
                        int currentEval = minMax(NewBoard,depth-1,alpha,beta,true);
                        // Store the value of the min current node between the current evaluation and the min evaluation
                        minEval = Min(minEval, currentEval);
                        // If the current evaluation is the optimal move, record the move as the new best move
                        if(minEval == currentEval)
                        {
                            OptimalMove = m;
                            //System.out.println(m.getColumn()+"minmove");
                        }
                        //beta = Min(beta,currentEval);
                        //if (beta <= alpha)
                        //{
                        //    break;
                        //}
                    }
                }
            }
            return minEval;
        }

    }


    public Move search(HeuristicStateTree board) {
        return new Move(true, 0);
    }

}
