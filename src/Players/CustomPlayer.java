package Players;

import Utilities.HeuristicStateTree;
import Utilities.Move;
import Utilities.StateTree;

public class CustomPlayer extends Player {

    public CustomPlayer(String n, int t, int l)
    {
        super(n, t, l);
    }

    public Move minimaxDecision(HeuristicStateTree s) {
       Minimax minimax = new Minimax();
       minimax.minMax(s, 1, 0,0,true);
       return minimax.OptimalMove;
    }



    @Override
    public Move getMove(StateTree state) {
        HeuristicStateTree NewTree = new HeuristicStateTree(state.rows,state.columns,state.winNumber,state.turn,state.pop1,state.pop2,state);
        NewTree.setBoard();
        Move a = minimaxDecision(NewTree);
        return a;
    }
}


