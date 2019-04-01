import java.util.ArrayList;

public class Board {


    String[][] board;
    private int height;
    private int width;
    private int connect;

    public Board(String[][] board) {
        height = board.length;
        width = board[0].length;
        connect = 3;
        this.board = board;
    }


    static Board initialize(String input) {

        if ((input == null) || input.length() == 0) {
            String[][] board = new String[3][4];
            //String[][] board = new String[4][5];
            return new Board(board);
        }

        String[] parts = input.split("\\|");
        if (parts.length < 1) return null;
        int width = parts[0].length();
        if (width < 1) return null;
        for (String part : parts) {
            if (part.length() != width) return null;
        }

        String[][] board = new String[parts.length][width]; // Row and column

        for (int j = 0; j < parts.length; j++) {
            String row = parts[j];
            char[] positions = row.toCharArray();
            for (int i = 0; i < width; i++) {
                char c = positions[i];
                if (c == ' ') {
                    board[j][i] = null; // Empty
                } else {
                    board[j][i] = "" + c;
                }
            }
        }


        Board newBoard = new Board(board);
        return newBoard;
    }


    String compact_string() {
        String out = "";

        for (int i = 0; i < height; i++) {

            for (int j = 0; j < width; j++) {
                if (board[i][j] == null) {
                    out += " ";
                } else {
                    out += board[i][j];
                }
            }
            out += "|";
        }
        return out.substring(0,out.length()-1);
    }


    void print() {

        System.out.print(" ");
        for (int i = 0; i < width; i++) { System.out.print("-"); }
        System.out.println();

        for (int i = 0; i < height; i++) {

            System.out.print("|");
            for (int j = 0; j < width; j++) {
                if (board[i][j] == null) {
                    System.out.print(" ");
                } else {
                    System.out.print("" + board[i][j]);
                }
            }
            System.out.println("|");
        }

        System.out.print(" ");
        for (int i = 0; i < width; i++) { System.out.print("–"); }
        System.out.println();

    }


    private String[] printToStringArray() {

        String[] out = new String[height + 2];
        out[0] = " ";
        for (int i = 0; i < width; i++) { out[0] += "-"; }
        out[0] += " ";

        for (int i = 0; i < height; i++) {

            out[i+1] = "|";
            for (int j = 0; j < width; j++) {
                if (board[i][j] == null) {
                    out[i+1] += " ";
                } else {
                    out[i+1] += board[i][j];
                }
            }
            out[i+1] += "|";
        }

        out[height+1] = " ";
        for (int i = 0; i < width; i++) { out[height+1] += "-"; }
        out[height+1] += " ";

        return out;
    }


    void printNext(String label) {

        ArrayList<String[]> strings = new ArrayList<>();
        for (Board board : next(label)) {
            strings.add(board.printToStringArray());
        }

        for (int i = 0; i < height+2; i++) {
            for (String[] x : strings) {
                System.out.print(x[i] + " ");
            }
            System.out.println();
        }
    }


    private static void printAllBoards(ArrayList<Board> list) {

        if (list == null || list.size() == 0) return;

        ArrayList<String[]> strings = new ArrayList<>();
        for (Board board : list) {
            strings.add(board.printToStringArray());
        }

        int height = list.get(0).height;
        for (int i = 0; i < height+2; i++) {
            for (String[] x : strings) {
                System.out.print(x[i] + " ");
            }
            System.out.println();
        }
    }


    static void printBoards(ArrayList<Board> list) {

        if (list == null || list.size() == 0) return;

        for (int i = 0; i < list.size(); i+= 6) {
            printAllBoards(new ArrayList<>(list.subList(i, Math.min(i + 6, list.size()))));
        }
    }


    private int first_empty(int column) {
        int j = height - 1;
        if (board[j][column] != null)
        for ( ; j >= 0 && board[j][column] != null; j--);
        if (j == 0 && board[j][column] != null) return -1;
        return j;
    }


    ArrayList<Board> next(String label) {

        ArrayList<Board> next = new ArrayList<>();

        for (int i = 0; i < width; i++) {
            int j = first_empty(i);
            if (j >= 0) {
                Board board = cloneBoard();
                board.board[j][i] = label;
                next.add(board);
            }
        }

        return next;
    }


    private int empties() {
        String line = compact_string();
        return (int) line.chars().filter(ch -> ch ==' ').count();
    }


    private Board cloneBoard() {
        return Board.initialize(compact_string());
    }


    private boolean _winner_test(String label, int i, int j, int di, int dj) {
        for (int k = 0; k < connect - 1; k++) {
            i += di;
            j += dj;
            if ((i > -1 && i < height) && (j > -1 && j < width)) {
                if ((board[i][j] == null) || !board[i][j].equals(label))
                    return false;
            } else return false;
        }

        return true;
    }


    String winner() {
        for (int j = 0; j < width; j++) {
            for (int i = 0; i < height; i++) {


                String label = board[i][j];
                if (label != null) {
                    if (_winner_test(label, i, j, +1, 0) ||
                            _winner_test(label, i, j, 0, +1) ||
                            _winner_test(label, i, j, +1, +1) ||
                            _winner_test(label, i, j, -1, +1))
                        return label;
                }
            }
        }
        if (empties() == 0) return "TIE"; else return null;
    }


    boolean isCompleted() {
        return winner() != null;
    }


    @Override
    public String toString() {
        return compact_string();
    }
}
