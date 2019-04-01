public class Main {

    public static void main (String[] args) {

        String command;
        String board;

        if (args.length < 1) {
            System.err.println("Command Required! Exiting...");
            System.exit(-1);
        }
        command = args[0];
        //command = "print";

        if (args.length == 2) {
            board = args[1];
        } else {
            board = null;
        }

        Board b = Board.initialize(board);
        if (b == null) {
            System.err.println("Invalid Board Supplied! Exiting...");
            System.exit(-1);
        }
        switch (command) {
            case "next": /////////////////////////////////////////////////
                b.printNext("X");
                break;
            case "random":
                Path path = new Path(b);
                path.randomGame();
                break;
            case "minmax":
                path = new Path(b);
                path.minimaxGame();
                break;
            case "alphabeta":
                path = new Path(b);
                path.abGame();
                break;
            case "test":

                long startTime = System.nanoTime();
                for (int i = 0; i < 10; i++) {
                    Board b1 = Board.initialize(board);
                    path = new Path(b1);
                    path.minimaxGame();
                }
                long endTime = System.nanoTime();
                long duration = (endTime - startTime) / 1000000;
                System.out.println("Average MiniMax time: " + duration/10000.0 + " s");


                startTime = System.nanoTime();
                for (int i = 0; i < 10; i++) {
                    Board b1 = Board.initialize(board);
                    path = new Path(b1);
                    path.abGame();

                }
                endTime = System.nanoTime();
                duration = (endTime - startTime) / 1000000;
                System.out.println("Average Alpha-Beta time: " + duration/10000.0 + " s");
                break;
        }

    }

}
