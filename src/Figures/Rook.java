package Figures;

import java.util.Objects;

public class Rook extends Figure{
    public Rook(String name, char color) {
        super(name, color);
    }
    // TODO: 10.11.2023 рокировка делается тем-же механизмом что и проверка первого хода для пешки
    private boolean checkDirectionBlocks(int row, int col, int row1, int col1, Figure[][] fields) {
        if( (row == row1 && col !=col1) || (row != row1 && col ==col1) ){
            int i;
            if(row == row1 && col !=col1){
                for ( i = Math.min(col, col1)+1; i < Math.max(col, col1); i++ )
                    if (fields[row][i] != null) return false;
            } else {
                for ( i = Math.min(row, row1)+1; i < Math.max(row, row1); i++ )
                    if (fields[i][col] != null) return false;
            }
            return true;
        }
        return false;
    }
    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields) { // по пути проверка на преграды
        if ( !super.canMove(row, col, row1, col1, fields) ){ return false; }

        if (fields[row1][col1] != null) return false;
        return checkDirectionBlocks(row, col, row1, col1, fields);
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1, Figure[][] fields) {
        if ( !super.canAttack(row, col, row1, col1, fields) ) { return false; }

        if ( !checkDirectionBlocks(row, col, row1, col1, fields) ) { return false; }

        switch (this.getColor()) {
            case 'w':
                if ( Objects.equals(fields[row1][col1].getColor(), "w") ) { return false; }
                break;
            case 'b':
                if ( Objects.equals(fields[row1][col1].getColor(), "b") ) { return false; }
                break;
        }

        return true;
    }
}
