package Figures;

import java.util.Objects;

public class King extends Figure{
    public boolean isFirstStep = true;
    public King(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields) {
        if ( !super.canMove(row, col, row1, col1, fields) ){ return false; }

        if ( Math.abs(row - row1)<=1 && Math.abs(col - col1)<=1 ) { isFirstStep = false; return true; }
        if ( Math.abs(col - col1)==2 && Math.abs(row - row1)==0 && isFirstStep ) { return true; }
        return false;
    }
    // TODO: 10.11.2023 допилить проверку на сближение с вражеским королем
    // TODO: 10.11.2023 допилить рокировку королю по типу 1 хода пешки

    // TODO: 10.11.2023 мб запилить проверку чисто в короля по линиям + конь
    @Override
    public boolean canAttack(int row, int col, int row1, int col1, Figure[][] fields) {
        if ( Math.abs(row - row1)>1 || Math.abs(col - col1)>1 ) { return false; }
        return true;
    }
}
