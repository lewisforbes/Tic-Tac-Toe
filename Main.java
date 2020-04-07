import java.util.Scanner;

public class Main {

    private static final int maxDim = 9;
    private static final int minDim = 3;

    public static int xScore = 0;
    public static int oScore = 0;

    private static int players = 0;

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Naughts and Crosses!");
        initialise();
    }

    private static void initialise() {
        players = getPlayers();
        newGame();
        String playAgain = "YES";
        String inputtedString;

        while (true) {
            System.out.println(Board.symbol.X + " has " + xScore + " point(s).");
            System.out.println(Board.symbol.O + " has " + oScore + " point(s).");

            System.out.println("Type '" + playAgain + "' to play again!");
            inputtedString = input.nextLine();

            if (!inputtedString.equalsIgnoreCase(playAgain)) {
                System.out.println("Okay, thanks for playiing!");
                break;
            }

            newGame();
        }

    }

    private static void newGame() {
        int dim;
        if (players == 1) {
            dim = 3;
            new Board(dim, getTurn(), players);
            return;
        }


        if (players == 2) {
            dim = getDim();
            new Board(dim, getTurn(), players);
            return;
        }

        throw new IllegalArgumentException("Players is not 1 or 2");
    }

    private static Board.symbol getTurn() {
        if (((xScore + oScore) % 2) == 0) {
            return Board.symbol.X;
        }
        return Board.symbol.O;
    }

    private static int getDim() {
        String currentInput = "";

        while (true) {
            System.out.println("\nEnter the board's dimension: ");
            currentInput = input.nextLine();

            try {
                if ((Integer.parseInt(currentInput) >= minDim) || (Integer.parseInt(currentInput) <= maxDim)) {
                    break;
                }
            } catch (NumberFormatException nfe) {}

            System.out.println("Enter an number between " + minDim + " and " + maxDim + ".");
        }

        return Integer.parseInt(currentInput);
    }

    private static int getPlayers() {
        String currentInput = "";

        while (true) {
            System.out.println("\nEnter '1' for single player or '2' for two player: ");
            currentInput = input.nextLine();

            try {
                if ((Integer.parseInt(currentInput) == 1) || (Integer.parseInt(currentInput) == 2)) {
                    break;
                }
            } catch (NumberFormatException nfe) {}

            System.out.println("Enter 1 or 2.");
        }

        return Integer.parseInt(currentInput);
    }
}
