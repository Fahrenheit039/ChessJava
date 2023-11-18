package Figures;

import java.util.Objects;
import java.util.Scanner;

public class Pawn extends Figure {
    private boolean isFirstStep = true;

    public Pawn(String name, char color) {
        super(name, color);
    }

    @Override
    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields) {

        if ( !super.canMove(row, col, row1, col1, fields) ){ return false; }

        if (this.isFirstStep) {
            if ( ( ( ((row + 2 == row1) || (row + 1 == row1)) && this.getColor() == 'w' && fields[row+1][col] == null) ||
                   ( ((row - 2 == row1) || (row - 1 == row1)) && this.getColor() == 'b' && fields[row-1][col] == null) ) && (col == col1) ) {
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

        switch (this.getColor()) {
            case 'w':
                if (row + 1 == row1) {
                    if (row1 == 7) { return true; }

                    return true;
                }
                break;
            case 'b':
                if (row - 1 == row1) { return true; }
                break;
        }

        return false;
    }

    public Figure becomeAnyone(char color) {
        System.out.println("----- Выбери фигуру вместо пешки:\n \"Q\" for Queen \\ \"B\" for Bishop \\ \"N\" for Knight \\ \"R\" for Rook\n");
        Scanner in = new Scanner(System.in);
        String figure = in.nextLine();
        while ( true ){
            switch (figure) {
                case "Q": return new Queen("Q", color);
                case "B": return new Bishop("B", color);
                case "N": return new Knight("N", color);
                case "R": return new Rook("R", color);
            }
            System.out.print("try again : ");
            in = new Scanner(System.in);
            figure = in.nextLine();
        }
    }

}
