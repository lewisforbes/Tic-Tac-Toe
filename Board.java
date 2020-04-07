import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Board {

    private Scanner input = new Scanner(System.in);

    public enum symbol {X, O}
    private symbol firstTurn;

    private symbol[][] board; // symbol[column][row]
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private final String hLine = "—";
    private final String vLine = "|";
    private final String blankChar = " ";
    private final int paddingSize = 3;

    public Board(int dim, symbol firstTurn) {
        this.firstTurn = firstTurn;
        this.board = new symbol[dim][dim];
        play();
    }

    private void play() {
        symbol turn = firstTurn;
        ArrayList<Integer> parsedMove;

        while (true) {
            displayBoard();
            parsedMove = getMove(turn);
            board[parsedMove.get(0)][parsedMove.get(1)] = turn;
            if (win(parsedMove, turn)) {
                break;
            }
            turn = changeTurn(turn);
        }

        displayBoard();
        System.out.println(turn + " won!");

        if (turn == symbol.X) {
            Main.xScore++;
        } else {
            Main.oScore++;
        }
    }

    private void displayBoard() {
        int dim = board.length;
        String padding = " ".repeat(paddingSize);


        String[][] boardForPrinting = new String[dim][dim];
        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                if (board[r][c] == null) {
                    boardForPrinting[r][c] = blankChar;
                } else {
                    boardForPrinting[r][c] = board[r][c].name();
                }
            }
        }

        String insideLine = hLine.repeat(3) + hLine.repeat(4 * (dim - 1));

        String output = " " + padding + mkColLabels() + "\n" + "\n".repeat(paddingSize / 3);
        output += 1 + padding + getRow(boardForPrinting, 0) + "\n";
        for (int r = 1; r <= dim - 1; r++) {
            output += " " + padding + insideLine + "\n";
            output += (r + 1) + padding + getRow(boardForPrinting, r) + "\n";
        }
        System.out.println(output);
    }

    private String mkColLabels() {
        if (alphabet.length() < board.length) {
            throw new IllegalArgumentException("The dimension of the board is too high");
        }

        String output = " " + alphabet.charAt(0) + " ";
        for (int c = 1; c < board.length; c++) {
            output += "  " + alphabet.charAt(c) + " ";
        }

        return output;

    }

    private String getRow(String[][] board, int row) {
        String output = blankChar + board[0][row] + blankChar;
        for (int c = 1; c <= board.length - 1; c++) {
            output += vLine + blankChar + board[c][row] + blankChar;
        }
        return output;
    }

    private symbol changeTurn(symbol turn) {
        if (turn == symbol.X) {
            return symbol.O;
        }

        if (turn == symbol.O) {
            return symbol.X;
        }

        throw new IllegalArgumentException("The turn passed in was invalid");
    }

    private ArrayList<Integer> getMove(symbol turn) {

        boolean validMove = false;
        String inputtedMove = "";

        while (!validMove) {
            System.out.println(turn + " to go. Enter your move:");
            inputtedMove = input.nextLine();

            if (!isFormatValid(inputtedMove)) {
                System.err.println("The inputted move is not of the correct format.\n");
            } else if (!isMoveValid(inputtedMove)) {
                System.err.println("The inputted move is not possible.\n");
            } else {
                validMove = true;
            }
        }

        if (inputtedMove.equals("")) {
            throw new IllegalArgumentException("inputtedMove has not been changed after initialisation");
        }

        return parseMove(inputtedMove.toUpperCase());
    }

    private boolean isFormatValid(String inputtedMove) {

        if (inputtedMove.length() < 2) {
            return false;
        }
        if (!Character.isLetter(inputtedMove.charAt(0))) {
            return false;
        }

        try {
            if (Integer.parseInt(inputtedMove.substring(1)) <= 0) {
                return false;
            }
        } catch (NumberFormatException nfe) {
            return false;
        }

        return true;
    }

    private boolean isMoveValid(String inputtedMove) {
        ArrayList<Integer> move = parseMove(inputtedMove.toUpperCase());
        if (move.size() != 2) {
            throw new IllegalArgumentException("the length of move is not 2");
        }

        int column = move.get(0);
        int row = move.get(1);

        if ((row > board.length) || (column > board.length))
            return false;

        if (board[column][row] != null) {
            return false;
        }

        return true;
    }

    private ArrayList<Integer> parseMove(String formattedMove) {
        int column = -1;
        int row = Integer.parseInt(formattedMove.substring(1)) - 1;

        for (int i = 0; i < alphabet.length(); i++) {
            if (formattedMove.charAt(0) == alphabet.charAt(i)) {
                column = i;
            }
        }

        if (column == -1) {
            throw new IllegalArgumentException("column has not been changed after initialisation");
        }

        return new ArrayList<>(Arrays.asList(column, row));
    }

    private boolean win(ArrayList<Integer> move, symbol turn) {
        int dim = board.length;
        int col = move.get(0);
        int row = move.get(1);

        if (rowWin(col, row, turn, dim) || colWin(col, row, turn, dim) || diagWin(col, row, turn, dim)) {
            return true;
        }

        return false;
    }

    private boolean rowWin(int col, int row, symbol turn, int dim) {
        int found = 0;

        for (int c = col; c <= dim - 1; c++) {
            if (board[c][row] == turn) {
                found++;
            } else {
                break;
            }
        }

        for (int c = col; c >= 0; c--) {
            if (board[c][row] == turn) {
                found++;
            } else {
                break;
            }
        }

        if ((found-1) == dim) {
            return true;
        }
        return false;
    }

    private boolean colWin(int col, int row, symbol turn, int dim) {
        int found = 0;

        for (int r = row; r <= dim - 1; r++) {
            if (board[col][r] == turn) {
                found++;
            } else {
                break;
            }
        }

        for (int r = row; r >= 0; r--) {
            if (board[col][r] == turn) {
                found++;
            } else {
                break;
            }
        }

        if ((found-1) == dim) {
            return true;
        }
        return false;
    }

    private boolean diagWin(int col, int row, symbol turn, int dim) {
        int found = 0;

        int currentCol = col;
        int currentRow = row;

        while ((currentCol<dim) && (currentRow<dim)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol++;
            currentRow++;
        }

        currentCol = col;
        currentRow = row;

        while ((currentCol>=0) && (currentRow>=0)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol--;
            currentRow--;
        }


        if ((found-1) == dim) {
            return true;
        }
        return false;
    }
}