import java.util.ArrayList;
import java.util.Arrays;

/**
 * This 'AI' will never lose but won't always win.
 */

public class AI {

    private static Board.symbol[][] board;
    private static Board.symbol turn;

    private static ArrayList<Integer> centre = new ArrayList<>(Arrays.asList(1,1));

    public static ArrayList<Integer> getMove(Board.symbol[][] givenBoard, Board.symbol givenTurn) {
        board = givenBoard;
        turn = givenTurn;
        ArrayList<ArrayList<Integer>> aiMoves = getMoves(turn);
        ArrayList<ArrayList<Integer>> opMoves = getMoves(getOtherPlayer(turn));

        if (aiMoves.size() == opMoves.size()) {
            return getRandomMove(); //aiFirst()
        }

        if (aiMoves.size() < opMoves.size()) {
            return aiSecond(opMoves);
        }

        throw new IllegalArgumentException("aiMovesTaken > oppMovesTaken");
    }

    private static ArrayList<Integer> aiSecond(ArrayList<ArrayList<Integer>> opMoves) {
        if ((opMoves.size() == 1) && (isCorner(opMoves.get(0)))) {
            return centre;
        }
        return getRandomMove();
    }

    private static ArrayList<Integer> getRandomMove() {
        int dim = board.length;
        //checks if a winning move can be made
        if (getPotentialWin(turn) != null) {
            return getPotentialWin(turn);
        }

        //checks if opponent can make winning move
        if (getPotentialWin(getOtherPlayer(turn)) != null) {
            return getPotentialWin(getOtherPlayer(turn));
        }

        //tries to play in all non-corner positions
        for (int i=0; i<dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((board[i][j] == null) && (!isCorner(new ArrayList<>(Arrays.asList(i,j))))) {
                    return new ArrayList<>(Arrays.asList(i,j));
                }
            }
        }

        //tries to play in all positions (inc. corners)
        for (int i=0; i<dim; i++) {
            for (int j = 0; j < dim; j++) {
                if ((board[i][j] == null)) {
                    return new ArrayList<>(Arrays.asList(i,j));
                }
            }
        }

        throw new IllegalArgumentException("There are no free spaces on the board");
    }

    private static ArrayList<Integer> getPotentialWin(Board.symbol turnToCheckWin) {
        Board.symbol[][] trialBoard;
        int dim = board.length;

        for (int r = 0; r < dim; r++) {
            for (int c = 0; c < dim; c++) {
                trialBoard = copyArray(board);
                if (trialBoard[c][r] == null) {
                    trialBoard[c][r] = turnToCheckWin;
                    if (WinChecker.isWin(new ArrayList<>(Arrays.asList(c, r)), turnToCheckWin, trialBoard)) {
                        return new ArrayList<>(Arrays.asList(c, r));
                    }
                }
            }
        } //a1, b1, c2
        return null;
    }

    private static boolean isCorner(ArrayList<Integer> coord) {
        int col = coord.get(0);
        int row = coord.get(1);

        if (((col == 0) || (col == board.length-1)) && (((row == 0) || (row == board.length-1)))) {
            return true;
        }

        return false;
    }

    private static Board.symbol getOtherPlayer(Board.symbol player) {
        if (player == Board.symbol.X) {
            return Board.symbol.O;
        }

        if (player == Board.symbol.O) {
            return Board.symbol.X;
        }

        throw new IllegalArgumentException("An invalid player has been passed through");
    }

    private static ArrayList<ArrayList<Integer>> getMoves(Board.symbol player) {
        ArrayList<ArrayList<Integer>> output = new ArrayList<>();
        ArrayList<Integer> currentCoord;
        int dim = board.length;

        for (int i=0; i<dim; i++) {
            for (int j=0; j<dim; j++) {
                if (board[i][j] == player) {
                    currentCoord = new ArrayList<>(Arrays.asList(i,j));
                    output.add(currentCoord);
                }
            }
        }

        return output;
    }

    private static Board.symbol[][] copyArray(Board.symbol[][] oldArray) {
        int size = oldArray.length;
        Board.symbol[][] output = new Board.symbol[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                output[i][j] = oldArray[i][j];
            }
        }
        return output;
    }
}
