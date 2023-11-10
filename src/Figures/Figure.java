package Figures;

// 1(w) - White \ 2(b) - Black;

import java.util.Objects;

public abstract class Figure {
    private String name;
    private char color;

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public char getColor() {
        return color;
    }
    public void setColor(char color) {
        this.color = color;
    }

    //private int[] position = new int[2];  // 0- row ; 1 - col


    public Figure(String name, char color) {
        this.name = name;
        this.color = color;
    }

    public boolean canMove(int row, int col, int row1, int col1, Figure[][] fields){
        return ( row >=0 && row < 8 ) && ( col >=0 && col < 8) &&
               ( row1 >=0 && row1 < 8 ) && ( col1 >=0 && col1 < 8 ) &&
//                ( row != row1 ) && ( col != col1 ); //при движении на одной линии это условие кривое скорее всего
                // ↑ тут была кривая попытка захватить 3 случая из 4. но корректно и удобно сделать её через отрицание ↓:
               !( (row == row1) && (col == col1) );
        }

//    public boolean canAttack(int row, int col, int row1, int col1){
//        return this.canMove(row, col, row1, col1);
//    }

    public boolean canAttack(int row, int col, int row1, int col1, Figure[][] fields){
        return (!Objects.equals(fields[row1][col1].getName(), "K"));
    }
}
