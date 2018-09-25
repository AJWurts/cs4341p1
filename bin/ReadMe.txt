To run this program, compile the code and run RunReferee.java.

Our custom player uses a heuristic function to evaluate the utility value of different possible moves each turn and
execute the optimal move for our player. Our heuristic strategy involves weighting different characteristics of each node
on the game board, as described in Experiment.txt, to create a value for each node to a ply of a given depth. Once the
game tree has been searched and each move has been evaluated, the player chooses the optimal move, or the move with the
highest utility value.