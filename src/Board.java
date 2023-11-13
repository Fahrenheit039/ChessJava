import Figures.Bishop;
import Figures.Figure;
import Figures.King;
import Figures.Knight;
import Figures.Pawn;
import Figures.Queen;
import Figures.Rook;

import java.util.ArrayList;

public class Board {
//TODO: Список фигур и начальное положение всех фигур
    private Figure[][] fields = new Figure[8][8];
    private ArrayList<String> takeWhite = new ArrayList(16);
    private ArrayList<String> takeBlack = new ArrayList(16);

    public char getColorGaming() {
        return colorGaming;
    }

    public void setColorGaming( char colorGaming ) {
        this.colorGaming = colorGaming;
    }

    private char colorGaming;

    public void init(){
        this.fields[0] = new Figure[]{
                new Rook("R", 'w'), new Knight("N", 'w'),
                new Bishop("B", 'w'), new Queen("Q", 'w'),
                new King("K", 'w'), new Bishop("B", 'w'),
                new Knight("N", 'w'),new Rook("R", 'w')
        };
        this.fields[1] = new Figure[]{
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
                new Pawn("P", 'w'),    new Pawn("P", 'w'),
        };

        this.fields[7] = new Figure[]{
                new Rook("R", 'b'), new Knight("N", 'b'),
                new Bishop("B", 'b'), new Queen("Q", 'b'),
                new King("K", 'b'), new Bishop("B", 'b'),
                new Knight("N", 'b'),new Rook("R", 'b')
        };
        this.fields[6] = new Figure[]{
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
                new Pawn("P", 'b'),    new Pawn("P", 'b'),
        };
    }

    public String getCell( int row, int col ){
        Figure figure = this.fields[row][col];
        if (figure == null){
            return "    ";
        }
        return " "+figure.getColor()+figure.getName()+" ";
    } // используется для отрисовки

    public Figure getCell_f( int row, int col ){
        return this.fields[row][col];
    } // для просмотра объекта в клетке

    public ArrayList<String> getTakeWhite() {
        return takeWhite;
    }

    public ArrayList<String> getTakeBlack() {
        return takeBlack;
    }

    private void pawnCheck( int row1, int col1, int row2, int col2) {
        if( this.fields[row2][col2].getName().equals("P") ){
            switch (fields[row2][col2].getColor()) {
                case 'w': if (row2 == 7) { fields[row2][col2] = ((Pawn)fields[row2][col2]).becomeAnyone('w'); } break;
                case 'b': if (row2 == 0) { fields[row2][col2] = ((Pawn)fields[row2][col2]).becomeAnyone('b'); } break;
            }
        }
    }
    private boolean shahFlagWhite = false;
    private boolean shahFlagBlack = false;
    private boolean shahCheck( Figure[][] fields, char color ) {
//        #1 search the right King
        int i=0,j=0,flag=0;
        for ( ; i<8; i++){
            for ( ; j<8; j++){
                if ( fields[i][j].getName().equals("K") && color == fields[i][j].getColor() ) {flag = 1; break;}
            }
            if (flag == 1) break;
        }

        // TODO: 11.11.2023 таблица с проставленными X в клетках, куда нельзя ходить королю. интересуют в радиусе 1
        // TODO: 11.11.2023 upd: шах есть шах. соседние X сделаем в мате
//        #2 shah check for current King
//        # Pawn
        switch (color){
            case 'w':
                if (i<=6){
                    if ( (j>=1 && j<=6) && ( ((fields[i+1][j-1] instanceof Pawn) && fields[i+1][j-1].getColor() != color) ||
                            ((fields[i+1][j+1] instanceof Pawn) && fields[i+1][j+1].getColor() != color) ) ) { return true; }
                    if ( j==0 && ((fields[i+1][j+1] instanceof Pawn) && fields[i+1][j+1].getColor() != color) ) { return true; }
                    if ( j==7 && ((fields[i+1][j-1] instanceof Pawn) && fields[i+1][j-1].getColor() != color) ) { return true; }
                }
                break;
            case 'b':
                if (i>=1){
                    if ( (j>=1 && j<=6) && ( ((fields[i-1][j-1] instanceof Pawn) && fields[i-1][j-1].getColor() != color) ||
                            ((fields[i-1][j+1] instanceof Pawn) && fields[i-1][j+1].getColor() != color) ) ) { return true; }
                    if ( j==0 && ((fields[i-1][j+1] instanceof Pawn) && fields[i-1][j+1].getColor() != color) ) { return true; }
                    if ( j==7 && ((fields[i-1][j-1] instanceof Pawn) && fields[i-1][j-1].getColor() != color) ) { return true; }
                } break;

        }
//        # 6 8 4 2
        int k=0;
        if (j>=1)
            for (k = j-1; k>=0; k--) // влево \ 4
                if (fields[i][k] != null)
                    if ( (fields[i][k].getColor() != color) && ((fields[i][k] instanceof Queen) || (fields[i][k] instanceof Rook)) ) return true;
        if (j<=6)
            for (k = j+1; k<=7; k++) // вправо \ 6
                if (fields[i][k] != null)
                    if ( (fields[i][k].getColor() != color) && ((fields[i][k] instanceof Queen) || (fields[i][k] instanceof Rook)) ) return true;
        if (i>=1)
            for (k = i-1; k>=0; k--) // вниз \ 2
                if (fields[k][j] != null)
                    if ( (fields[k][j].getColor() != color) && ((fields[k][j] instanceof Queen) || (fields[k][j] instanceof Rook)) ) return true;
        if (i<=6)
            for (k = i+1; k<=7; k++) // вверх \ 8
                if (fields[k][j] != null)
                    if ( (fields[k][j].getColor() != color) && ((fields[k][j] instanceof Queen) || (fields[k][j] instanceof Rook)) ) return true;
//        # 9 7 1 3
        int l=0;
        if (i>=1 && j>=1){
            k = i-1;
            l = j-1;
            while (k>=0 && l>=0){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k--;
                l--;
            }
        } // 1
        if (i<=6 && j<=6){
            k = i+1;
            l = j+1;
            while (k<=7 && l<=7){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k++;
                l++;
            }
        } // 9
        if (i<=6 && j>=1){
            k = i+1;
            l = j-1;
            while (k<=7 && l>=0){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k++;
                l--;
            }
        } // 7
        if (i>=1 && j<=6){
            k = i-1;
            l = j+1;
            while (k>=0 && l<=7){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k--;
                l++;
            }
        } // 3

//        # Knight
        if ( (i>=2 && i<=6 && j>=2 && j<=6) &&
                ( ((fields[i+1][j+2] instanceof Knight) && fields[i+1][j+2].getColor() != color) || ((fields[i+2][j+1] instanceof Knight) && fields[i+2][j+1].getColor() != color) ||
                        ((fields[i+2][j-1] instanceof Knight) && fields[i+2][j-1].getColor() != color) || ((fields[i+1][j-2] instanceof Knight) && fields[i+1][j-2].getColor() != color) ||
                        ((fields[i-1][j-2] instanceof Knight) && fields[i-1][j-2].getColor() != color) || ((fields[i-2][j-1] instanceof Knight) && fields[i-2][j-1].getColor() != color) ||
                        ((fields[i-2][j+1] instanceof Knight) && fields[i-2][j+1].getColor() != color) || ((fields[i-1][j+2] instanceof Knight) && fields[i-1][j+2].getColor() != color) ) )
            return true;
        // подсветка jetbrains кажется думает, что король в жизни не походит, поэтому рисует, что условия всегда true или false
//        # 1
        if ( i+1 <= 7 && j+2 <= 7)
            if ( (fields[i+1][j+2] instanceof Knight) && fields[i+1][j+2].getColor() != color) return true;
        if ( i+2 <= 7 && j+1 <= 7)
            if ( (fields[i+2][j+1] instanceof Knight) && fields[i+2][j+1].getColor() != color) return true;
//        # 2
        if ( i+1 <= 7 && j-2 >= 0)
            if ( (fields[i+1][j-2] instanceof Knight) && fields[i+1][j-2].getColor() != color) return true;
        if ( i+2 <= 7 && j-1 >= 0)
            if ( (fields[i+2][j-1] instanceof Knight) && fields[i+2][j-1].getColor() != color) return true;
        // # 3
        if ( i-1 >= 0 && j-2 >= 0)
            if ( (fields[i-1][j-2] instanceof Knight) && fields[i-1][j-2].getColor() != color) return true;
        if ( i-2 >= 0 && j-1 >= 0)
            if ( (fields[i-2][j-1] instanceof Knight) && fields[i-2][j-1].getColor() != color) return true;
//        # 4
        if ( i-1 >= 0 && j+2 <= 7)
            if ( (fields[i-1][j+2] instanceof Knight) && fields[i-1][j+2].getColor() != color) return true;
        if ( i-2 >= 0 && j+1 <= 7)
            if ( (fields[i-2][j+1] instanceof Knight) && fields[i-2][j+1].getColor() != color) return true;

        return false;
    } //false = ne Shah
    private boolean defeat( Figure[][] fields, char color ) {
//        #1 search the right King
        int i=0,j=0,flag=0;
        for ( ; i<8; i++){
            for ( ; j<8; j++){
                if ( fields[i][j].getName().equals("K") && color == fields[i][j].getColor() ) {flag = 1; break;}
            }
            if (flag == 1) break;
        }

        // TODO: 11.11.2023 таблица с проставленными X в клетках, куда нельзя ходить королю. интересуют в радиусе 1
        // TODO: 11.11.2023 upd: шах есть шах. соседние X сделаем в мате
//        #2 shah check for current King
//        # Pawn
        switch (color){
            case 'w':
                if (i<=6){
                    if ( (j>=1 && j<=6) && ( ((fields[i+1][j-1] instanceof Pawn) && fields[i+1][j-1].getColor() != color) ||
                            ((fields[i+1][j+1] instanceof Pawn) && fields[i+1][j+1].getColor() != color) ) ) { return true; }
                    if ( j==0 && ((fields[i+1][j+1] instanceof Pawn) && fields[i+1][j+1].getColor() != color) ) { return true; }
                    if ( j==7 && ((fields[i+1][j-1] instanceof Pawn) && fields[i+1][j-1].getColor() != color) ) { return true; }
                }
                break;
            case 'b':
                if (i>=1){
                    if ( (j>=1 && j<=6) && ( ((fields[i-1][j-1] instanceof Pawn) && fields[i-1][j-1].getColor() != color) ||
                            ((fields[i-1][j+1] instanceof Pawn) && fields[i-1][j+1].getColor() != color) ) ) { return true; }
                    if ( j==0 && ((fields[i-1][j+1] instanceof Pawn) && fields[i-1][j+1].getColor() != color) ) { return true; }
                    if ( j==7 && ((fields[i-1][j-1] instanceof Pawn) && fields[i-1][j-1].getColor() != color) ) { return true; }
                } break;

        }
//        # 6 8 4 2
        int k=0;
        if (j>=1)
            for (k = j-1; k>=0; k--) // влево \ 4
                if (fields[i][k] != null)
                    if ( (fields[i][k].getColor() != color) && ((fields[i][k] instanceof Queen) || (fields[i][k] instanceof Rook)) ) return true;
        if (j<=6)
            for (k = j+1; k<=7; k++) // вправо \ 6
                if (fields[i][k] != null)
                    if ( (fields[i][k].getColor() != color) && ((fields[i][k] instanceof Queen) || (fields[i][k] instanceof Rook)) ) return true;
        if (i>=1)
            for (k = i-1; k>=0; k--) // вниз \ 2
                if (fields[k][j] != null)
                    if ( (fields[k][j].getColor() != color) && ((fields[k][j] instanceof Queen) || (fields[k][j] instanceof Rook)) ) return true;
        if (i<=6)
            for (k = i+1; k<=7; k++) // вверх \ 8
                if (fields[k][j] != null)
                    if ( (fields[k][j].getColor() != color) && ((fields[k][j] instanceof Queen) || (fields[k][j] instanceof Rook)) ) return true;
//        # 9 7 1 3
        int l=0;
        if (i>=1 && j>=1){
            k = i-1;
            l = j-1;
            while (k>=0 && l>=0){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k--;
                l--;
            }
        } // 1
        if (i<=6 && j<=6){
            k = i+1;
            l = j+1;
            while (k<=7 && l<=7){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k++;
                l++;
            }
        } // 9
        if (i<=6 && j>=1){
            k = i+1;
            l = j-1;
            while (k<=7 && l>=0){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k++;
                l--;
            }
        } // 7
        if (i>=1 && j<=6){
            k = i-1;
            l = j+1;
            while (k>=0 && l<=7){
                if (fields[k][l] != null)
                    if ( (fields[k][l].getColor() != color) && ((fields[k][l] instanceof Queen) || (fields[k][l] instanceof Bishop)) ) return true;
                k--;
                l++;
            }
        } // 3

//        # Knight
        if ( (i>=2 && i<=6 && j>=2 && j<=6) &&
                ( ((fields[i+1][j+2] instanceof Knight) && fields[i+1][j+2].getColor() != color) || ((fields[i+2][j+1] instanceof Knight) && fields[i+2][j+1].getColor() != color) ||
                        ((fields[i+2][j-1] instanceof Knight) && fields[i+2][j-1].getColor() != color) || ((fields[i+1][j-2] instanceof Knight) && fields[i+1][j-2].getColor() != color) ||
                        ((fields[i-1][j-2] instanceof Knight) && fields[i-1][j-2].getColor() != color) || ((fields[i-2][j-1] instanceof Knight) && fields[i-2][j-1].getColor() != color) ||
                        ((fields[i-2][j+1] instanceof Knight) && fields[i-2][j+1].getColor() != color) || ((fields[i-1][j+2] instanceof Knight) && fields[i-1][j+2].getColor() != color) ) )
            return true;
        // подсветка jetbrains кажется думает, что король в жизни не походит, поэтому рисует, что условия всегда true или false
//        # 1
        if ( i+1 <= 7 && j+2 <= 7)
            if ( (fields[i+1][j+2] instanceof Knight) && fields[i+1][j+2].getColor() != color) return true;
        if ( i+2 <= 7 && j+1 <= 7)
            if ( (fields[i+2][j+1] instanceof Knight) && fields[i+2][j+1].getColor() != color) return true;
//        # 2
        if ( i+1 <= 7 && j-2 >= 0)
            if ( (fields[i+1][j-2] instanceof Knight) && fields[i+1][j-2].getColor() != color) return true;
        if ( i+2 <= 7 && j-1 >= 0)
            if ( (fields[i+2][j-1] instanceof Knight) && fields[i+2][j-1].getColor() != color) return true;
        // # 3
        if ( i-1 >= 0 && j-2 >= 0)
            if ( (fields[i-1][j-2] instanceof Knight) && fields[i-1][j-2].getColor() != color) return true;
        if ( i-2 >= 0 && j-1 >= 0)
            if ( (fields[i-2][j-1] instanceof Knight) && fields[i-2][j-1].getColor() != color) return true;
//        # 4
        if ( i-1 >= 0 && j+2 <= 7)
            if ( (fields[i-1][j+2] instanceof Knight) && fields[i-1][j+2].getColor() != color) return true;
        if ( i-2 >= 0 && j+1 <= 7)
            if ( (fields[i-2][j+1] instanceof Knight) && fields[i-2][j+1].getColor() != color) return true;

        return false;
    } //false = ne Shah

    private boolean castling( int row1, int col1, int row2, int col2, Figure[][] fields ){
        Figure figure = fields[row1][col1];
        if( figure.getName().equals("K") && ((King)fields[row2][col2]).isFirstStep && Math.abs(col1-col2) == 2 )
            if ( col1 < col2 ) {
                if ( !fields[row1][col1+3].getName().equals("R") || !((Rook)fields[row1][col1 + 3]).isFirstStep || fields[row1][col1+1] != null ) {
                    System.out.println("path is not empty");
                    fields[row1][col1] = figure;
                    fields[row2][col2] = null;
                    return false;
                }
                fields[row1][col1+1] = figure;
                fields[row1][col1+2] = null;
                switch(colorGaming){
                    case 'w':
                        if ( shahFlagWhite || shahCheck(fields, colorGaming) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1+1] = null;
                            return false;
                        } break;
                    case 'b':
                        if ( shahFlagBlack || shahCheck(fields, colorGaming) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1+1] = null;
                            return false;
                        } break;
                }
                fields[row1][col1+2] = figure;
                fields[row1][col1+1] = fields[row1][col1+3];
                fields[row1][col1+3] = null;
                ((King)fields[row1][col1+2]).isFirstStep = false;
            } else {
                if ( !fields[row1][col1-4].getName().equals("R") || !((Rook)fields[row1][col1-4]).isFirstStep ||
                        fields[row1][col1-1] != null || fields[row1][col1-3] != null ) {
                    System.out.println("path is not empty");
                    fields[row1][col1] = figure;
                    fields[row2][col2] = null;
                    return false;
                }
                fields[row1][col1-1] = figure;
                fields[row1][col1-2] = null;
                switch(colorGaming){
                    case 'w':
                        if ( shahFlagWhite || shahCheck(fields, colorGaming) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1-1] = null;
                            return false;
                        } break;
                    case 'b':
                        if ( shahFlagBlack || shahCheck(fields, colorGaming) ){
                            System.out.println("path under Shah");
                            fields[row1][col1] = figure;
                            fields[row1][col1-1] = null;
                            return false;
                        } break;
                }
                fields[row1][col1-2] = figure;
                fields[row1][col1-1] = fields[row1][col1-4];
                fields[row1][col1-4] = null;
                ((King)fields[row1][col1-2]).isFirstStep = false;
            }
        return true;
    }

    public boolean move_figure( int row1, int col1, int row2, int col2 ){

        Figure figure =  this.fields[row1][col1];

        if (figure.getColor() != colorGaming) return false;

        shahFlagWhite = shahCheck(fields, 'w');
        shahFlagBlack = shahCheck(fields, 'b');

        if (figure.canMove(row1, col1, row2, col2, fields) && this.fields[row2][col2]==null){

            System.out.println("move");
            fields[row2][col2] = figure;
            fields[row1][col1] = null;

            if ( shahCheck(fields, colorGaming) ) {
                System.out.println("still under Shah");
                fields[row1][col1] = figure;
                fields[row2][col2] = null;
                return false;
            }

            if ( !castling(row1, col1, row2, col2, fields) ) return false;
            pawnCheck(row1, col1, row2, col2);

            return true;
        } else if (figure.canAttack(row1, col1, row2, col2, fields) && this.fields[row2][col2] != null &&
                this.fields[row2][col2].getColor() != this.fields[row1][col1].getColor() &&
                !this.fields[row2][col2].getName().equals("K") ){

            System.out.println("attack");
            this.fields[row2][col2] = figure;
            this.fields[row1][col1] = null;

            if ( shahCheck(fields, colorGaming) ) {
                System.out.println("still under Shah");
                fields[row1][col1] = figure;
                fields[row2][col2] = null;
                return false;
            }

            switch (this.fields[row2][col2].getColor()){
                case 'w': this.takeWhite.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
                case 'b': this.takeBlack.add(this.fields[row2][col2].getColor()+this.fields[row2][col2].getName());break;
            }

            pawnCheck(row1, col1, row2, col2);

            return true;
        }
        return false;

    }

    public void print_board(){
        System.out.println(" +----+----+----+----+----+----+----+----+");
        for(int row = 7; row >= 0; row--){
            System.out.print(row);
            for(int col = 0; col <= 7; col++){
                System.out.print("|"+getCell(row, col));
             }
            System.out.println("|");
            System.out.println(" +----+----+----+----+----+----+----+----+");
        }

        for(int col = 0; col <= 7; col++){
            System.out.print("    "+col);
        }


    }


}
