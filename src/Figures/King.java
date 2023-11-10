package Figures;

import java.util.Objects;

public class King extends Figure{
    public King(String name, char color) {
        super(name, color);
    }

    // TODO: 10.11.2023 рокировка делается тем-же механизмом что и проверка первого хода для пешки
    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields) {
        if ( !super.canMove(row, col, row1, col1, fields) ){ return false; }

        if ( Math.abs(row - row1)<=1 && Math.abs(col - col1)<=1 ) { return true; }
        return false;
    }
    // TODO: 10.11.2023 допилить проверку на сближение с вражеским королем
    // TODO: 10.11.2023 допилить рокировку королю по типу 1 хода пешки

    @Override
    public boolean canAttack(int row, int col, int row1, int col1, Figure[][] fields) {
        if ( Math.abs(row - row1)>1 || Math.abs(col - col1)>1 ) { return false; }

        if ( !super.canAttack(row, col, row1, col1, fields) ) { return false; }

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
