import java.util.ArrayList;
import java.util.Comparator;

public class MiniMaxPlayer extends Player {
    @Override
    State makeMove(State previousState) {

        ArrayList<State> children = previousState.getChildren(label);
        children.sort(Comparator.comparingDouble(x -> x.min_value(label, oppositePlayer)));

        State selectedChild = children.get(children.size() - 1);
        previousState.cleanup(selectedChild);

        return selectedChild;
    }


    MiniMaxPlayer (String label, String oppositePlayer) {
        super(label, oppositePlayer);
    }
}
