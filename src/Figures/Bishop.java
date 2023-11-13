package Figures;

import java.util.Objects;

public class Bishop extends Figure{
    public Bishop(String name, char color) {
        super(name, color);
    }
    private boolean checkDirectionBlocks(int row, int col, int row1, int col1, Figure[][] fields) {
        if( Math.abs(row - row1) == Math.abs(col-col1) ){
            int i, j;
            if ( (row < row1 && col < col1) || (row1 < row && col1 < col) ) {
                i = Math.min(row, row1) + 1;
                j = Math.min(col, col1) + 1;
                while (i < Math.max(row, row1) && j < Math.max(col, col1)) {
                    if (fields[i][j] != null) return false;
                    i++;
                    j++;
                }
            } else {
                i = Math.max(row, row1) - 1;
                j = Math.min(col, col1) + 1;
                while (i > Math.min(row, row1) && j < Math.max(col, col1)) {
                    if (fields[i][j] != null) return false;
                    i--;
                    j++;
                }
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields) {
        if ( !super.canMove(row, col, row1, col1, fields) ){ return false; }

        if (fields[row1][col1] != null) return false;
        return checkDirectionBlocks(row, col, row1, col1, fields);
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1, Figure[][] fields) {
        if ( !checkDirectionBlocks(row, col, row1, col1, fields) ) { return false; }
        return true;
    }
}
