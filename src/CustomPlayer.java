package Players;

import Utilities.HeuristicStateTree;
import Utilities.Move;
import Utilities.StateTree;

public class CustomPlayer extends Player {

    public CustomPlayer(String n, int t, int l)
    {
        super(n, t, l);
    }

    //Function that calls our minimax algorithm
    public Move minimaxDecision(HeuristicStateTree s) {
       //check to see if we are the first player
        Players.Minimax minimax = new Players.Minimax(checkEmpty(s));
        //run minimax algorithm with our HeuristicStateStree class s, a depth of three, smallest and biggest value possible for alpha beta rpuning and the boolean maximizing player
       minimax.minMax(s, 3, Integer.MIN_VALUE,Integer.MAX_VALUE,true);
       //returns the optimal move returned from minimax class
       return minimax.OptimalMove;
    }
    //iterate through the board to check if it is empty for our first move
    public static boolean checkEmpty(HeuristicStateTree s) {
        for(int i=0; i<s.rows; i++)
        {
            for(int j=0; j<s.columns; j++)
            {
                if(s.getBoardMatrix()[i][j] != 0)
                    return false;
            }
        }
        return true;
    }
    //Our getMove function that copies the heuristic tree that is the same as the current state of the board and returns the minimax decision.
    @Override
    public Move getMove(StateTree state) {
        HeuristicStateTree NewTree = new HeuristicStateTree(state.rows,state.columns,state.winNumber,state.turn,false,false,state);
        NewTree.setBoard();
        Move a = minimaxDecision(NewTree);
        return a;
    }
}


