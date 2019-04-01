import java.util.ArrayList;
import java.util.Comparator;

public class AlphaBetaPlayer extends Player {
    @Override
    State makeMove(State previousState) {

        ArrayList<State> children = previousState.getChildren(label);
        children.sort(Comparator.comparingDouble(x -> x.ab_max_value(label, oppositePlayer, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY)));

        State selectedChild = children.get(children.size() - 1);
        previousState.cleanup(selectedChild);

        return selectedChild;
    }


    AlphaBetaPlayer (String label, String oppositePlayer) {
        super(label, oppositePlayer);
    }
}
