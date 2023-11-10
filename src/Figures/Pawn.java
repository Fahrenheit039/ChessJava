package Figures;

import java.util.Objects;

public class Pawn extends Figure {

    // TODO: 10.11.2023 рокировка делается тем-же механизмом что и проверка первого хода для пешки 
    private boolean isFirstStep = true;

    public Pawn(String name, char color) {
        super(name, color);
    }

    // TODO: 10.11.2023 в canMove проверка на последний ряд и превращение в фигуру на выбор (можно сделать 9 ферзей) 
    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields) {

        if ( !super.canMove(row, col, row1, col1, fields) ){ return false; }

        if (this.isFirstStep) {
            if ( ( ( ((row + 2 == row1) || (row + 1 == row1)) && this.getColor() == 'w') ||
                   ( ((row - 2 == row1) || (row - 1 == row1)) && this.getColor() == 'b') ) && (col == col1)) {
                this.isFirstStep = false;
                return true;
            }

        } else {
            if ( ( ((row + 1 == row1) && (this.getColor() == 'w')) || ((row - 1 == row1) && (this.getColor() == 'b')) ) && (col == col1)) {
                return true;
            }

        }
        return false;
    }

    @Override
    public boolean canAttack(int row, int col, int row1, int col1, Figure[][] fields) {
        if (Math.abs(col - col1) != 1) { return false; }

        if ( !super.canAttack(row, col, row1, col1, fields) ) { return false; }

        switch (this.getColor()) {
            case 'w':
                if ( Objects.equals(fields[row1][col1].getColor(), "w") ) { return false; }
                if (row1 - 1 == row) { return true; }
                break;
            case 'b':
                if ( Objects.equals(fields[row1][col1].getColor(), "b") ) { return false; }
                if (row1 + 1 == row) { return true; }
                break;
        }

        return false;
    }
}
