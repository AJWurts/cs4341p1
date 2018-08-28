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
        return 0;
    }



    @Override
    public Move getMove(StateTree state) {
        return new Move(false, 1);
    }
}


