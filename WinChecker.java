import java.util.ArrayList;

public class WinChecker {

    private static Board.symbol[][] board;

    public static boolean isWin(ArrayList<Integer> move, Board.symbol turn, Board.symbol[][] givenBoard) {
        board = givenBoard;
        int dim = board.length;
        int col = move.get(0);
        int row = move.get(1);

        if (rowWin(col, row, turn, dim) || colWin(col, row, turn, dim) || diagWin(col, row, turn, dim)) {
            return true;
        }

        return false;
    }

    private static boolean rowWin(int col, int row, Board.symbol turn, int dim) {
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

    private static boolean colWin(int col, int row, Board.symbol turn, int dim) {
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

    private static boolean diagWin(int col, int row, Board.symbol turn, int dim) {
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

        currentCol = col;
        currentRow = row;

        while ((currentCol<dim) && (currentRow>=0)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol++;
            currentRow--;
        }

        currentCol = col;
        currentRow = row;

        while ((currentCol>=0) && (currentRow<dim)) {
            if (board[currentCol][currentRow] == turn) {
                found++;
            } else {
                break;
            }
            currentCol--;
            currentRow++;
        }


        if ((found-3) == dim) {
            return true;
        }
        return false;
    }
}
