package Figures;

import java.util.Objects;

public class Knight extends Figure{
    public Knight(String name, char color) {
        super(name, color);
    }

    // TODO: 10.11.2023 для проверки шаха и мата надо будет запустить пост-экшн проверку всей доски. т.к. подставиться король может миллиардом способов 
    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields ) {
        if ( !super.canMove(row, col, row1, col1, fields) ){ return false; }
        if (fields[row1][col1] != null) return false;
        if ((Math.abs(row - row1)==1 && Math.abs(col - col1)==2) || (Math.abs(row - row1)==2 && Math.abs(col - col1)==1)){ return true; }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1, Figure[][] fields) {
        if ((Math.abs(row - row1)==1 && Math.abs(col - col1)==2) || (Math.abs(row - row1)==2 && Math.abs(col - col1)==1)){ return true; }
        return false;
    }
}
