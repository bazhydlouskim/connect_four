import java.util.ArrayList;
import java.util.Comparator;

public class State {

    Board board;
    private State parent;
    private ArrayList<State> children;
    private double utility = Double.NaN;
    int currentDepth;
    String currentPlayer;
    String nextPlayer;

    public State () {
        board = null;
        parent = null;
        currentDepth = 0;
        children = null;
    }

    State(Board board) {
        this.board = board;
        parent = null;
        currentDepth = 0;
        children = null;
    }

    State(Board board, State parent, int currentDepth) {
        this.board = board;
        this.parent = parent;
        this.currentDepth = currentDepth;
        children = null;
    }


    ArrayList<State> getChildren(String currentPlayer) {

        if (children != null) return children;
        ArrayList<State> children = new ArrayList<>();

        for (Board newBoard : board.next(currentPlayer)) {
            State child = new State(newBoard, this, currentDepth + 1);
            children.add(child);
        }

        this.children = children;
        return children;
    }


    boolean isGoal() {
        return board.isCompleted();
    }


    void printPath() {
        ArrayList<Board> path = new ArrayList<>();
        path.add(board);
        State state = parent;
        while (state != null) {
            path.add(0, state.board);
            state = state.parent;
        }
        Board.printBoards(path);
    }


    double max_value(String currentPlayer, String nextPlayer) {
        if (isGoal()) {
            return utility(currentPlayer);
        }

        if (!Double.isNaN(utility)) return utility;

        ArrayList<State> children = getChildren(currentPlayer);
        children.sort(Comparator.comparingDouble(x -> x.min_value(currentPlayer, nextPlayer)));

        utility = children.get(children.size() - 1).min_value(currentPlayer, nextPlayer);

        return utility;
    }

    double min_value(String currentPlayer, String nextPlayer) {

        if (isGoal()) {
            return utility(currentPlayer);
        }

        if (!Double.isNaN(utility)) return utility;

        ArrayList<State> children = getChildren(nextPlayer);
        children.sort(Comparator.comparingDouble(x -> x.max_value(currentPlayer, nextPlayer)));

        utility = children.get(0).max_value(currentPlayer, nextPlayer);

        return utility;
    }


    double ab_max_value(String currentPlayer, String nextPlayer, double alpha, double beta) {
        if (isGoal()) {
            return utility(currentPlayer);
        }

        if (!Double.isNaN(utility)) return utility;


        ArrayList<State> children = getChildren(currentPlayer);

        //double value = Double.NEGATIVE_INFINITY;

        for (State child : children) {
            alpha = Double.max(alpha, child.ab_min_value(currentPlayer, nextPlayer, alpha, beta));
            //alpha = Double.max(alpha, value);
            if (alpha >= beta) {
                return alpha;
            }
        }

        utility = alpha;

        return utility;
    }

    double ab_min_value(String currentPlayer, String nextPlayer, double alpha, double beta) {

        if (isGoal()) {
            return utility(currentPlayer);
        }

        if (!Double.isNaN(utility)) return utility;


        ArrayList<State> children = getChildren(nextPlayer);

        //double value = Double.POSITIVE_INFINITY;

        for (State child : children) {
            beta = Double.min(beta, child.ab_max_value(currentPlayer, nextPlayer, alpha, beta));
            if (alpha >= beta) {
                return beta;
            }
        }

        utility = beta;

        return utility;
    }


    double utility(String label) {
        String winner = board.winner();
        if (winner.equals(label)) return 1 / (double) currentDepth;
        if (winner.equals("TIE")) return 0;
        return -1 / (double) currentDepth;
    }

    public void cleanup(State selectedChild) {
        parent.children = new ArrayList<>();
        parent.children.add(this);

        children = new ArrayList<>();
        children.add(selectedChild);
    }
}
