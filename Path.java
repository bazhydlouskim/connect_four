import java.util.ArrayList;

public class Path {

    ArrayList<State> path;
    State initialState;
    State currentState;


    public Path (Board board) {
        path = new ArrayList<>();
        initialState = new State(board);
        currentState = initialState;
    }


    void randomGame() {
        Player player1 = new RandomPlayer("X" , "O");
        Player player2 = new RandomPlayer("O", "X");

        playGame(player1, player2);

    }



    void minimaxGame() {
        Player player1 = new RandomPlayer("X", "O");
        Player player2 = new MiniMaxPlayer("O", "X");

        playGame(player1, player2);

    }

    void abGame() {
        Player player1 = new RandomPlayer("X", "O");
        Player player2 = new AlphaBetaPlayer("O", "X");

        playGame(player1, player2);

    }


    private void playGame(Player player1, Player player2) {
        Player currentPlayer = player1;
        Player nextPlayer = player2;
        currentState.currentPlayer = currentPlayer.label;
        currentState.nextPlayer = nextPlayer.label;

        while (!currentState.isGoal()) {

            currentState = currentPlayer.makeMove(currentState);
            //System.out.println("Playing Game:");
            //currentState.board.print();
            Player temp = currentPlayer;
            currentPlayer = nextPlayer;
            nextPlayer = temp;
            currentState.currentPlayer = currentPlayer.label;
            currentState.nextPlayer = nextPlayer.label;

        }
        currentState.printPath();
        printResult();
    }


    private void printResult() {
        String winner = currentState.board.winner();
        if (winner != null) {
            if (winner.equals("TIE")) System.out.println(winner);
            else System.out.println("Player " + winner + " won!");
        }
    }
}
