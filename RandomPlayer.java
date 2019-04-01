import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class RandomPlayer extends Player {
    public RandomPlayer(String label, String oppositePlayer) {
        super(label, oppositePlayer);
    }

    @Override
    State makeMove(State previousState) {
        //return previousState.randomNext();

        ArrayList<Board> children = previousState.board.next(previousState.currentPlayer);
        int i = ThreadLocalRandom.current().nextInt(children.size());

        return new State(children.get(i), previousState, previousState.currentDepth + 1);
    }


}
