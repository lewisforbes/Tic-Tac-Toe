import java.util.Scanner;

public class Main {

    private static final int maxDim = 9;
    private static final int minDim = 3;

    public static int xScore = 0;
    public static int oScore = 0;

    private static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Welcome to Naughts and Crosses!");
        initialise();
    }

    private static void initialise() {
        int dim = getDim();
        new Board(dim, getTurn());
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

            dim = getDim();
            new Board(dim, getTurn());
        }

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
}
