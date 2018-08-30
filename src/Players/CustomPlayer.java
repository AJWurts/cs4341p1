package Players;

import Utilities.HeuristicStateTree;
import Utilities.Move;
import Utilities.StateTree;

public class CustomPlayer extends Player {

    public CustomPlayer(String n, int t, int l)
    {
        super(n, t, l);
    }

    public int minimaxDecision(HeuristicStateTree s) {
       Minimax minimax = new Minimax();
       minimax.minMax(s, 10000000, 0,0,false);
        return 0;
    }



    @Override
    public Move getMove(StateTree state) {
        return new Move(false, 1);
    }
}


