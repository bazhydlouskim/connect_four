public abstract class Player {

    String label;
    String oppositePlayer;

    Player (String label, String oppositePlayer) {
        this.label = label;
        this.oppositePlayer = oppositePlayer;
    }

    abstract State makeMove(State previousState);
}
